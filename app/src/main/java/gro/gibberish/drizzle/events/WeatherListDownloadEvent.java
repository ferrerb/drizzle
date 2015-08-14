package gro.gibberish.drizzle.events;

import java.util.List;

import gro.gibberish.drizzle.models.LocationModel;

public class WeatherListDownloadEvent {
    private List<LocationModel> locationList;

    public WeatherListDownloadEvent(List<LocationModel> locationList) {
        this.locationList = locationList;
    }

    public List<LocationModel> getData() {
        return locationList;
    }
}
