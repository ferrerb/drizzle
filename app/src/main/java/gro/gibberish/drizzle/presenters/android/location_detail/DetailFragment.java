package gro.gibberish.drizzle.presenters.android.location_detail;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import javax.inject.Inject;

import gro.gibberish.drizzle.R;
import gro.gibberish.drizzle.presenters.android.common.BaseFragment;
import gro.gibberish.drizzle.data_external.weather_beans_owm.LocationForecastModel;
import gro.gibberish.drizzle.data_external.weather_beans_owm.LocationModel;
import gro.gibberish.drizzle.ui.WeatherForecastAdapter;
import gro.gibberish.drizzle.util.NumberFormatting;

public class DetailFragment extends BaseFragment implements DetailView {
    @Inject DetailPresenter detailPresenter;
    private String currentLocationId;
    private View result;
    private RecyclerView forecastList;
    private ActionBar actionBar;

    public static DetailFragment newInstance() {
        return new DetailFragment();
    }

    public DetailFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        result = inflater.inflate(R.layout.fragment_location_detail, container, false);
        forecastList = (RecyclerView) result.findViewById(R.id.forecast_recyclerview);
        actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        return result;

    }

    @Override
    public void onResume() {
        super.onResume();
        detailPresenter.init(this);
        detailPresenter.onResume();
    }

    @Override
    public void showForecast(LocationForecastModel data) {
        forecastList.setLayoutManager(new LinearLayoutManager(getActivity()));
        forecastList.swapAdapter(new WeatherForecastAdapter(data), false);
    }

    @Override
    public void showCurrentWeather(LocationModel data) {
        TextView locationTemp = (TextView) result.findViewById(R.id.city_current_temp);
        TextView locationHumidity = (TextView) result.findViewById(R.id.city_current_humidity);
        TextView locationPressure = (TextView) result.findViewById(R.id.city_current_pressure);

        actionBar.setTitle(data.getName());
        // TODO dat dependency on utility class. HMM
        locationTemp.setText(NumberFormatting.doubleToStringNoDecimals(data.getMain().getTemp()) +
                getString(R.string.degrees));
        locationHumidity.setText(NumberFormatting.doubleToStringNoDecimals(data.getMain().getHumidity()) +
                getString(R.string.percent));
        locationPressure.setText(NumberFormatting.doubleToStringOneDecimal(data.getMain().getPressure()) +
                getString(R.string.percent));
    }
}
