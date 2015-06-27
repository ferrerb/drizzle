package gro.gibberish.drizzle.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public final class LocationsStringHelper {

    private LocationsStringHelper() {
    }

    public static String deleteLocationFromString(
            String commaSeparatedLocations, String locationToBeDeleted) {
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
        List<String> list = new ArrayList<>(Arrays.asList(toBeSplit.split(",")));
        return list;
    }

    public static String createCommaSeparatedStringFromList(List<String> data) {
        StringBuilder builder = new StringBuilder();
        for (String s : data) {
            builder.append(s).append(",");
        }
        builder.deleteCharAt(builder.length() - 1);
        return builder.toString();
    }
}
