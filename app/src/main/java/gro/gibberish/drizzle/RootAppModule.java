package gro.gibberish.drizzle;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import gro.gibberish.drizzle.interactors.InteractorsModule;

@Module(
        injects ={
                RootApp.class
        },
        includes = {
                EventBusRxModule.class,
                InteractorsModule.class
        },
        library=true
)

public class RootAppModule {
    private RootApp rootApp;

    public RootAppModule(RootApp rootApp) {
        this.rootApp = rootApp;
    }

    @Provides
    @Singleton
    public Context provideApplicationContext() {
        return rootApp;
    }
}
