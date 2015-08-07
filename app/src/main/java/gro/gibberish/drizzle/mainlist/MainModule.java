package gro.gibberish.drizzle.mainlist;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import gro.gibberish.drizzle.EventBusRx;
import gro.gibberish.drizzle.EventBusRxModule;
import gro.gibberish.drizzle.RootAppModule;
import gro.gibberish.drizzle.common.BaseActivity;
import gro.gibberish.drizzle.data.DataModule;
import gro.gibberish.drizzle.interactors.InteractorsModule;
import gro.gibberish.drizzle.interactors.MainWeatherInteractor;
import gro.gibberish.drizzle.interactors.MainWeatherInteractorImpl;

@Module(
        injects={
                MainActivity.class,
                MainFragment.class,
        },
        includes={
                InteractorsModule.class,
                //EventBusRxModule.class,
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
