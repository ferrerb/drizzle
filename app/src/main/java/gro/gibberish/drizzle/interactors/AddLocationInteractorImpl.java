package gro.gibberish.drizzle.interactors;

import javax.inject.Inject;

import gro.gibberish.drizzle.EventBusRx;
import gro.gibberish.drizzle.data.LocationObservableProvider;

public class AddLocationInteractorImpl implements AddLocationInteractor {
    private LocationObservableProvider locationObservableProvider;
    private EventBusRx eventBus;

    @Inject
    public AddLocationInteractorImpl(LocationObservableProvider locationObservableProvider, EventBusRx eventBus) {
        this.locationObservableProvider = locationObservableProvider;
        this.eventBus = eventBus;
    }

    public void retrieveGpsLocation() {

    }
}
