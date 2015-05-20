package gro.gibberish.drizzle.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import gro.gibberish.drizzle.R;
import gro.gibberish.drizzle.data.ObservableLocationProvider;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Change this
 */
public class LocationAddFragment extends DialogFragment implements DialogInterface.OnClickListener {
    private OnLocationSubmitted mCallbacks;
    private EditText mZipCode;
    private View mForm;
    private Subscription mGpsSubscription;

    public static LocationAddFragment newInstance() {
        LocationAddFragment frag = new LocationAddFragment();

        return frag;
    }

    public interface OnLocationSubmitted {
        void onZipCodeEntered(String zip);
        // Don't actually know whta GPS service provides
        void onGpsCoordsChosen(double latitude, double longitude);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            mCallbacks = (OnLocationSubmitted) getTargetFragment();
        } catch (ClassCastException e) {
            throw new ClassCastException(getTargetFragment().toString() +
                    " must implement OnLocationSubmitted");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        mForm = getActivity().getLayoutInflater().inflate(R.layout.fragment_add_location, null);
        builder.setView(mForm);
        mZipCode = (EditText) mForm.findViewById(R.id.edit_text_zip_code);
        Button mGpsButton = (Button) mForm.findViewById(R.id.btn_get_gps);
        mGpsButton.setOnClickListener(view -> getGpsLocation());

        return builder.setTitle("Add a location")
                .setPositiveButton("OK", this)
                .setNegativeButton("Cancel", null).create();
    }

    @Override
    public void onClick(DialogInterface diag, int button) {
        if (button == DialogInterface.BUTTON_POSITIVE) {
            // Send either zip code or gps coord interface. For now just zip code
            String enteredZip = mZipCode.getText().toString();
            mCallbacks.onZipCodeEntered(enteredZip);
        }
    }

    private void getGpsLocation() {
        setRetainInstance(true);
        mGpsSubscription = ObservableLocationProvider.retrieveLocationObservable(getActivity())
                .subscribe(
                        location -> mCallbacks
                                .onGpsCoordsChosen(location.getLatitude(), location.getLongitude()),
                        Throwable::printStackTrace,
                        this::dismiss
                );
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        mCallbacks = null;
        if (mGpsSubscription != null) {
            mGpsSubscription.unsubscribe();
        }
    }
}
