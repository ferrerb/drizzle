package gro.gibberish.drizzle;

import android.content.Context;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import gro.gibberish.drizzle.data.DataModule;
import gro.gibberish.drizzle.interactors.InteractorsModule;

@Module(
        injects ={
                RootApp.class
        },
        includes = {
                EventBusRxModule.class,
                DataModule.class
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
}
