package gro.gibberish.drizzle.ui;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import gro.gibberish.drizzle.R;
import gro.gibberish.drizzle.http.WeatherApi;
import gro.gibberish.drizzle.models.BaseModel;
import gro.gibberish.drizzle.models.LocationForecastModel;
import gro.gibberish.drizzle.models.LocationModel;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Displays current and predicted weather for a specific location
 * Activities that contain this fragment must implement the
 * {@link LocationDetailFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LocationDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LocationDetailFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String API_KEY = "API_KEY";
    private static final String LOCATION = "LOCATION";
    private static final String DAY_COUNT = "5";

    // TODO: Rename and change types of parameters
    private String mApiKey;
    private String mLocation;

    private OnFragmentInteractionListener mListener;

    /**
     * Returns a new instance of this fragment with the specified parameters
     *
     * @param key The API key for openweathermap
     * @param loc The desired location's zip code.
     * @return A new instance of fragment LocationDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LocationDetailFragment newInstance(String key, String loc) {
        LocationDetailFragment fragment = new LocationDetailFragment();
        Bundle args = new Bundle();
        args.putString(API_KEY, key);
        args.putString(LOCATION, loc);
        fragment.setArguments(args);
        return fragment;
    }

    public LocationDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mApiKey = getArguments().getString(API_KEY);
            mLocation = getArguments().getString(LOCATION);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View result = inflater.inflate(R.layout.fragment_location_detail, container, false);

        refreshWeather();

        return result;
    }

    private void refreshWeather() {
       /*WeatherApi.getWeatherService()
                .getLocationDetailWeather(mLocation, getString(R.string.units_imperial), mApiKey)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        locationModel -> insertLocationData(locationModel),
                        error -> Log.d("error", error.getMessage()),
                        () -> {});
        */
        Observable.zip(
            WeatherApi.getWeatherService().getLocationDetailWeather(mLocation, getString(R.string.units_imperial), mApiKey),
            WeatherApi.getWeatherService().getLocationDailyForecast(mLocation, DAY_COUNT, getString(R.string.units_imperial), mApiKey),
                (locationData, forecastData) -> { List<BaseModel> mList = new ArrayList<BaseModel>();
                    mList.add(locationData);
                    mList.add(forecastData);
                    return mList; })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        weatherData -> insertLocationData(weatherData),
                        error -> Log.d("error", error.getMessage()),
                        () -> {});
    }

    private void insertLocationData(List<BaseModel> l) {
        TextView cityName = (TextView) getActivity().findViewById(R.id.city_name);
        TextView cityTemp = (TextView) getActivity().findViewById(R.id.city_current_temp);
        TextView cityHumid = (TextView) getActivity().findViewById(R.id.city_current_humidity);
        LocationModel mModel = (LocationModel)l.get(0);
        LocationForecastModel mForecast = (LocationForecastModel)l.get(1);

        cityName.setText(mModel.getName());
        cityTemp.setText(Double.toString(mModel.getMain().getTemp()) + getString(R.string.degrees_fahrenheit));
        cityHumid.setText(Double.toString(mModel.getMain().getHumidity()) + getString(R.string.percent));

    }
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
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
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
