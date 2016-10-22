package gro.gibberish.drizzle.data_external.weather_beans_owm;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Change this
 */
public class WeatherList implements Serializable{
    public WeatherList() {}

    private long dt;
    private Temp temp;
    private int humidity;
    private double pressure;
    private List<Weather> weather = new ArrayList< Weather >();

    public long getDt() {
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

    public double getPressure() {
        return pressure;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

}
