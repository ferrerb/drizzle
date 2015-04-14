package gro.gibberish.drizzle.http;

import gro.gibberish.drizzle.R;
import gro.gibberish.drizzle.models.LocationModel;
import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by mag on 4/3/15.
 */
public interface WeatherDownloadInterface {
    // The zip query string must have ",us" appended to indicate a US city
    @GET("weather")
    Observable<LocationModel> getLocationDetailWeather(@Query("zip") String zip,
                                                       @Query("APPID") String API_KEY);
}
