package gro.gibberish.drizzle.models;

/**
 * Created by mag on 4/2/15.
 */
public class LocationModel {
    public String name;
    public String main;
    public String icon;
    public float temp;

    public LocationModel(String name, String main, String icon, float temp) {
        this.name = name;
        this.main = main;
        this.icon = icon;
        this.temp = temp;
    }
}
