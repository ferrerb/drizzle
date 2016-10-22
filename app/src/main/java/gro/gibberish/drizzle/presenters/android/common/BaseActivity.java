package gro.gibberish.drizzle.presenters.android.common;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.Arrays;
import java.util.List;

import dagger.ObjectGraph;
import gro.gibberish.drizzle.RootApp;
import gro.gibberish.drizzle.presenters.android.location_list.MainModule;

public class BaseActivity extends AppCompatActivity {
    private ObjectGraph activityGraph;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //TODO Use this to create the activitygraph(ie to get activity context), and inject the fragment/mainview as from basefragment
        RootApp rootApp = (RootApp) getApplication();
        activityGraph = rootApp.createScopedGraph(getModules().toArray());
        activityGraph.inject(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        activityGraph = null;
    }

    protected List<Object> getModules() {
        return Arrays.<Object>asList(new MainModule(this));
    }

    public void inject(Object object) {
        activityGraph.inject(object);
    }

    public ObjectGraph getActivityGraph() {
        return activityGraph;
    }
}
