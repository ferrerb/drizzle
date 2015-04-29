package gro.gibberish.drizzle.data;

import gro.gibberish.drizzle.models.LocationForecastModel;
import gro.gibberish.drizzle.models.LocationModel;
import gro.gibberish.drizzle.models.MultipleLocationModel;
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
        Observable<LocationModel> searchLocationByZip(
                @Query("zip") String zip,
                @Query("units") String units,
                @Query("APPID") String api);

        @GET("/weather")
        Observable<LocationModel> getLocationDetailWeather(
                @Query("id") String id,
                @Query("units") String units,
                @Query("APPID") String api);

        @GET("/forecast/daily")
        Observable<LocationForecastModel> getLocationDailyForecast(
                @Query("id") String id,
                @Query("cnt") String count,
                @Query("units") String units,
                @Query("APPID") String api);

        @GET("/group")
        Observable<MultipleLocationModel> getAllLocationsWeather(
                @Query("id") String id,
                @Query("units") String units,
                @Query("APPID") String api);
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
