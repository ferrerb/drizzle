package gro.gibberish.drizzle.interactors;

import android.content.Context;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import gro.gibberish.drizzle.EventBusRx;
import gro.gibberish.drizzle.R;
import gro.gibberish.drizzle.data.ApiProvider;
import gro.gibberish.drizzle.data.FileHandler;
import gro.gibberish.drizzle.data.OpenWeatherService;
import gro.gibberish.drizzle.data.SharedPrefs;
import gro.gibberish.drizzle.util.LocationsStringHelper;
import gro.gibberish.drizzle.events.LocationListEvent;
import gro.gibberish.drizzle.models.LocationModel;
import gro.gibberish.drizzle.models.MultipleLocationModel;
import rx.Observable;
import rx.schedulers.Schedulers;

public class MainWeatherInteractorImpl implements MainWeatherInteractor {
    private EventBusRx eventBus;
    private SharedPrefs sharedPrefs;
    private FileHandler fileHandler;
    private OpenWeatherService openWeatherService;
    @Inject @Named("api_key") String apiKey;

    private String commaSeparatedLocations;

    @Inject
    public MainWeatherInteractorImpl(
            EventBusRx eventBus, SharedPrefs sharedPrefs,
            FileHandler fileHandler, OpenWeatherService openWeatherService) {
        this.eventBus = eventBus;
        this.sharedPrefs = sharedPrefs;
        // TODO push this context to filehandler instead of here, ie make filehandler not static etc
        this.fileHandler = fileHandler;
        this.openWeatherService = openWeatherService;
    }

    @Override
    public void retrieveWeather() {
        //commaSeparatedLocations = sharedPrefs.getAllLocationsString();
        commaSeparatedLocations = "1851632,2172797";
        final int oneHourInMilliSeconds = 3600000;
        long lastRefresh = sharedPrefs.getLastLocationListRefreshTime();
        boolean needsRefresh = (System.currentTimeMillis() - lastRefresh) > oneHourInMilliSeconds;
        if (needsRefresh) {
            getWeatherFromInternet();
        } else {
            getWeatherFromFile();
        }
    }

    private void getWeatherFromInternet() {
        // TODO PRovide the apikey from the rootappmodule?
        openWeatherService.getAllLocationsWeather(commaSeparatedLocations, "imperial", apiKey)
                .retry(3)
                .doOnNext(weatherData -> saveLocationWeatherToSeparateFiles(weatherData.getLocationList()))
                .map(MultipleLocationModel::getLocationList)
                .subscribeOn(Schedulers.io())
                .subscribe(
                        weatherData -> eventBus.post(new LocationListEvent(weatherData)),
                        Throwable::printStackTrace,
                        () -> sharedPrefs.setLastLocationListRefreshTime(System.currentTimeMillis())
                );
    }

    private void getWeatherFromFile() {
        Observable.from(LocationsStringHelper.createListFromCommaSeparatedString(commaSeparatedLocations))
                .flatMap(s -> fileHandler.getSerializedObjectFromFile(
                        LocationModel.class, s))
                .toList()
                .doOnError(e-> getWeatherFromInternet())
                .subscribeOn(Schedulers.io())
                .subscribe(
                        weatherList -> eventBus.post(new LocationListEvent(weatherList)),
                        Throwable::printStackTrace,
                        () -> {}
                );
    }

    private void saveLocationWeatherToSeparateFiles(List<LocationModel> data) {
        for ( LocationModel loc : data ) {
            fileHandler.saveSerializableObjectToFile(
                    loc,
                    Long.toString(loc.getId()))
                    .subscribe();
        }
    }

}
