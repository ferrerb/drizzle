package gro.gibberish.drizzle.location_list;

import android.content.Context;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import gro.gibberish.drizzle.EventBusRx;
import gro.gibberish.drizzle.RootAppModule;
import gro.gibberish.drizzle.android.common.BaseActivity;
import gro.gibberish.drizzle.interactors.InteractorsModule;
import gro.gibberish.drizzle.interactors.MainWeatherInteractor;

@Module(
        injects={
                MainActivity.class,
                MainFragment.class,
        },
        includes={
                InteractorsModule.class,
                //DataModule.class
        },
        addsTo=RootAppModule.class
)
public class MainModule {
    private final BaseActivity activity;

    public MainModule(BaseActivity activity) {
        this.activity = activity;
    }

    @Provides
    @Singleton
    @Named("activity")
    Context provideActivityContext() {
        return activity;
    }

    @Provides
    @Singleton
    MainPresenter provideMainPresenter(EventBusRx eventBus, MainWeatherInteractor mainWeatherInteractor) {
        return new MainPresenterImpl(eventBus, mainWeatherInteractor);
    }
}
