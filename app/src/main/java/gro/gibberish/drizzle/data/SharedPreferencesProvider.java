package gro.gibberish.drizzle.data;

public interface SharedPreferencesProvider {
    void setLocationsString(String commaSeparatedLocations);

    String getLocationsString();

    void setLastLocationListRefreshTime(long lastRefreshTime);

    long getLastLocationListRefreshTime();

    void setLastRefreshTimeLocationForecast(long lastRefreshTime, String locationId);

    long getLastRefreshTimeLocationForecast(String locationId);
}
