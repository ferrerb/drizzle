package gro.gibberish.drizzle.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
        library=true,
        complete=false
)
public class DataModule {
    @Provides
    @Singleton
    public SharedPrefs provideSharedPrefs(@Named("appContext") Context context) {
        return new SharedPrefsImpl(PreferenceManager.getDefaultSharedPreferences(context));
    }
}
