package gro.gibberish.drizzle.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A model for a returned JSON object for a specific location from OpenWeatherMap
 * TODO consider separating the classes for list/temp etc their own java file
 */
public class LocationForecastModel implements BaseModel{
    @SerializedName("list")
    private List<weatherList> weatherList = new ArrayList<weatherList>();

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
        private List<LocationModel.Weather> weather = new ArrayList< LocationModel.Weather >();

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

        public java.util.List<LocationModel.Weather> getWeather() {
            return weather;
        }

        public void setWeather(List<LocationModel.Weather> weather) {
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
}
