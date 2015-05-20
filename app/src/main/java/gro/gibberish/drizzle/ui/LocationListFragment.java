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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import gro.gibberish.drizzle.R;
import gro.gibberish.drizzle.data.ApiProvider;
import gro.gibberish.drizzle.data.FileHandler;
import gro.gibberish.drizzle.models.LocationModel;
import gro.gibberish.drizzle.models.MultipleLocationModel;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * A fragment that displays a recyclerview containing weather at user submitted locations
 * Activities that contain this fragment must implement the
 * {@link LocationListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LocationListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LocationListFragment extends Fragment
        implements LocationAddFragment.OnLocationSubmitted{

    private static final String LOCATIONS = "locations";
    private static final String API_KEY = "api_key";
    private static final String NEEDS_REFRESH = "needs_refresh";
    private String mLocations;
    private String mApi;
    private boolean needsRefresh;
    private RecyclerView rv;
    private Subscription mWeatherDownloadSubscription;
    private List<LocationModel> mData = new ArrayList<LocationModel>();
    private OnItemTouchListener mOnItemClick;
    private OnFragmentInteractionListener mListener;

    /**
     * Creates a new fragment containing a recycler view
     *
     * @param location List of locations as city ID, comma separated
     * @return A new instance of fragment LocationListFragment.
     */
    public static LocationListFragment newInstance(String location, String apiKey, Boolean refresh) {
        LocationListFragment fragment = new LocationListFragment();
        Bundle args = new Bundle();
        args.putString(LOCATIONS, location);
        args.putString(API_KEY, apiKey);
        args.putBoolean(NEEDS_REFRESH, refresh);
        fragment.setArguments(args);
        return fragment;
    }

    public LocationListFragment() {
    }

    public interface OnFragmentInteractionListener {
        void onListWeatherRefreshed(long refreshTime);
        void onLocationChosen(long id);
        void onLocationAdded(String locations);
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
            mLocations = getArguments().getString(LOCATIONS);
            mApi = getArguments().getString(API_KEY);
            needsRefresh = getArguments().getBoolean(NEEDS_REFRESH);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View result = inflater.inflate(R.layout.fragment_location_list, container, false);
        rv = (RecyclerView) result.findViewById(R.id.recycler_list);
        // TODO make use of insert item, etc?
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));

        mOnItemClick = (view, position) -> mListener.onLocationChosen(mData.get(position).getId());

        if (mLocations.length() > 0) {
            insertWeather();
        }
        // TODO CompositeSubscription
        // TODO possibly move the FAB to this fragments layout
        ImageButton btnAddLocation = (ImageButton) getActivity().findViewById(R.id.btn_add_location_fab);
        btnAddLocation.setOnClickListener(
                view -> {
                    FragmentManager fm = getFragmentManager();
                    LocationAddFragment frag = LocationAddFragment.newInstance();
                    frag.setTargetFragment(LocationListFragment.this, 0);
                    frag.show(fm, "");
                }
        );
        return result;
    }

    private void insertWeather() {
        // TODO All locations are added but they are not necessarily in the order in mLocations
        if (needsRefresh) {
            getWeatherFromApi(mLocations);
        } else {
            String[] locationsArray = mLocations.split(",");
            Observable.from(locationsArray)
                    .flatMap(s -> FileHandler.getSerializedObjectObservable(
                            LocationModel.class, getActivity().getCacheDir(), s))
                    .subscribe(
                            mData::add,
                            System.err::println,
                            () -> {
                                // TODO maybe dont need this check, since needRefresh should be true if mdata would be null
                                if (mData != null) {
                                    rv.swapAdapter(new WeatherListAdapter(mData, mOnItemClick), true);
                                } else {
                                    getWeatherFromApi(mLocations);
                                }
                            }
                    );

        }
    }

    private void getWeatherFromApi(String loc) {
        mWeatherDownloadSubscription = ApiProvider.getWeatherService().getAllLocationsWeather(loc, "imperial", mApi)
                .doOnNext(weatherData -> saveLocationWeatherToSeparateFiles(weatherData.getLocationList()))
                .map(MultipleLocationModel::getLocationList)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        weatherData -> {
                            mData = weatherData;
                            rv.swapAdapter(new WeatherListAdapter(mData, mOnItemClick), true);
                            },
                        // TODO Have an errorfragment or something display
                        Throwable::printStackTrace,
                        () -> mListener.onListWeatherRefreshed(System.currentTimeMillis()));
    }

    // TODO Currently not doing anything with errors from the observable.
    private void saveLocationWeatherToSeparateFiles(List<LocationModel> data) {
        for ( LocationModel loc : data ) {
            FileHandler.saveSerializedObjectObservable(
                    loc,
                    getActivity().getCacheDir(),
                    Long.toString(loc.getId()))
            .subscribe();
        }
    }

    @Override
    public void onZipCodeEntered(String zip) {
        // The second call with flatmap is to get the location ID, since a request for location
        // by zip code does not return the ID, but does return the coordinates :|
        if (zip.length() == 5) {
            String azip = zip + ",us"; // To conform to the API needs
            ApiProvider.getWeatherService().getLocationByZip(azip, "imperial", mApi)
                    .flatMap(data -> ApiProvider.getWeatherService().getLocationByCoords(
                            Double.toString(data.getCoord().getLat()),
                            Double.toString(data.getCoord().getLon()),
                            "imperial",
                            mApi))
                    .subscribe(
                            this::updateLocationList,
                            error -> Log.d("error", error.getMessage()),
                            () -> getWeatherFromApi(mLocations)
                    );
        }
    }

    @Override
    public void onGpsCoordsChosen(double latitude, double longitude) {
        // Make the call to the API for a single location, add it
        // to the saved IDs, and to mLocaions, and getweatherformapi
        ApiProvider.getWeatherService().getLocationByCoords(
                Double.toString(latitude), Double.toString(longitude), "imperial", mApi)
                    .subscribe(
                            this::updateLocationList,
                            Throwable::printStackTrace,
                            () -> getWeatherFromApi(mLocations)
                    );
    }

    private void sortRetrievedLocations(LocationModel data, String[] original) {

    }

    private void updateLocationList(LocationModel data) {
        if (mLocations.length() == 0) {
            mLocations = Long.toString(data.getId());
        } else {
            mLocations += "," + Long.toString(data.getId());
        }

        mListener.onLocationAdded(mLocations);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        if (mWeatherDownloadSubscription != null) {
            mWeatherDownloadSubscription.unsubscribe();
        }
    }
}
