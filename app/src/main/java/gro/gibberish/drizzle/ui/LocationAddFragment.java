package gro.gibberish.drizzle.ui;

import android.app.DialogFragment;
import android.app.Fragment;

/**
 * Change this
 */
public class LocationAddFragment extends DialogFragment {

    public static LocationAddFragment newInstance() {
        LocationAddFragment frag = new LocationAddFragment();

        return frag;
    }

    public interface OnLocationSubmitted {
        void onZipCodeEntered(int zip);
        // Don't actually know whta GPS service provides
        void onGpsCoordsChosen(int x, int y);
    }
}
