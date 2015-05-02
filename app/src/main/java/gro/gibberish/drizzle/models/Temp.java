package gro.gibberish.drizzle.models;

import java.io.Serializable;

/**
 * Change this
 */
public class Temp implements Serializable {
    public Temp() {}

    private double max;
    private double min;

    public double getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public double getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }
}
