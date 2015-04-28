package gro.gibberish.drizzle.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Change this
 */
public class MultipleLocationModel implements Serializable {
    private static final long serialVersionUID = 1L;

    public List<LocationModel> getLocationList() {
        return locationList;
    }

    public void setLocationList(List<LocationModel> locationList) {
        this.locationList = locationList;
    }

    @SerializedName("list")
    private List<LocationModel> locationList = new ArrayList<LocationModel>();
}
