package gro.gibberish.drizzle.presenters.android.location_detail;

import gro.gibberish.drizzle.data_external.model_net.LocationForecastModel;
import gro.gibberish.drizzle.data_external.model_net.LocationModel;

public interface DetailView {
    void showCurrentWeather(LocationModel data);

    void showForecast(LocationForecastModel data);
}
