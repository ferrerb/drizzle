package gro.gibberish.drizzle.events;

import gro.gibberish.drizzle.models.LocationForecastModel;

public class CurrentLocationForecastEvent implements RxBusEvent {
    private LocationForecastModel locationForecastModel;

    public CurrentLocationForecastEvent(LocationForecastModel locationForecastModel) {
        this.locationForecastModel = locationForecastModel;
    }

    public LocationForecastModel getData() {return locationForecastModel;}
}
