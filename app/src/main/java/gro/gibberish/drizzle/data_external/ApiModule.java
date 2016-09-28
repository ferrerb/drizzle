package gro.gibberish.drizzle.data_external;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module(
        library=true,
        complete=false
)
public class ApiModule {

    @Provides
    @Singleton
    OpenWeatherService provideOpenWeatherService(Retrofit restAdapter) {
        return restAdapter.create(OpenWeatherService.class);
    }
}
