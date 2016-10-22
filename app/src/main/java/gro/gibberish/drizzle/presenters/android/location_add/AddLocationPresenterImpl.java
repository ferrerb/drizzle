package gro.gibberish.drizzle.presenters.android.location_add;

import javax.inject.Inject;

import gro.gibberish.drizzle.EventBusRx;
import gro.gibberish.drizzle.rxbus_events.GpsLocationEvent;
import rx.Subscription;

public class AddLocationPresenterImpl implements AddLocationPresenter {
    private AddLocationView addLocationView;
    private EventBusRx eventBus;

    @Inject
    public AddLocationPresenterImpl(EventBusRx eventBus) {
        this.eventBus = eventBus;
    }

    @Override
    public void init(AddLocationView addLocationView) {
        this.addLocationView = addLocationView;
    }

    @Override
    public void findLocationWithZip(String zipCode) {

    }

    @Override
    public void findLocationWithGps() {
        //  TODO Decide where to send this information, should this be in the Mainlist presenter?
        // TODO Or, have this also query the Weather API to get the location ID
        // TODO Subscribe to eventbus in the basefragment/activity??
        Subscription eventBusSubscrption = eventBus.get()
                .ofType(GpsLocationEvent.class)
                .subscribe(
                        event -> System.out.println("Yes"),
                        Throwable::printStackTrace,
                        () -> {
                        }
                );
    }
}
