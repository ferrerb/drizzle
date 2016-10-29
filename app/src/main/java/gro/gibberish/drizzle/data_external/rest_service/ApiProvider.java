package gro.gibberish.drizzle.data_external.rest_service;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Holds a static instance of the rest adapter for accessing the OpenWeather API
 */
public final class ApiProvider {
    // TODO Convert to enum
    private ApiProvider() {}

    private static final String BASE_URL ="http://api.openweathermap.org/data/2.5";

    private static final Retrofit RETROFIT = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    
    private static final OpenWeatherService WEATHER_SERVICE = RETROFIT.create(OpenWeatherService.class);

    public static OpenWeatherService getWeatherService() {
        return WEATHER_SERVICE;
    }

}
