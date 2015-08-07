package gro.gibberish.drizzle.interactors;

import android.content.Context;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import gro.gibberish.drizzle.EventBusRx;
import gro.gibberish.drizzle.data.SharedPrefs;

@Module(
        library=true,
        complete=false
)
public class InteractorsModule {
    @Provides
    public MainWeatherInteractor provideMainWeatherInteractor(
            EventBusRx eventBus,
            SharedPrefs sharedPrefs,
            @Named("activity") Context activityContext) {
        return new MainWeatherInteractorImpl(eventBus, sharedPrefs, activityContext);
    }
}
