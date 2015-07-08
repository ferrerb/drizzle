package gro.gibberish.drizzle.mainlist;

import java.util.List;

import gro.gibberish.drizzle.EventBusRx;
import gro.gibberish.drizzle.events.LocationListEvent;
import rx.Subscription;

public class MainPresenterImpl implements MainPresenter {
    private EventBusRx eventBus;
    private MainView mainView;
    private MainWeatherRetriever mainWeatherRetriever;

    public static MainPresenterImpl newInstance(MainView mainView) {
        MainPresenterImpl newInstance = new MainPresenterImpl();
        newInstance.eventBus = EventBusRx.INSTANCE;
        newInstance.mainView = mainView;
        newInstance.mainWeatherRetriever = MainWeatherRetrieverImpl.newInstance();
        return newInstance;
    }

    @Override
    public void onResume() {
        Subscription retrieveWeatherSubscription = eventBus.get()
                .ofType(LocationListEvent.class)
                .subscribe(
                        event -> mainView.fillRecyclerView(event.getData()),
                        Throwable::getStackTrace,
                        () -> {}
                );
        mainWeatherRetriever.retrieveWeather();
    }

    @Override
    public void onListClick(String id) {
        // launch detail activity
    }
}
