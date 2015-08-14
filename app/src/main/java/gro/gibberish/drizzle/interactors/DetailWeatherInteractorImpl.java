package gro.gibberish.drizzle.interactors;

import javax.inject.Inject;
import javax.inject.Named;

import gro.gibberish.drizzle.EventBusRx;
import gro.gibberish.drizzle.data.FileHandler;
import gro.gibberish.drizzle.data.OpenWeatherService;
import gro.gibberish.drizzle.data.SharedPrefs;

public class DetailWeatherInteractorImpl implements DetailWeatherInteractor {
    private EventBusRx eventBus;
    private SharedPrefs sharedPrefs;
    private FileHandler fileHandler;
    private OpenWeatherService openWeatherService;
    @Inject @Named("api_key") String apiKey;

    private String commaSeparatedLocations;

    @Inject
    public DetailWeatherInteractorImpl(
            EventBusRx eventBus, SharedPrefs sharedPrefs,
            FileHandler fileHandler, OpenWeatherService openWeatherService) {
        this.eventBus = eventBus;
        this.sharedPrefs = sharedPrefs;
        this.fileHandler = fileHandler;
        this.openWeatherService = openWeatherService;
    }

    @Override
    public void retrieveCurrentWeather() {

    }

    @Override
    public void retrieveForecastWeather() {

    }
}
