package gro.gibberish.drizzle.ui;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import gro.gibberish.drizzle.R;
import gro.gibberish.drizzle.data.ApiProvider;
import gro.gibberish.drizzle.data.FileHandler;
import gro.gibberish.drizzle.data.LocationsStringHelper;
import gro.gibberish.drizzle.data.NumberFormatting;
import gro.gibberish.drizzle.models.LocationForecastModel;
import gro.gibberish.drizzle.models.LocationModel;
import rx.android.schedulers.AndroidSchedulers;

public class LocationDetailFragment extends Fragment {
    private static final String API_KEY = "api_key";
    private static final String LOCATION = "id";
    private static final String DAY_COUNT = "5";
    private static final long ONE_HOUR_MS = 3600000;
    private static final String FORECAST_FILE_APPENDED = "cast";
    private View result;
    private RecyclerView forecastList;
    private ActionBar actionBar;
    private SharedPreferences sharedPreferences;
    private String unitType;
    private String apiKey;
    private String currentLocation;


    public static LocationDetailFragment newInstance(String key, String loc) {
        LocationDetailFragment fragment = new LocationDetailFragment();
        Bundle args = new Bundle();
        args.putString(API_KEY, key);
        args.putString(LOCATION, loc);
        fragment.setArguments(args);
        return fragment;
    }

    public LocationDetailFragment() {
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        if (getArguments() != null) {
            apiKey = getArguments().getString(API_KEY);
            currentLocation = getArguments().getString(LOCATION);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        result = inflater.inflate(R.layout.fragment_location_detail, container, false);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        forecastList = (RecyclerView) result.findViewById(R.id.forecast_recyclerview);

        actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        return result;
    }

    @Override
    public void onResume() {
        super.onResume();
        retrieveWeatherFromFileOrInternet();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_location_detail, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case (R.id.action_delete):
                deleteLocation();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void retrieveWeatherFromFileOrInternet() {
        getCurrentWeatherFromFile();

        long lastForecastRefresh = sharedPreferences.getLong((currentLocation + FORECAST_FILE_APPENDED), 0L);
        boolean needsRefresh = (System.currentTimeMillis() - lastForecastRefresh) > ONE_HOUR_MS;
        if (needsRefresh) {
            getForecastFromInternet();
        } else {
            getForecastFromFile();
        }
    }

    private void getCurrentWeatherFromFile() {
        FileHandler.getSerializedObjectFromFile(
                LocationModel.class,
                getActivity().getCacheDir(),
                currentLocation)
                .subscribe(
                        this::insertCurrentData,
                        System.err::println,
                        () -> {}
                );
    }

    private void getForecastFromFile() {
        FileHandler.getSerializedObjectFromFile(
                LocationForecastModel.class,
                getActivity().getCacheDir(),
                currentLocation + FORECAST_FILE_APPENDED)
                .subscribe(
                        this::insertForecastData,
                        e -> {
                            System.err.println(e);
                            getForecastFromInternet();
                            },
                        () -> {}
                );
    }

    private void getForecastFromInternet() {
        ApiProvider.getWeatherService().getLocationDailyForecast(
                currentLocation, DAY_COUNT, "imperial", apiKey)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(forecastData -> FileHandler.saveSerializableObjectToFile(
                        forecastData,
                        getActivity().getCacheDir(),
                        currentLocation + FORECAST_FILE_APPENDED).subscribe())
                .subscribe(
                        this::insertForecastData,
                        Throwable::printStackTrace,
                        () -> sharedPreferences.edit().putLong(
                                currentLocation + FORECAST_FILE_APPENDED,
                                System.currentTimeMillis()).apply()
                );
    }

    private void insertCurrentData(LocationModel data) {
        TextView locationTemp = (TextView) result.findViewById(R.id.city_current_temp);
        TextView locationHumidity = (TextView) result.findViewById(R.id.city_current_humidity);
        TextView locationPressure = (TextView) result.findViewById(R.id.city_current_pressure);

        actionBar.setTitle(data.getName());
        locationTemp.setText(NumberFormatting.doubleToStringNoDecimals(data.getMain().getTemp()) +
                getString(R.string.degrees));
        locationHumidity.setText(NumberFormatting.doubleToStringNoDecimals(data.getMain().getHumidity()) +
                getString(R.string.percent));
        locationPressure.setText(NumberFormatting.doubleToStringOneDecimal(data.getMain().getPressure()) +
                getString(R.string.percent));
    }

    private void insertForecastData(LocationForecastModel data) {
        forecastList.setLayoutManager(new LinearLayoutManager(getActivity()));
        forecastList.swapAdapter(new WeatherForecastAdapter(data), false);
    }

    private void deleteLocation() {
        // TODO get the location list, remove current location, deleted associated files, go back to listview
        String allLocations = sharedPreferences.getString("locations", "");
        String locationsAfterDelete = LocationsStringHelper.deleteLocationFromString(currentLocation, allLocations);
        sharedPreferences.edit().putString("locations", locationsAfterDelete).apply();
    }
}
