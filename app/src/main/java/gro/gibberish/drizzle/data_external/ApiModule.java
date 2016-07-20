package gro.gibberish.drizzle.data_external;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.RestAdapter;

@Module(
        library=true,
        complete=false
)
public class ApiModule {
    private static final String SERVICE_ENDPOINT ="http://api.openweathermap.org/data/2.5";

    @Provides
    @Singleton
    RestAdapter provideRestAdapter() {
        return new RestAdapter.Builder()
                .setEndpoint(SERVICE_ENDPOINT)
                .build();
    }

    @Provides
    @Singleton
    OpenWeatherService provideOpenWeatherService(RestAdapter restAdapter) {
        return restAdapter.create(OpenWeatherService.class);
    }
}
