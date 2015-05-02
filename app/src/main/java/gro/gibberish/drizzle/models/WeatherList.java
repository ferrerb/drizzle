package gro.gibberish.drizzle.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Change this
 */
public class WeatherList implements Serializable{
    public WeatherList() {}
    
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
