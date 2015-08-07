package gro.gibberish.drizzle.mainlist;

import java.util.List;

import javax.inject.Inject;

import gro.gibberish.drizzle.EventBusRx;
import gro.gibberish.drizzle.events.LocationListEvent;
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
                // TODO Dont do filter and oftype, obviously. Avoid using 'event' classes maybe?
                // TODO ^^ could this even work if checking for List<>? if generics type erasure hmm
                .filter(data -> data.getClass().equals(List.class))
                .ofType(LocationListEvent.class)
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
