package gro.gibberish.drizzle.detail;

import javax.inject.Inject;

import gro.gibberish.drizzle.EventBusRx;
import gro.gibberish.drizzle.interactors.DetailWeatherInteractor;

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
    public void onResume() {
        // get some weather
    }

    @Override
    public void init(DetailView detailView) {
        this.detailView = detailView;
    }

    @Override
    public void onDeleteLocation(String locationToDelete) {
        // ???
    }
}
