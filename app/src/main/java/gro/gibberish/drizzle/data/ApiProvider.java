package gro.gibberish.drizzle.data;

import retrofit.RestAdapter;

/**
 * Holds a static instance of the rest adapter for accessing the OpenWeather API
 */
public final class ApiProvider {
    private ApiProvider() {}

    private static final String SERVICE_ENDPOINT ="http://api.openweathermap.org/data/2.5";

    private static final RestAdapter REST_ADAPTER = new RestAdapter.Builder()
            .setEndpoint(SERVICE_ENDPOINT)
            .build();
    
    private static final OpenWeatherService WEATHER_SERVICE = REST_ADAPTER.create(OpenWeatherService.class);

    public static OpenWeatherService getWeatherService() {
        return WEATHER_SERVICE;
    }

}
