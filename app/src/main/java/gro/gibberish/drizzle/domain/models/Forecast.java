package gro.gibberish.drizzle.domain.models;

import java.util.Map;

/**
 * Created by mag on 7/19/16.
 */
// TODO DO I NEED THIS CLASS
public class Forecast {
    private String locationId;
    private String city;
    private String state;
    private String country;
    private short zipCode;

    private Map<String, DailyWeather> forecastMap;

    public Forecast() {

    }

    @Override
    public String toString() {
        return "Forecast for " + city + "\n" +
                "";
    }
}
