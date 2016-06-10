package gro.gibberish.drizzle.data;

import retrofit2.Retrofit;

/**
 * Holds a static instance of the rest adapter for accessing the OpenWeather API
 */
public final class ApiProvider {
    private ApiProvider() {}

    private static final String BASE_URL ="http://api.openweathermap.org/data/2.5";

    private static final Retrofit RETROFIT = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .build();
    
    private static final OpenWeatherService WEATHER_SERVICE = RETROFIT.create(OpenWeatherService.class);

    public static OpenWeatherService getWeatherService() {
        return WEATHER_SERVICE;
    }

}
