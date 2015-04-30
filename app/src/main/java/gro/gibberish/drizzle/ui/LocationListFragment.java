package gro.gibberish.drizzle.ui;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

import gro.gibberish.drizzle.R;
import gro.gibberish.drizzle.data.FileHandler;
import gro.gibberish.drizzle.data.WeatherApi;
import gro.gibberish.drizzle.models.LocationModel;
import gro.gibberish.drizzle.models.MultipleLocationModel;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LocationListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LocationListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LocationListFragment extends Fragment {

    private static final String LOCATIONS = "locations";
    private static final String API_KEY = "api_key";
    private static final String NEEDS_REFRESH = "needs_refresh";
    private static final String WEATHER_LIST_FILE = "allCurrentWeather.srl";
    private String mLocations;
    private String mApi;
    private boolean needsRefresh;
    private RecyclerView rv;
    private Subscription mSubscription;
    private WeatherListAdapter mAdapter;
    private List<LocationModel> mData;
    OnItemTouchListener mOnItemClick;


    private OnFragmentInteractionListener mListener;

    /**
     * Creates a new fragment containing a recycler view
     *
     * @param location List of locations as city ID, comma seperated
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
        // Required empty public constructor
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
        // Inflate the layout for this fragment
        View result = inflater.inflate(R.layout.fragment_location_list, container, false);
        rv = (RecyclerView) result.findViewById(R.id.recycler_list);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));

        mOnItemClick = new OnItemTouchListener() {
            @Override
            public void onLocationClick(View v, int position) {
                mListener.onLocationChosen(mData.get(position).getId());
            }
        };
        if (needsRefresh) {
            getWeatherFromApi();
        } else {
            MultipleLocationModel data =
                    FileHandler.getSerializedObjectFromFile(
                            MultipleLocationModel.class,
                            getActivity().getCacheDir(),
                            WEATHER_LIST_FILE);
            mData = data.getLocationList();
            // TODO If data == null, call the api?
            rv.swapAdapter(new WeatherListAdapter(mData, mOnItemClick), false);

        }
        return result;
    }

    private void getWeatherFromApi() {
        Log.d("weather from internet!", "true");
        mSubscription = WeatherApi.getWeatherService().getAllLocationsWeather(mLocations, "imperial", mApi)
                .doOnNext(weatherData -> FileHandler.saveSerializedObjectToFile(
                        weatherData, getActivity().getCacheDir(), WEATHER_LIST_FILE))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        weatherData -> { mData = weatherData.getLocationList();
                            rv.swapAdapter(
                                new WeatherListAdapter(mData, mOnItemClick), false);},
                        // TODO Have an errorfragment or something display
                        error -> Log.d("error", error.getMessage()),
                        () -> {mListener.onListWeatherRefreshed(System.currentTimeMillis());});
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
    public void onDetach() {
        super.onDetach();
        mListener = null;
        mSubscription.unsubscribe();
    }

    public interface OnFragmentInteractionListener {
        void onListWeatherRefreshed(long refreshTime);

        void onLocationChosen(long id);
    }

}
