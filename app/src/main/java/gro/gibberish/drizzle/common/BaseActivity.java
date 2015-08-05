package gro.gibberish.drizzle.common;

import android.app.Activity;
import android.os.Bundle;

import java.util.Arrays;
import java.util.List;

import dagger.ObjectGraph;
import gro.gibberish.drizzle.RootApp;

public abstract class BaseActivity extends Activity{
    private ObjectGraph activityGraph;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        RootApp rootApp = (RootApp) getApplication();
        activityGraph = rootApp.createScopedGraph(getModules().toArray());
        activityGraph.inject(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        activityGraph = null;
    }

    protected abstract List<Object> getModules();
}
