package gro.gibberish.drizzle.rxbus_events;

import gro.gibberish.drizzle.data_external.weather_beans_owm.LocationModel;

public class CurrentLocationWeatherEvent implements RxBusEvent {
    private LocationModel locationModel;

    public CurrentLocationWeatherEvent(LocationModel locationModel) {
        this.locationModel = locationModel;
    }

    public LocationModel getData() {return locationModel;}
}
