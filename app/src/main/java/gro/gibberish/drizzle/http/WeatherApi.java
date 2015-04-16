package gro.gibberish.drizzle.http;

import java.util.List;

import gro.gibberish.drizzle.models.LocationModel;
import retrofit.RestAdapter;
import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

/**
 * Holds a static instance of the rest adapter for accessing the OpenWeather API
 */
public class WeatherApi {
    private static final String SERVICE_ENDPOINT ="http://api.openweathermap.org/data/2.5";

    public interface WeatherService {
        // The zip query string must have ",us" appended to indicate a US city
        // It would be better to use the city id, but that would involve searching a massive txt file
        @GET("/weather")
        Observable<LocationModel> getLocationDetailWeather(
                @Query("zip") String zip,
                @Query("units") String units,
                @Query("APPID") String api);

        @GET("/group")
        Observable<List<LocationModel>> getAllLocationsWeather(@Query("zip") String zip);
    }

    private static final RestAdapter REST_ADAPTER = new RestAdapter.Builder()
            .setEndpoint(SERVICE_ENDPOINT)
            .build();
    private static final WeatherService WEATHER_SERVICE = REST_ADAPTER.create(WeatherService.class);

    //
    public static WeatherService getWeatherService() {
        return WEATHER_SERVICE;
    }

}
