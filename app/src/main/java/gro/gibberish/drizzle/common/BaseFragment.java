package gro.gibberish.drizzle.common;

import android.app.Fragment;
import android.os.Bundle;

import java.util.List;

import dagger.ObjectGraph;
import gro.gibberish.drizzle.RootApp;

public class BaseFragment extends Fragment {
    private ObjectGraph activityGraph;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ((BaseActivity) getActivity()).inject(this);
    }
}
