package gro.gibberish.drizzle.interactors;

import android.location.Location;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import gro.gibberish.drizzle.EventBusRx;
import gro.gibberish.drizzle.data.GpsLocationObservable;
import gro.gibberish.drizzle.events.GpsLocationEvent;
import rx.Subscription;

public class AddLocationInteractorImpl implements AddLocationInteractor {
    private GpsLocationObservable gpsLocationObservable;
    private EventBusRx eventBus;

    @Inject
    public AddLocationInteractorImpl(
            GpsLocationObservable gpsLocationObservable, EventBusRx eventBus) {
        this.gpsLocationObservable = gpsLocationObservable;
        this.eventBus = eventBus;
    }

    public void retrieveGpsLocation() {
        // TODO chain observable from gpslocation
        Subscription locationSubscription = gpsLocationObservable.retrieveGpsCoordsSingleUpdate()
                .map(location -> getGpsCoordsFromLocationObject(location))
                .subscribe(
                        listOfCoordinates -> eventBus.post(new GpsLocationEvent(listOfCoordinates)),
                        Throwable::printStackTrace,
                        () -> {}
                );

    }

    private List<Double> getGpsCoordsFromLocationObject(Location location) {
        List<Double> coordinatesList = new ArrayList<>();
        coordinatesList.add(location.getLatitude());
        coordinatesList.add(location.getLongitude());
        return coordinatesList;
    }
}
