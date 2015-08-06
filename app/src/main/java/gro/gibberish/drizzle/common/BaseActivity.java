package gro.gibberish.drizzle.common;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.Arrays;
import java.util.List;

import dagger.ObjectGraph;
import gro.gibberish.drizzle.RootApp;
import gro.gibberish.drizzle.mainlist.MainModule;

public class BaseActivity extends AppCompatActivity {
    private ObjectGraph activityGraph;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //TODO Use this to create the activitygraph(ie to get activity context), and inject the fragment/mainview as from basefragment
        RootApp rootApp = (RootApp) getApplicationContext();
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
}
