package gro.gibberish.drizzle.weather_beans;

import java.io.Serializable;

/**
 * Change this
 */
public class Weather implements Serializable{
    private String main;
    private String description;

    public Weather() {}

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
