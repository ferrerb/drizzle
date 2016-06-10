package gro.gibberish.drizzle.location_list;

import javax.inject.Inject;

import gro.gibberish.drizzle.EventBusRx;
import gro.gibberish.drizzle.rxbus_events.WeatherListDownloadEvent;
import gro.gibberish.drizzle.interactors.MainWeatherInteractor;
import rx.Subscription;

public class MainPresenterImpl implements MainPresenter {
    private EventBusRx eventBus;
    private MainWeatherInteractor mainWeatherInteractor;
    private MainView mainView;

    @Inject
    public MainPresenterImpl(EventBusRx eventBus, MainWeatherInteractor mainWeatherInteractor) {
        this.eventBus = eventBus;
        this.mainWeatherInteractor = mainWeatherInteractor;
    }

    @Override
    public void onResume() {
        Subscription retrieveWeatherSubscription = eventBus.get()
                // TODO Avoid using 'event' classes maybe?
                // TODO unsubscribe in some onpause() method
                .ofType(WeatherListDownloadEvent.class)
                .subscribe(
                        event -> mainView.fillRecyclerView(event.getData()),
                        Throwable::getStackTrace,
                        () -> {
                        }
                );
        mainWeatherInteractor.retrieveWeather();
    }

    @Override
    public void onListClick(String id) {
        // launch detail activity
    }

    @Override
    public void init(MainView mainView) {
        this.mainView = mainView;
    }
}
