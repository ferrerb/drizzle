package gro.gibberish.drizzle.presenters.android.location_detail;

import gro.gibberish.drizzle.data_external.weather_beans_owm.LocationForecastModel;
import gro.gibberish.drizzle.data_external.weather_beans_owm.LocationModel;

public interface DetailView {
    void showCurrentWeather(LocationModel data);

    void showForecast(LocationForecastModel data);
}
