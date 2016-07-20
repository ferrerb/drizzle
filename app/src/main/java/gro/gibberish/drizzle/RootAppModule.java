package gro.gibberish.drizzle;

import android.content.Context;
import android.location.LocationManager;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
        injects ={
                RootApp.class
        },
        includes = {
                EventBusRxModule.class
        },
        library=false
)

public class RootAppModule {
    private RootApp rootApp;

    public RootAppModule(RootApp rootApp) {
        this.rootApp = rootApp;
    }

    @Provides
    @Singleton
    @Named("appContext")
    public Context provideApplicationContext() {
        return rootApp;
    }

    @Provides
    @Singleton
    public LocationManager provideLocationManager() {
        return (LocationManager) rootApp.getSystemService(Context.LOCATION_SERVICE);
    }

    @Provides
    @Singleton
    @Named("api_key")
    public String provideApiKey() {
        return rootApp.getResources().getString(R.string.api_key);
    }
}
