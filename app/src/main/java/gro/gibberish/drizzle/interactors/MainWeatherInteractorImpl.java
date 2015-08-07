package gro.gibberish.drizzle.interactors;

import android.content.Context;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import gro.gibberish.drizzle.EventBusRx;
import gro.gibberish.drizzle.R;
import gro.gibberish.drizzle.data.ApiProvider;
import gro.gibberish.drizzle.data.FileHandler;
import gro.gibberish.drizzle.data.SharedPreferencesProvider;
import gro.gibberish.drizzle.util.LocationsStringHelper;
import gro.gibberish.drizzle.events.LocationListEvent;
import gro.gibberish.drizzle.models.LocationModel;
import gro.gibberish.drizzle.models.MultipleLocationModel;
import rx.Observable;
import rx.schedulers.Schedulers;

public class MainWeatherInteractorImpl implements MainWeatherInteractor {
    private EventBusRx eventBus;
    private SharedPreferencesProvider sharedPreferencesProvider;
    private Context activityContext;
    private String commaSeparatedLocations;

    @Inject
    public MainWeatherInteractorImpl(
            EventBusRx eventBus, SharedPreferencesProvider sharedPreferencesProvider,
            @Named("activity") Context activityContext) {
        this.eventBus = eventBus;
        this.sharedPreferencesProvider = sharedPreferencesProvider;
        this.activityContext = activityContext;
    }

    @Override
    public void retrieveWeather() {
        commaSeparatedLocations = sharedPreferencesProvider.getAllLocationsString();
        final int oneHourInMilliSeconds = 3600000;
        long lastRefresh = sharedPreferencesProvider.getLastLocationListRefreshTime();
        boolean needsRefresh = (System.currentTimeMillis() - lastRefresh) > oneHourInMilliSeconds;
        if (needsRefresh) {
            getWeatherFromInternet();
        } else {
            getWeatherFromFile();
        }
    }

    private void getWeatherFromInternet() {
        String apiKey = activityContext.getResources().getString(R.string.api_key);
        ApiProvider.getWeatherService().getAllLocationsWeather(commaSeparatedLocations, "imperial", apiKey)
                .retry(3)
                .doOnNext(weatherData -> saveLocationWeatherToSeparateFiles(weatherData.getLocationList()))
                .map(MultipleLocationModel::getLocationList)
                .subscribeOn(Schedulers.io())
                .subscribe(
                        weatherData -> eventBus.post(new LocationListEvent(weatherData)),
                        Throwable::printStackTrace,
                        () -> sharedPreferencesProvider.setLastLocationListRefreshTime(System.currentTimeMillis())
                );
    }

    private void getWeatherFromFile() {
        Observable.from(LocationsStringHelper.createListFromCommaSeparatedString(commaSeparatedLocations))
                .flatMap(s -> FileHandler.getSerializedObjectFromFile(
                        LocationModel.class, activityContext.getCacheDir(), s))
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
            FileHandler.saveSerializableObjectToFile(
                    loc,
                    activityContext.getCacheDir(),
                    Long.toString(loc.getId()))
                    .subscribe();
        }
    }

}
