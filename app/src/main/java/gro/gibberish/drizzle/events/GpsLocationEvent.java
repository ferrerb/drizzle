package gro.gibberish.drizzle.events;

import java.util.ArrayList;
import java.util.List;

public class GpsLocationEvent implements RxBusEvent {
    private List<Double> coordinatesList = new ArrayList<>();

    public GpsLocationEvent(List<Double> coordinatesList) {
        this.coordinatesList = coordinatesList;
    }

    public List<Double> getData() {return coordinatesList;}
}
