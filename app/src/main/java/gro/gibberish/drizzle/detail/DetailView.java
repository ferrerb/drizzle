package gro.gibberish.drizzle.detail;

import gro.gibberish.drizzle.models.LocationForecastModel;
import gro.gibberish.drizzle.models.LocationModel;

public interface DetailView {
    void showCurrentWeather(LocationModel data);

    void showForecast(LocationForecastModel data);
}
