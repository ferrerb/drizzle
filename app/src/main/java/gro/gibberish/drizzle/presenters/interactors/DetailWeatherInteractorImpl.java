package gro.gibberish.drizzle.presenters.interactors;

import javax.inject.Inject;
import javax.inject.Named;

import gro.gibberish.drizzle.EventBusRx;
import gro.gibberish.drizzle.data_external.storage.FileHandler;
import gro.gibberish.drizzle.data_external.rest_service.OpenWeatherService;
import gro.gibberish.drizzle.data_external.storage.SharedPrefs;
import gro.gibberish.drizzle.rxbus_events.CurrentLocationWeatherEvent;
import gro.gibberish.drizzle.rxbus_events.CurrentLocationForecastEvent;
import gro.gibberish.drizzle.data_external.model_net.LocationForecastModel;
import gro.gibberish.drizzle.data_external.model_net.LocationModel;

public class DetailWeatherInteractorImpl implements DetailWeatherInteractor {
    private static final String DAY_COUNT = "5";
    private static final String FORECAST_FILE_APPENDED = "cast";

    private EventBusRx eventBus;
    private SharedPrefs sharedPrefs;
    private FileHandler fileHandler;
    private OpenWeatherService openWeatherService;
    @Inject @Named("api_key") String apiKey;
    private String currentLocationId;

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
    public void retrieveWeather() {
        retrieveCurrentWeather();
        retrieveForecastWeather();
    }

    private void retrieveCurrentWeather() {
        fileHandler.getSerializedObjectFromFile(LocationModel.class, currentLocationId)
                .subscribe(
                        currentWeather -> eventBus.post(new CurrentLocationWeatherEvent(currentWeather)),
                        System.err::println,
                        () -> {}
                );
    }

    private void retrieveForecastWeather() {
        final int oneHourInMilliSeconds = 3600000;
        long lastForecastRefresh = sharedPrefs.getLastRefreshTimeLocationForecast(currentLocationId);
        boolean needsRefresh = (System.currentTimeMillis() - lastForecastRefresh) > oneHourInMilliSeconds;
        if (needsRefresh) {
            getForecastFromInternet();
        } else {
            getForecastFromFile();
        }
    }

    private void getForecastFromFile() {
        fileHandler.getSerializedObjectFromFile(
                LocationForecastModel.class, currentLocationId + FORECAST_FILE_APPENDED)
                .subscribe(
                        forecast -> eventBus.post(new CurrentLocationForecastEvent(forecast)),
                        e -> {
                            System.err.println(e);
                            getForecastFromInternet();
                        },
                        () -> {}
                );
    }

    private void getForecastFromInternet() {
        openWeatherService.getLocationDailyForecast(
                currentLocationId, DAY_COUNT, "imperial", apiKey)
                .doOnNext(forecastData -> fileHandler.saveSerializableObjectToFile(
                        forecastData,
                        currentLocationId + FORECAST_FILE_APPENDED).subscribe())
                .subscribe(
                        forecast -> eventBus.post(new CurrentLocationForecastEvent(forecast)),
                        Throwable::printStackTrace,
                        () -> sharedPrefs.setLastRefreshTimeLocationForecast(
                                System.currentTimeMillis(),
                                currentLocationId + FORECAST_FILE_APPENDED
                                )
                );
    }

    @Override
    public void onPause() {
        // TODO unsubscribe
    }
}
