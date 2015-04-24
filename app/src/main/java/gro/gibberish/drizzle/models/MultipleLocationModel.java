package gro.gibberish.drizzle.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Change this
 */
public class MultipleLocationModel {
    public List<LocationModel> getLocationList() {
        return locationList;
    }

    public void setLocationList(List<LocationModel> locationList) {
        this.locationList = locationList;
    }

    @SerializedName("list")
    private List<LocationModel> locationList = new ArrayList<LocationModel>();
}
