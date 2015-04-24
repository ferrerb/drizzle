package gro.gibberish.drizzle.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A model for a returned JSON object for a specific location from OpenWeatherMap
 * TODO consider separating the classes for list/temp etc their own java file
 */
public class LocationForecastModel implements BaseModel, Serializable{
    private static final long serialVersionUID = 4L;

    private City city;

    @SerializedName("list")
    private List<weatherList> weatherList = new ArrayList<weatherList>();

    public LocationForecastModel () {}

    public List<weatherList> getList() {
        return weatherList;
    }

    public void setList(List<weatherList> weatherList) {
        this.weatherList = weatherList;
    }

    public static class weatherList {
        private int dt;
        private Temp temp;
        private int humidity;
        private List<Weather> weather = new ArrayList< Weather >();

        public int getDt() {
            return dt;
        }

        public void setDt(int dt) {
            this.dt = dt;
        }

        public Temp getTemp() {
            return temp;
        }

        public void setTemp(Temp temp) {
            this.temp = temp;
        }

        public int getHumidity() {
            return humidity;
        }

        public void setHumidity(int humidity) {
            this.humidity = humidity;
        }

        public List<Weather> getWeather() {
            return weather;
        }

        public void setWeather(List<Weather> weather) {
            this.weather = weather;
        }
    }

    public static class Temp {
        private int max;
        private int min;

        public int getMax() {
            return max;
        }

        public void setMax(int max) {
            this.max = max;
        }

        public int getMin() {
            return min;
        }

        public void setMin(int min) {
            this.min = min;
        }
    }

    public static class City {
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
