package gro.gibberish.drizzle.common;

import android.app.Fragment;
import android.os.Bundle;

import java.util.List;

import dagger.ObjectGraph;
import gro.gibberish.drizzle.RootApp;

public abstract class BaseFragment extends Fragment {
    private ObjectGraph activityGraph;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        RootApp rootApp = (RootApp) getActivity().getApplicationContext();
        activityGraph = rootApp.createScopedGraph(getModules().toArray());
        activityGraph.inject(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        activityGraph = null;
    }

    protected abstract List<Object> getModules();
}
