package gro.gibberish.drizzle.detail;

import javax.inject.Inject;

import gro.gibberish.drizzle.EventBusRx;
import gro.gibberish.drizzle.events.CurrentLocationForecastEvent;
import gro.gibberish.drizzle.events.CurrentLocationWeatherEvent;
import gro.gibberish.drizzle.events.RxBusEvent;
import gro.gibberish.drizzle.interactors.DetailWeatherInteractor;
import gro.gibberish.drizzle.util.LocationsStringHelper;
import rx.Subscription;

public class DetailPresenterImpl implements DetailPresenter {
    private EventBusRx eventBus;
    private DetailView detailView;
    private DetailWeatherInteractor detailWeatherInteractor;

    @Inject
    public DetailPresenterImpl(EventBusRx eventBus, DetailWeatherInteractor detailWeatherInteractor) {
        this.eventBus = eventBus;
        this.detailWeatherInteractor = detailWeatherInteractor;
    }


    @Override
    public void init(DetailView detailView) {
        this.detailView = detailView;
    }

    @Override
    public void onResume() {
        // TODO subscribe to eventbus to get locationId
        Subscription retrieveWeatherSubscription = eventBus.get()
                // TODO Avoid using 'event' classes maybe?
                // TODO unsubscribe in some onpause() method
                .ofType(RxBusEvent.class)
                .subscribe(
                        this::updateViewBasedOnEvent,
                        Throwable::getStackTrace,
                        () -> {}
                );
        detailWeatherInteractor.retrieveWeather();
    }

    // TODO do not like.
    private <T extends RxBusEvent> void updateViewBasedOnEvent(T event) {
        if (event.getClass() == CurrentLocationForecastEvent.class) {
            detailView.showForecast(((CurrentLocationForecastEvent) event).getData());
        } else if (event.getClass() == CurrentLocationWeatherEvent.class) {
            detailView.showCurrentWeather(((CurrentLocationWeatherEvent) event).getData());
        }
    }

    @Override
    public void onDeleteLocation(String locationToDelete) {
        // TODO Where should this actually happen?
//        String allLocations = sharedPreferences.getString("locations", "");
//        String locationsAfterDelete = LocationsStringHelper.deleteLocationFromString(currentLocation, allLocations);
//        sharedPreferences.edit().putString("locations", locationsAfterDelete).apply();
    }
}
