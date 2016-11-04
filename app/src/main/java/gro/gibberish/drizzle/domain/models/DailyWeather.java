package gro.gibberish.drizzle.domain.models;

/**
 * Internal model for the current weather in a location
 */
public class DailyWeather {
    private final int locationId;
    private String city;
    private String state;
    private String country;
    private short zipCode;

    private float currentTemperature;
    private float highTemperature;
    private float lowTemperature;
    private float currentHumidity;

    public DailyWeather(int locationId) {
        this.locationId = locationId;
    }

    public int getLocationId() {
        return locationId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public short getZipCode() {
        return zipCode;
    }

    public void setZipCode(short zipCode) {
        this.zipCode = zipCode;
    }

    public float getCurrentTemperature() {
        return currentTemperature;
    }

    public void setCurrentTemperature(float currentTemperature) {
        this.currentTemperature = currentTemperature;
    }

    public float getHighTemperature() {
        return highTemperature;
    }

    public void setHighTemperature(float highTemperature) {
        this.highTemperature = highTemperature;
    }

    public float getLowTemperature() {
        return lowTemperature;
    }

    public void setLowTemperature(float lowTemperature) {
        this.lowTemperature = lowTemperature;
    }

    public float getCurrentHumidity() {
        return currentHumidity;
    }

    public void setCurrentHumidity(float currentHumidity) {
        this.currentHumidity = currentHumidity;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("Daily weather for " + city + "\n");
        stringBuilder.append("Current temperature is " + String.valueOf(currentTemperature) + "\n");
        stringBuilder.append("Expected high is " + String.valueOf(highTemperature) + "\n");
        stringBuilder.append("Expected low is " + String.valueOf(lowTemperature) + "\n");
        stringBuilder.append("Current humidity is " + String.valueOf(currentHumidity));

        return stringBuilder.toString();
    }
}
