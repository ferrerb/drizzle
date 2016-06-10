package gro.gibberish.drizzle.rxbus_events;

import gro.gibberish.drizzle.weather_beans.LocationForecastModel;

public class CurrentLocationForecastEvent implements RxBusEvent {
    private LocationForecastModel locationForecastModel;

    public CurrentLocationForecastEvent(LocationForecastModel locationForecastModel) {
        this.locationForecastModel = locationForecastModel;
    }

    public LocationForecastModel getData() {return locationForecastModel;}
}
