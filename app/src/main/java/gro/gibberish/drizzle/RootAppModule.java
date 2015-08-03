package gro.gibberish.drizzle;

import android.content.Context;

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
