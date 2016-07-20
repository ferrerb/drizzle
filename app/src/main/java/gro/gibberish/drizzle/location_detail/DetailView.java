package gro.gibberish.drizzle.location_detail;

import gro.gibberish.drizzle.weather_beans_owm.LocationForecastModel;
import gro.gibberish.drizzle.weather_beans_owm.LocationModel;

public interface DetailView {
    void showCurrentWeather(LocationModel data);

    void showForecast(LocationForecastModel data);
}
