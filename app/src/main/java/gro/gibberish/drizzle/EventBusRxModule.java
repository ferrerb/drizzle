package gro.gibberish.drizzle;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
        library=true
)
public class EventBusRxModule {
    @Provides
    @Singleton
    public EventBusRx provideEventBusRx() {
        return EventBusRx.INSTANCE;
    }
}
