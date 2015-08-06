package gro.gibberish.drizzle.mainlist;

import android.app.Activity;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import gro.gibberish.drizzle.RootAppModule;
import gro.gibberish.drizzle.common.BaseActivity;
import gro.gibberish.drizzle.interactors.MainWeatherInteractorImpl;

@Module(
        injects={
                MainFragment.class,
                MainPresenterImpl.class,
                MainWeatherInteractorImpl.class
        },
        addsTo=RootAppModule.class,
        library=true
)
public class MainModule {
    private BaseActivity activity;

    public MainModule(BaseActivity activity) {
        this.activity = activity;
    }

    @Provides
    @Singleton
    MainPresenter provideMainPresenter() {
        return new MainPresenterImpl();
    }

    @Provides
    @Singleton
    Context provideActivityContext() {
        return activity;
    }
}
