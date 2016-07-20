package gro.gibberish.drizzle.weather_models;

/**
 * Internal model for the current weather in a location
 */
public class DailyWeather {
    private String locationId;
    private String city;
    private String state;
    private String country;
    private short zipCode;

    private int currentTemperature;
    private int HighTemperature;
    private int lowTemperature;
    private int humidity;
}
