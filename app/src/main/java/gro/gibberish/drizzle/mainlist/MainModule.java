package gro.gibberish.drizzle.mainlist;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import gro.gibberish.drizzle.RootAppModule;

@Module(
        injects={MainFragment.class,
                 MainPresenterImpl.class
        },
        addsTo=RootAppModule.class,
        library=true
)
public class MainModule {
    private MainView view;

    public MainModule(MainView view) {
        this.view = view;
    }

    @Provides
    @Singleton
    public MainPresenter provideMainPresenter() {
        return new MainPresenterImpl(view);
    }
}
