package gro.gibberish.drizzle.mainlist;

import java.util.List;

import gro.gibberish.drizzle.EventBusRx;
import gro.gibberish.drizzle.events.LocationListEvent;
import rx.Subscription;

public class MainPresenterImpl implements MainPresenter {
    private EventBusRx eventBus;
    private MainView mainView;
    private MainWeatherInteractor mainWeatherInteractor;

    public static MainPresenterImpl newInstance(MainView mainView) {
        MainPresenterImpl newInstance = new MainPresenterImpl();
        newInstance.eventBus = EventBusRx.INSTANCE;
        newInstance.mainView = mainView;
        newInstance.mainWeatherInteractor = MainWeatherInteractorImpl.newInstance();
        return newInstance;
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
}
