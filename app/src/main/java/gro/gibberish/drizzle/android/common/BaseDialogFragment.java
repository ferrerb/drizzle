package gro.gibberish.drizzle.android.common;

import android.app.DialogFragment;
import android.os.Bundle;

public class BaseDialogFragment extends DialogFragment {
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ((BaseActivity) getActivity()).inject(this);
    }
}
