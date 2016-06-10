package gro.gibberish.drizzle.location_detail;

import android.content.Context;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import gro.gibberish.drizzle.RootAppModule;
import gro.gibberish.drizzle.common.BaseActivity;
import gro.gibberish.drizzle.interactors.InteractorsModule;

@Module(
        injects={
                DetailActivity.class,
                DetailFragment.class
        },
        includes={
                InteractorsModule.class,
        },
        addsTo= RootAppModule.class
)
public class DetailModule {
    private BaseActivity activity;

    public DetailModule(BaseActivity activity) {
        this.activity = activity;
    }

    @Provides
    @Singleton
    @Named("activity")
    public Context provideActivityContext() {
        return activity;
    }
}
