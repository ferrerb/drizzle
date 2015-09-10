package gro.gibberish.drizzle.data;

import android.content.Context;
import android.location.LocationManager;
import android.preference.PreferenceManager;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
        includes={
                ApiModule.class
        },
        library=true,
        complete=false
)
public class DataModule {
    @Provides
    @Singleton
    SharedPrefs provideSharedPrefs(@Named("activity") Context context) {
        return new SharedPrefsImpl(PreferenceManager.getDefaultSharedPreferences(context));
    }

    @Provides
    @Singleton
    FileHandler provideFileHandler(@Named("activity") Context context) {
        return new FileHandler(context);
    }

    @Provides
    @Singleton
    GpsLocationObservable provideGpsLocationObservable(LocationManager locationManager) {
        return new GpsLocationObservable(locationManager);
    }

}
