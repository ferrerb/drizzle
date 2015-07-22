package gro.gibberish.drizzle.mainlist;

import java.util.List;

import gro.gibberish.drizzle.EventBusRx;
import gro.gibberish.drizzle.data.ApiProvider;
import gro.gibberish.drizzle.data.FileHandler;
import gro.gibberish.drizzle.util.LocationsStringHelper;
import gro.gibberish.drizzle.events.LocationListEvent;
import gro.gibberish.drizzle.models.LocationModel;
import gro.gibberish.drizzle.models.MultipleLocationModel;
import rx.Observable;
import rx.schedulers.Schedulers;

public class MainWeatherInteractorImpl implements MainWeatherInteractor {
    EventBusRx eventBus;
    String commaSeparatedLocations;

    public static MainWeatherInteractorImpl newInstance() {
        MainWeatherInteractorImpl newInstance = new MainWeatherInteractorImpl();
        newInstance.eventBus = EventBusRx.INSTANCE;
        return newInstance;
    }

    private MainWeatherInteractorImpl() {}

    @Override
    public void retrieveWeather() {
        final int oneHourInMilliSeconds = 3600000;
        long lastRefresh = sharedPreferences.getLong(SP_LAST_REFRESH, 0L);
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
                        () -> sharedPreferences.edit().putLong(SP_LAST_REFRESH, System.currentTimeMillis()).apply()
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
