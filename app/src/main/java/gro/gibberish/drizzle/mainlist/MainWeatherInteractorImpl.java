package gro.gibberish.drizzle.mainlist;

import java.util.List;

import javax.inject.Inject;

import gro.gibberish.drizzle.EventBusRx;
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
    @Inject EventBusRx eventBus;
    @Inject SharedPreferencesProvider sharedPreferencesProvider;
    String commaSeparatedLocations;

    public static MainWeatherInteractorImpl newInstance() {
        return new MainWeatherInteractorImpl();
    }

    private MainWeatherInteractorImpl() {}

    // TODO make interface/implementation of a sharedpreferences thing, impl having sharedprefernces
    @Override
    public void retrieveWeather() {
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
                        LocationModel.class, getActivity().getCacheDir(), s))
                .toList()
                .doOnError(getWeatherFromInternet())
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
                    getActivity().getCacheDir(),
                    Long.toString(loc.getId()))
                    .subscribe();
        }
    }

}
