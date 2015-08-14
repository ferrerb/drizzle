package gro.gibberish.drizzle.events;

import gro.gibberish.drizzle.models.LocationModel;

public class CurrentLocationWeatherEvent implements RxBusEvent {
    private LocationModel locationModel;

    public CurrentLocationWeatherEvent(LocationModel locationModel) {
        this.locationModel = locationModel;
    }

    public LocationModel getData() {return locationModel;}
}
