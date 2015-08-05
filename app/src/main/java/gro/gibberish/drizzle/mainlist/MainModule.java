package gro.gibberish.drizzle.mainlist;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import gro.gibberish.drizzle.RootAppModule;

@Module(
        injects={MainFragment.class,
                 MainWeatherInteractorImpl.class,
                 MainPresenterImpl.class
        },
        addsTo=RootAppModule.class,
        library=true
)
public class MainModule {
    private MainActivity activity;

    public MainModule(MainActivity activity) {
        this.activity = activity;
    }

    @Provides
    @Singleton
    public Context provideActivityContext() {
        return activity;
    }

    @Provides
    @Singleton
    public MainPresenter provideMainPresenter() {
        return new MainPresenterImpl();
    }

    @Provides

}
