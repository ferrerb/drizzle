package gro.gibberish.drizzle.rxbus_events;

import gro.gibberish.drizzle.weather_beans_owm.LocationModel;

public class CurrentLocationWeatherEvent implements RxBusEvent {
    private LocationModel locationModel;

    public CurrentLocationWeatherEvent(LocationModel locationModel) {
        this.locationModel = locationModel;
    }

    public LocationModel getData() {return locationModel;}
}
