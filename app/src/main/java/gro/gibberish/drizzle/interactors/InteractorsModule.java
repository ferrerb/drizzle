package gro.gibberish.drizzle.interactors;

import android.content.Context;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import gro.gibberish.drizzle.EventBusRx;
import gro.gibberish.drizzle.data.FileHandler;
import gro.gibberish.drizzle.data.OpenWeatherService;
import gro.gibberish.drizzle.data.SharedPrefs;

@Module(
        library=true,
        complete=false
)
public class InteractorsModule {
    // TODO Break up the interactors to be something like LocatoinCurrentWeather,LocationForecastWeather, ListWeather
    // TODO Then each could do either from file or from internet
    @Provides
    public MainWeatherInteractor provideMainWeatherInteractor(
            EventBusRx eventBus,
            SharedPrefs sharedPrefs,
            FileHandler fileHandler,
            OpenWeatherService openWeatherService) {
        return new MainWeatherInteractorImpl(eventBus, sharedPrefs, fileHandler, openWeatherService);
    }

    @Provides
    public DetailWeatherInteractor provideDetailWeatherInteractor(
            EventBusRx eventBus,
            SharedPrefs sharedPrefs,
            FileHandler fileHandler,
            OpenWeatherService openWeatherService
    ) {
        return new DetailWeatherInteractorImpl(eventBus, sharedPrefs, fileHandler, openWeatherService);
    }
}
