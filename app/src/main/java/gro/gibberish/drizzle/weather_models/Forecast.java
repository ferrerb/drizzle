package gro.gibberish.drizzle.weather_models;

import java.util.Map;

/**
 * Created by mag on 7/19/16.
 */
public class Forecast {
    private String locationId;
    private String city;
    private String state;
    private String country;
    private short zipCode;

    private Map<String, DailyWeather> forecastMap;
}
