package gro.gibberish.drizzle.data;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Change this
 */
public final class LocationsHandler {
    private LocationsHandler() {}

    public static String deleteLocation(String original, String deleteMe) {
        List<String> newList = Arrays.asList(original.split(","));
        Iterator<String> mIter = newList.iterator();

        while (mIter.hasNext()) {
            String s = mIter.next();
            if (s.equals(deleteMe)) {
                mIter.remove();
                break;
            }
        }

        return stringArrayToCommaString(newList);
    }

    private static String stringArrayToCommaString(List<String> data) {
        StringBuilder builder = new StringBuilder();
        for (String s : data) {
            builder.append(s).append(",");
        }
        builder.deleteCharAt(builder.length() - 1);
        return builder.toString();
    }
}
