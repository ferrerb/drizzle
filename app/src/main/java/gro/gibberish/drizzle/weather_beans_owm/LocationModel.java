package gro.gibberish.drizzle.weather_beans_owm;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A model for a returned JSON object for a specific location from OpenWeatherMap
 */
public class LocationModel implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private long id;
    private Coord coord;
    private List<Weather> weather = new ArrayList<Weather>();
    private Main main;
    private long dt;

    public LocationModel() {}

    public void setCoord(Coord coord) {
        this.coord = coord;
    }

    public Coord getCoord() {
        return coord;
    }

    public long getDt() {
        return dt;
    }

    public void setDt(int dt) {
        this.dt = dt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public void setWeather(List<Weather> weather) {
        this.weather = weather;
    }

    public Main getMain() {
        return main;
    }

    public void setMain() {
        this.main = main;
    }

    public static class Coord implements Serializable {
        private double lon;
        private double lat;

        public void setLon(double lon) {
            this.lon = lon;
        }

        public double getLon() {
            return lon;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }

        public double getLat() {
            return lat;
        }
    }

    public static class Main implements Serializable {
        private double temp;
        private double humidity;
        private double pressure;

        public Main() {}

        public double getTemp() {
            return temp;
        }

        public void setTemp(double temp) {
            this.temp = temp;
        }

        public double getHumidity() {
            return humidity;
        }

        public void setHumidity(double humidity) {
            this.humidity = humidity;
        }

        public double getPressure() {
            return pressure;
        }

        public void setPressure(double pressure) {
            this.pressure = pressure;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LocationModel that = (LocationModel) o;

        if (dt != that.dt) return false;
        if (main != null ? !main.equals(that.main) : that.main != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (weather != null ? !weather.equals(that.weather) : that.weather != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (weather != null ? weather.hashCode() : 0);
        result = 31 * result + (main != null ? main.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "City: " + name + ", current temp: " + Double.toString(main.temp) +
                    ", current humidity: " + Double.toString(main.humidity);
    }
}


