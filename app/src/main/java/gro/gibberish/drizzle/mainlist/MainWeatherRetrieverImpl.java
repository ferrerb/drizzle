package gro.gibberish.drizzle.mainlist;

import gro.gibberish.drizzle.EventBusRx;
import gro.gibberish.drizzle.data.ApiProvider;
import gro.gibberish.drizzle.data.FileHandler;
import gro.gibberish.drizzle.data.LocationsStringHelper;
import gro.gibberish.drizzle.events.LocationListEvent;
import gro.gibberish.drizzle.models.LocationModel;
import gro.gibberish.drizzle.models.MultipleLocationModel;
import rx.Observable;

public class MainWeatherRetrieverImpl implements MainWeatherRetriever {
    EventBusRx eventBus;

    public static MainWeatherRetrieverImpl newInstance() {
        MainWeatherRetrieverImpl newInstance = new MainWeatherRetrieverImpl();
        newInstance.eventBus = EventBusRx.INSTANCE;
        return newInstance;
    }

    @Override
    public void retrieveWeather() {
        long lastRefresh = sharedPreferences.getLong(SP_LAST_REFRESH, 0L);
        boolean needsRefresh = (System.currentTimeMillis() - lastRefresh) > ONE_HOUR_MS;
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
                .subscribe(
                        weatherList -> eventBus.post(new LocationListEvent(weatherList)),
                        Throwable::printStackTrace,
                        () -> {}
                );
    }

}
