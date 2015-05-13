package gro.gibberish.drizzle.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import gro.gibberish.drizzle.R;
import gro.gibberish.drizzle.data.ApiProvider;
import gro.gibberish.drizzle.data.FileHandler;
import gro.gibberish.drizzle.models.LocationForecastModel;
import gro.gibberish.drizzle.models.LocationModel;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Displays current and predicted weather for a specific location
 * Use the {@link LocationDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LocationDetailFragment extends Fragment {
    // Parameter names for creating a new fragment instance
    private static final String API_KEY = "api_key";
    private static final String LOCATION = "id";
    private static final String DAY_COUNT = "5";
    private static final long ONE_HOUR_MS = 3600000;
    // Appended to the forecast saved file and shared pref update time to differentiate from a locations saved current weather
    private static final String FORECAST_FILE_APPENDED = "cast";
    private static final String WEATHER_LIST_FILE = "allCurrentWeather.srl";
    private View result;
    private RecyclerView forecastList;

    private SharedPreferences sp;
    private String unitType;
    private String mApiKey;
    private String mLocation;

    /**
     * Returns a new instance of this fragment with the specified parameters
     *
     * @param key The API key for openweathermap
     * @param loc The desired location's zip code.
     * @return A new instance of fragment LocationDetailFragment.
     */
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
        result = inflater.inflate(R.layout.fragment_location_detail, container, false);
        sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        forecastList = (RecyclerView) result.findViewById(R.id.forecast_recyclerview);

        ((ActionBarActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        return result;
    }

    @Override
    public void onResume() {
        super.onResume();
        insertWeather();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    private void insertWeather() {
        long lastForecastRefresh = sp.getLong((mLocation + FORECAST_FILE_APPENDED), 0L);

        FileHandler.getSerializedObjectObservable(
                LocationModel.class,
                getActivity().getCacheDir(),
                mLocation)
                    .subscribe(
                            this::insertCurrentData,
                            System.err::println,
                            () -> {}
                    );

        if (System.currentTimeMillis() - lastForecastRefresh > ONE_HOUR_MS) {
            getForecastFromApi();
        } else {
            FileHandler.getSerializedObjectObservable(
                    LocationForecastModel.class,
                    getActivity().getCacheDir(),
                    mLocation + FORECAST_FILE_APPENDED)
                    .subscribe(
                            this::insertForecastData,
                            e -> { System.err.println(e);
                                   getForecastFromApi(); },
                            () -> {}
                    );
        }
    }

    private void getForecastFromApi() {
        ApiProvider.getWeatherService().getLocationDailyForecast(
                mLocation, DAY_COUNT, "imperial", mApiKey)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(forecastData -> FileHandler.saveSerializedObjectObservable(
                        forecastData,
                        getActivity().getCacheDir(),
                        mLocation + FORECAST_FILE_APPENDED).subscribe())
                .subscribe(
                        this::insertForecastData,
                        System.err::println,
                        () ->
                                sp.edit().putLong(
                                        mLocation + FORECAST_FILE_APPENDED,
                                        System.currentTimeMillis()).apply()
                );
    }

    private void insertCurrentData(LocationModel data) {
        TextView cityName = (TextView) result.findViewById(R.id.city_name);
        TextView cityTemp = (TextView) result.findViewById(R.id.city_current_temp);
        TextView cityHumid = (TextView) result.findViewById(R.id.city_current_humidity);
        TextView cityPressure = (TextView) result.findViewById(R.id.city_current_pressure);

        cityName.setText(data.getName());
        cityTemp.setText(Double.toString(data.getMain().getTemp()) + getString(R.string.degrees_fahrenheit));
        cityHumid.setText(Double.toString(data.getMain().getHumidity()) + getString(R.string.percent));
        cityPressure.setText(Double.toString(data.getMain().getPressure()) + R.string.percent);
    }

    private void insertForecastData(LocationForecastModel data) {
        forecastList.setLayoutManager(new LinearLayoutManager(getActivity()));
        forecastList.swapAdapter(new WeatherForecastAdapter(data), false);
    }


    @Override
    public void onDetach() {
        super.onDetach();
    }
}