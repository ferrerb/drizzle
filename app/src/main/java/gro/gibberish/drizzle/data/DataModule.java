package gro.gibberish.drizzle.data;

import dagger.Module;
import dagger.Provides;

@Module(
    library=true
)
public class DataModule {
    @Provides
    public SharedPreferencesProvider provideSharedPreferences() {
        return new SharedPreferencesProviderImpl();
    }
}
