package gro.gibberish.drizzle.data_external;

import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;

import gro.gibberish.drizzle.util.LocationsStringHelper;

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
        String actual = LocationsStringHelper.createCommaSeparatedStringFromList(expectedList);
        assertEquals(locationStrings, actual);
    }

    @Test
    public void testCommaSeparatedStringToList() {
        List<String> actual =
                new ArrayList<>(LocationsStringHelper.createListFromCommaSeparatedString(locationStrings));
        assertEquals(expectedList, actual);
    }

    @Test
    public void testDeleteLocationWithOneLocationString() {
        String toBeDeleted = "a";
        String expected = "";
        String actual = LocationsStringHelper.deleteLocationFromString(toBeDeleted, toBeDeleted);
        assertEquals(expected, actual);
    }

    @Test
    public void testDeleteLocationFromCommaSeparatedString() {
        String toBeRemoved = "b";
        String expected = "a,c";
        String actual = LocationsStringHelper.deleteLocationFromString(toBeRemoved, locationStrings);
        assertEquals(expected, actual);
    }

    @Test
    public void testAddLocationToCommaSeparatedString() {
        String toBeAdded = "f";
        String expected = "a,b,c,f";
        String actual = LocationsStringHelper.addLocationToCommaSeparatedString(toBeAdded, locationStrings);
        assertEquals(expected, actual);
    }

    @Test
    public void testAddLocationToEmptyString() {
        String toBeAdded = "f";
        String expected = "f";
        String actual = LocationsStringHelper.addLocationToCommaSeparatedString(toBeAdded, "");
        assertEquals(expected, actual);
    }
}
