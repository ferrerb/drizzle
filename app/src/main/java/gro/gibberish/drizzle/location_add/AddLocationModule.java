package gro.gibberish.drizzle.location_add;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import gro.gibberish.drizzle.EventBusRx;

@Module
public class AddLocationModule {
    @Provides
    @Singleton
    AddLocationPresenter provideAddLocationPresenter(EventBusRx eventBus) {
        return new AddLocationPresenterImpl(eventBus);
    }
}
