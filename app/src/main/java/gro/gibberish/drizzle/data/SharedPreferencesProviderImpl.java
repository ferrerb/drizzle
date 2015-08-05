package gro.gibberish.drizzle.data;

import android.content.SharedPreferences;

import javax.inject.Inject;

public class SharedPreferencesProviderImpl implements SharedPreferencesProvider {
    private static final String SP_LAST_REFRESH = "SP_LAST_REFRESH";
    private static final String LOCATIONS = "locations";
    private static final String FORECAST_FILE_APPENDED = "cast";
    @Inject SharedPreferences sharedPreferences;

    public SharedPreferencesProviderImpl() {
    }

    @Override
    public void setLocationsString(String commaSeparatedLocations) {
        sharedPreferences.edit().putString(LOCATIONS, commaSeparatedLocations).apply();
    }

    @Override
    public String getLocationsString() {
        return sharedPreferences.getString(LOCATIONS, "");
    }

    @Override
    public void setLastLocationListRefreshTime(long refreshTimeInMillis) {
        sharedPreferences.edit().putLong(SP_LAST_REFRESH, refreshTimeInMillis).apply();
    }

    @Override
    public long getLastLocationListRefreshTime() {
        return sharedPreferences.getLong(SP_LAST_REFRESH, 0L);
    }

    @Override
    public void setLastRefreshTimeLocationForecast(long refreshTimeInMillis, String locationId) {
        sharedPreferences.edit().putLong(locationId + FORECAST_FILE_APPENDED, refreshTimeInMillis).apply();
    }

    @Override
    public long getLastRefreshTimeLocationForecast(String locationId) {
        return sharedPreferences.getLong(locationId, 0L);
    }
}
