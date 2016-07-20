package gro.gibberish.drizzle.rxbus_events;

import java.util.List;

import gro.gibberish.drizzle.weather_beans_owm.LocationModel;

public class WeatherListDownloadEvent implements RxBusEvent {
    private List<LocationModel> locationList;

    public WeatherListDownloadEvent(List<LocationModel> locationList) {
        this.locationList = locationList;
    }

    public List<LocationModel> getData() {
        return locationList;
    }
}
