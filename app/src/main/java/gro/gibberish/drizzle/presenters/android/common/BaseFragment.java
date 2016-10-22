package gro.gibberish.drizzle.presenters.android.common;

import android.app.Fragment;
import android.os.Bundle;

public class BaseFragment extends Fragment {
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ((BaseActivity) getActivity()).inject(this);
    }
}
