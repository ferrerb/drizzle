package gro.gibberish.drizzle.data;

import gro.gibberish.drizzle.models.LocationForecastModel;
import gro.gibberish.drizzle.models.LocationModel;
import gro.gibberish.drizzle.models.MultipleLocationModel;
import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

/**
 * Change this
 */
public interface OpenWeatherService {
    // The zip query string must have ",us", or some country abbreviation,  appended to indicate a US city
    @GET("/weather")
    Observable<LocationModel> getLocationByZip(
            @Query("zip") String zip,
            @Query("units") String units,
            @Query("APPID") String api);

    @GET("/weather")
    Observable<LocationModel> getLocationByCoords(
            @Query("lat") String lat,
            @Query("lon") String lon,
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
