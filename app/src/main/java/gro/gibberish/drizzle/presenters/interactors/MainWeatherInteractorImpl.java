package gro.gibberish.drizzle.presenters.interactors;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import gro.gibberish.drizzle.EventBusRx;
import gro.gibberish.drizzle.data_external.storage.FileHandler;
import gro.gibberish.drizzle.data_external.rest_service.OpenWeatherService;
import gro.gibberish.drizzle.data_external.storage.SharedPrefs;
import gro.gibberish.drizzle.util.LocationsStringHelper;
import gro.gibberish.drizzle.rxbus_events.WeatherListDownloadEvent;
import gro.gibberish.drizzle.data_external.model_net.LocationModel;
import gro.gibberish.drizzle.data_external.model_net.MultipleLocationModel;
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
        openWeatherService.getAllLocationsWeather(commaSeparatedLocations, "imperial", apiKey)
                .retry(3)
                .doOnNext(weatherData -> saveLocationWeatherToSeparateFiles(weatherData.getLocationList()))
                .map(MultipleLocationModel::getLocationList)
                .subscribeOn(Schedulers.io())
                .subscribe(
                        weatherData -> eventBus.post(new WeatherListDownloadEvent(weatherData)),
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
                        weatherList -> eventBus.post(new WeatherListDownloadEvent(weatherList)),
                        Throwable::printStackTrace,
                        () -> {
                        }
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

    @Override
    public void onPause() {
        // TODO unsubscribe
    }

}
