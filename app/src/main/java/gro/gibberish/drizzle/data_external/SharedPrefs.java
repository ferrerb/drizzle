package gro.gibberish.drizzle.data_external;

public interface SharedPrefs {
    void setAllLocationsString(String commaSeparatedLocations);

    String getAllLocationsString();

    void setLastLocationListRefreshTime(long lastRefreshTime);

    long getLastLocationListRefreshTime();

    void setLastRefreshTimeLocationForecast(long lastRefreshTime, String locationId);

    long getLastRefreshTimeLocationForecast(String locationId);
}
