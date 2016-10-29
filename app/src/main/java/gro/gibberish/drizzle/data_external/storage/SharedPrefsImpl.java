package gro.gibberish.drizzle.data_external.storage;

import android.content.SharedPreferences;

import javax.inject.Inject;

public class SharedPrefsImpl implements SharedPrefs {
    private static final String SP_LAST_REFRESH = "SP_LAST_REFRESH";
    private static final String LOCATIONS = "locations";
    private static final String FORECAST_FILE_APPENDED = "cast";
    private SharedPreferences sharedPreferences;

    @Inject
    public SharedPrefsImpl(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    @Override
    public void setAllLocationsString(String commaSeparatedLocations) {
        sharedPreferences.edit().putString(LOCATIONS, commaSeparatedLocations).apply();
    }

    @Override
    public String getAllLocationsString() {
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
        return sharedPreferences.getLong(locationId + FORECAST_FILE_APPENDED, 0L);
    }
}
