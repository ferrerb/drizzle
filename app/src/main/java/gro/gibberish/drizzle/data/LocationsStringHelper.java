package gro.gibberish.drizzle.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public final class LocationsStringHelper {

    private LocationsStringHelper() {
    }

    public static String addLocationToCommaSeparatedString(String newLocation,
                                                           String commaSeparatedLocations) {
        if (commaSeparatedLocations.length() == 0) {
            commaSeparatedLocations = newLocation;
        } else {
            commaSeparatedLocations += "," + newLocation;
        }

        return commaSeparatedLocations;
    }

    public static String deleteLocationFromString(
            String locationToBeDeleted, String commaSeparatedLocations) {
        if (commaSeparatedLocations.equals(locationToBeDeleted)) {
            return "";
        }

        List<String> newList = createListFromCommaSeparatedString(commaSeparatedLocations);
        Iterator<String> iter = newList.iterator();
        while (iter.hasNext()) {
            String s = iter.next();
            if (s.equals(locationToBeDeleted)) {
                iter.remove();
                break;
            }
        }

        return createCommaSeparatedStringFromList(newList);
    }

    public static List<String> createListFromCommaSeparatedString(String toBeSplit) {
        return new ArrayList<>(Arrays.asList(toBeSplit.split(",")));
    }

    public static String createCommaSeparatedStringFromList(List<String> data) {
        StringBuilder builder = new StringBuilder();
        for (String s : data) {
            builder.append(s).append(",");
        }
        builder.deleteCharAt(builder.length() - 1); // remove trailing ','

        return builder.toString();
    }
}
