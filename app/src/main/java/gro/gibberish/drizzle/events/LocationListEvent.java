package gro.gibberish.drizzle.events;

import java.util.List;

import gro.gibberish.drizzle.models.LocationModel;

public class LocationListEvent {
    private List<LocationModel> locationList;

    public LocationListEvent(List<LocationModel> locationList) {
        this.locationList = locationList;
    }

    public List<LocationModel> getData() {
        return locationList;
    }
}
