package gro.gibberish.drizzle.location_detail;

import gro.gibberish.drizzle.weather_beans.LocationForecastModel;
import gro.gibberish.drizzle.weather_beans.LocationModel;

public interface DetailView {
    void showCurrentWeather(LocationModel data);

    void showForecast(LocationForecastModel data);
}
