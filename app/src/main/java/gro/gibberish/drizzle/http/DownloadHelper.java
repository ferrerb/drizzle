package gro.gibberish.drizzle.http;

import org.json.JSONObject;

import java.util.List;

import gro.gibberish.drizzle.models.LocationModel;
import retrofit.RestAdapter;
import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by mag on 4/2/15.
 */
public class DownloadHelper {
    private static final String SERVICE_ENDPOINT ="http://api.openweathermap.org/data/2.5/";
    private WeatherDownloadInterface mService;
    private String locations;

    public DownloadHelper(String s) {
        locations = s;
        RestAdapter rest = new RestAdapter.Builder()
                .setEndpoint(SERVICE_ENDPOINT)
                .build();
        mService = rest.create(WeatherDownloadInterface.class);
    }

    interface WeatherDownloadInterface {
        @GET("weather")
        Observable<String> getLocationDetailWeather(@Query("zip") String zip);

        @GET("group")
        Observable<String> getAllLocationsWeather(@Query("zip") String zip);
    }

    public Observable<LocationModel> getLocationDetailWeather(String s) {
        return null;
    }
}
