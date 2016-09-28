package gro.gibberish.drizzle.domain.models;

/**
 * Internal model for the current weather in a location
 */
public class DailyWeather {
    private String locationId;
    private String city;
    private String state;
    private String country;
    private short zipCode;

    private float currentTemperature;
    private float HighTemperature;
    private float lowTemperature;
    private float currentHumidity;
}
