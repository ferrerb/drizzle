package gro.gibberish.drizzle;

import android.app.Application;

import java.util.Arrays;
import java.util.List;

import dagger.ObjectGraph;

public class RootApp extends Application {
    private ObjectGraph applicationGraph;

    @Override
    public void onCreate() {
        super.onCreate();

        applicationGraph = ObjectGraph.create(getModules().toArray());
    }

    protected List<Object> getModules() {
        return Arrays.asList(new RootAppModule(this));
    }

    public void inject(Object object) {
        applicationGraph.inject(object);
    }

    public ObjectGraph getApplicationGraph() {
        return applicationGraph;
    }

    public ObjectGraph createScopedGraph(Object... module) {
        return applicationGraph.plus(module);
    }
}
