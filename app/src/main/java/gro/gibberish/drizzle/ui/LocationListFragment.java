package gro.gibberish.drizzle.ui;

import android.app.Activity;
import android.net.Uri;
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
import java.io.StreamCorruptedException;

import gro.gibberish.drizzle.R;
import gro.gibberish.drizzle.http.WeatherApi;
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
    private String mLocations;
    private String mApi;
    private boolean needsRefresh;
    private RecyclerView rv;
    private Subscription mSubscription;
    private WeatherListAdapter mAdapter;


    private OnFragmentInteractionListener mListener;

    /**
     * Creates a new fragment containing a recycler view
     *
     * @param location List of locations as city ID, comma seperated
     * @return A new instance of fragment LocationListFragment.
     */
    // TODO: Rename and change types and number of parameters
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
        if (needsRefresh) {
            getWeatherFromApi();
        } else {
            MultipleLocationModel data = getWeatherFromFile();
            rv.swapAdapter(new WeatherListAdapter(data.getLocationList()), false);

        }
        return result;
    }

    private void getWeatherFromApi() {
        /* The zip function takes some number of observables and combines output from each one using
         * a provided function, here just putting the original observables objects into a ArrayList,
         * and outputs a new observable.  Exposition.
         */
        Log.d("weather from internet!", "true");
        mSubscription = WeatherApi.getWeatherService().getAllLocationsWeather(mLocations, "imperial", mApi)
                .doOnNext(weatherData -> saveWeatherToFile(weatherData))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        weatherData -> rv.swapAdapter(new WeatherListAdapter(weatherData.getLocationList()), false),
                        // TODO Have an errorfragment or something display
                        error -> Log.d("error", error.getMessage()),
                        () -> {});
    }

    private MultipleLocationModel getWeatherFromFile() {
        Log.d("weather from files!", "true");
        MultipleLocationModel data = null;
        try {
            FileInputStream fis = new FileInputStream(new File(getActivity().getCacheDir(), "allCurrentWeather.srl"));
            ObjectInputStream in = new ObjectInputStream(fis);

            data = (MultipleLocationModel) in.readObject();
        }
        catch (FileNotFoundException e) {
            System.err.println("FileNotFoundException " + e.getMessage());
        }
        catch (IOException e) {
            System.err.println("IOException " + e.getMessage());
        }
        catch (ClassNotFoundException e) {
            System.err.println("ClassNotFoundException " + e.getMessage());
        }
        return data;
    }

    private void saveWeatherToFile(MultipleLocationModel data) {
        try {
            FileOutputStream fos = new FileOutputStream(new File(getActivity().getCacheDir(),"allCurrentWeather.srl"), false);
            ObjectOutputStream out = new ObjectOutputStream(fos);

            out.writeObject(data);
            out.close();
        }
        catch (FileNotFoundException e) {
            System.err.println("FileNotFoundException " + e.getMessage());
        }
        catch (IOException e) {
            System.err.println("IOException " + e.getMessage());
        }
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
        // TODO: Update argument type and name
        public void onLocationChosen(Uri uri);
    }

}
