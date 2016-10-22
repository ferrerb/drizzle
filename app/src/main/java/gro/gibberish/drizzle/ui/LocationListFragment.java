package gro.gibberish.drizzle.ui;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;

import gro.gibberish.drizzle.R;
import gro.gibberish.drizzle.data_external.ApiProvider;
import gro.gibberish.drizzle.data_external.FileHandler;
import gro.gibberish.drizzle.util.LocationsStringHelper;
import gro.gibberish.drizzle.data_external.weather_beans_owm.LocationModel;
import gro.gibberish.drizzle.data_external.weather_beans_owm.MultipleLocationModel;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

public class LocationListFragment extends Fragment
        implements LocationAddFragment.OnLocationSubmitted{
    // TODO Add a static TAG with getclass.getsimplename

    private static final String API_KEY = "api_key";
    private static final String SP_LAST_REFRESH = "SP_LAST_REFRESH";
    private static final String LOCATIONS = "locations";
    private static final int ONE_HOUR_MS = 3600000;
    private String commaSeparatedLocations;
    private String apiKey;
    private RecyclerView recyclerView;
    private Subscription weatherDownloadSubscription;
    private List<LocationModel> locationWeatherList = new ArrayList<>();
    private OnItemTouchListener mOnItemClick;
    private OnFragmentInteractionListener mListener;
    private SharedPreferences sharedPreferences;

    public static LocationListFragment newInstance( String apiKey) {
        LocationListFragment fragment = new LocationListFragment();
        Bundle args = new Bundle();
        args.putString(API_KEY, apiKey);
        fragment.setArguments(args);
        return fragment;
    }

    public LocationListFragment() {
    }

    public interface OnFragmentInteractionListener {
        void onLocationChosen(long id);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            apiKey = getArguments().getString(API_KEY);
        }
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        commaSeparatedLocations = sharedPreferences.getString(LOCATIONS, "");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View result = inflater.inflate(R.layout.fragment_location_list, container, false);
        recyclerView = (RecyclerView) result.findViewById(R.id.recycler_list);
        // TODO make use of insert item, etc?
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mOnItemClick = (view, position) -> mListener.onLocationChosen(locationWeatherList.get(position).getId());
        if (commaSeparatedLocations.length() > 0) {
            retrieveWeatherFromFileOrInternet();
        }
        // TODO possibly move the FAB to this fragments layout
        ImageButton btnAddLocation =
                (ImageButton) getActivity().findViewById(R.id.btn_add_location_fab);
        btnAddLocation.setOnClickListener(view -> createAddLocationDialog());
        return result;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        if (weatherDownloadSubscription != null) {
            weatherDownloadSubscription.unsubscribe();
        }
    }

    private void createAddLocationDialog() {
        FragmentManager fm = getFragmentManager();
        LocationAddFragment frag = LocationAddFragment.newInstance();
        frag.setTargetFragment(LocationListFragment.this, 0);
        frag.show(fm, "");
    }

    @Override
    public void onZipCodeEntered(String zip) {
        // The second call with flatmap is to get the location ID, since a request for location
        // by zip code does not return the ID, but does return the coordinates :|
        if (zip.length() == 5) {
            zip += ",us"; // To conform to the API needs
            ApiProvider.getWeatherService().getLocationByZip(zip, "imperial", apiKey)
                    .doOnError(e -> Log.d("first get zip ", e.getMessage()))
                    .flatMap(data -> ApiProvider.getWeatherService().getLocationByCoords(
                            Double.toString(data.getCoord().getLat()),
                            Double.toString(data.getCoord().getLon()),
                            "imperial",
                            apiKey))
                    .subscribe(
                            this::saveLocationToLocationString,
                            error -> Log.d("error onzipentered", error.getMessage()),
                            this::getWeatherFromInternet
                    );
        }
    }

    @Override
    public void onGpsCoordsChosen(double latitude, double longitude) {
        ApiProvider.getWeatherService().getLocationByCoords(
                Double.toString(latitude), Double.toString(longitude), "imperial", apiKey)
                .subscribe(
                        this::saveLocationToLocationString,
                        Throwable::printStackTrace,
                        this::getWeatherFromInternet
                );
    }

    private void retrieveWeatherFromFileOrInternet() {
        long lastRefresh = sharedPreferences.getLong(SP_LAST_REFRESH, 0L);
        boolean needsRefresh = (System.currentTimeMillis() - lastRefresh) > ONE_HOUR_MS;
        if (needsRefresh) {
            getWeatherFromInternet();
        } else {
            getWeatherFromFile();
        }
    }

    private void getWeatherFromInternet() {
        weatherDownloadSubscription =
                ApiProvider.getWeatherService().getAllLocationsWeather(commaSeparatedLocations, "imperial", apiKey)
                .doOnNext(weatherData -> saveLocationWeatherToSeparateFiles(weatherData.getLocationList()))
                .map(MultipleLocationModel::getLocationList)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        weatherData -> {
                            locationWeatherList = weatherData;
                            recyclerView.swapAdapter(new WeatherListAdapter(locationWeatherList, mOnItemClick), true);
                            },
                        // TODO Have an errorfragment or something display
                        Throwable::printStackTrace,
                        () -> sharedPreferences.edit().putLong(SP_LAST_REFRESH, System.currentTimeMillis()).apply()
                );
    }

    private void getWeatherFromFile() {
        // TODO All locations are added but they are not necessarily in the order in commaSeparatedLocations
        Observable.from(LocationsStringHelper.createListFromCommaSeparatedString(commaSeparatedLocations))
                .flatMap(s -> FileHandler.getSerializedObjectFromFile(
                        LocationModel.class, getActivity().getCacheDir(), s))
                .subscribe(
                        locationWeatherList::add,
                        System.err::println,
                        () -> {
                            // TODO maybe dont need this check, since needRefresh should be true if mdata would be null
                            if (locationWeatherList == null) {
                                getWeatherFromInternet();
                            } else {
                                recyclerView.swapAdapter(new WeatherListAdapter(locationWeatherList, mOnItemClick), true);
                            }
                        }
                );
    }

    // TODO Currently not doing anything with errors from the observable.
    private void saveLocationWeatherToSeparateFiles(List<LocationModel> data) {
        for ( LocationModel loc : data ) {
            FileHandler.saveSerializableObjectToFile(
                    loc,
                    getActivity().getCacheDir(),
                    Long.toString(loc.getId()))
            .subscribe();
        }
    }

    private void saveLocationToLocationString(LocationModel data) {
        String newLocation = Long.toString(data.getId());
        commaSeparatedLocations = LocationsStringHelper
                .addLocationToCommaSeparatedString(newLocation, commaSeparatedLocations);

        sharedPreferences.edit().putString(LOCATIONS, commaSeparatedLocations).apply();
    }

}
