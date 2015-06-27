package gro.gibberish.drizzle.data;

import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;

public class LocationsStringHelperTest{
    private String locationStrings = "a,b,c";
    private List<String> expectedList = new ArrayList<>();

    @Before
    public void setTestVariables() {
        expectedList.add("a");
        expectedList.add("b");
        expectedList.add("c");
    }

    @Test
    public void testListToCommaSeparatedString() {
        String stringFromList = LocationsStringHelper.createCommaSeparatedStringFromList(expectedList);
        assertEquals(locationStrings, stringFromList);
    }

    @Test
    public void testCommaSeparatedStringToList() {
        List<String> returnedList =
                new ArrayList<>(LocationsStringHelper.createListFromCommaSeparatedString(locationStrings));
        assertEquals(expectedList, returnedList);
    }

    @Test
    public void testDeleteLocationFromCommaSeparatedString() {
        String toBeRemoved = "b";
        String expectedString = "a,c";
        String locationStringsAfterDelete =
                LocationsStringHelper.deleteLocationFromString(locationStrings, toBeRemoved);
        assertEquals(expectedString, locationStringsAfterDelete);
    }
}
