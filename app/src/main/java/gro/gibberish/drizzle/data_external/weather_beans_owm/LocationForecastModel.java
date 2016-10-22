package gro.gibberish.drizzle.data_external.weather_beans_owm;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A model for a returned JSON object for a specific location from OpenWeatherMap
 */
public class LocationForecastModel implements Serializable{
    private static final long serialVersionUID = 1L;

    private City city;

    @SerializedName("list")
    private List<WeatherList> weatherList = new ArrayList<WeatherList>();

    public LocationForecastModel () {}

    public List<WeatherList> getList() {
        return weatherList;
    }

    public void setList(List<WeatherList> weatherList) {
        this.weatherList = weatherList;
    }

    public static class City implements Serializable{
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LocationForecastModel that = (LocationForecastModel) o;

        if (city != null ? !city.equals(that.city) : that.city != null) return false;
        if (weatherList != null ? !weatherList.equals(that.weatherList) : that.weatherList != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = city != null ? city.hashCode() : 0;
        result = 31 * result + (weatherList != null ? weatherList.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "City: " + city.name;
    }
}
