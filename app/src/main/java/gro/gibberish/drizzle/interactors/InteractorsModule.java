package gro.gibberish.drizzle.interactors;

import dagger.Module;
import dagger.Provides;

@Module(
        library=true
)
public class InteractorsModule {
    @Provides
    public MainWeatherInteractor provideMainWeatherInteractor() {
        return new MainWeatherInteractorImpl();
    }
}
