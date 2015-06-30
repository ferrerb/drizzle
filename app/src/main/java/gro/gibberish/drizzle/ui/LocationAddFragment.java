package gro.gibberish.drizzle.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import gro.gibberish.drizzle.R;
import gro.gibberish.drizzle.data.LocationObservableProvider;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Change this
 */
public class LocationAddFragment extends DialogFragment implements DialogInterface.OnClickListener {
    private OnLocationSubmitted mCallbacks;
    private EditText mZipCode;
    private View mForm;
    private Subscription locationServiceSubscription;

    public static LocationAddFragment newInstance() {
        return new LocationAddFragment();
    }

    public interface OnLocationSubmitted {
        void onZipCodeEntered(String zip);
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
        mGpsButton.setOnClickListener(view -> checkForLocationServices());

        return builder.setTitle("Add a location")
                .setPositiveButton(R.string.ok, this)
                .setNegativeButton(R.string.cancel, null).create();
    }

    @Override
    public void onClick(DialogInterface diag, int button) {
        if (button == DialogInterface.BUTTON_POSITIVE) {
            String enteredZip = mZipCode.getText().toString();
            mCallbacks.onZipCodeEntered(enteredZip);
        }
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        mCallbacks = null;
        if (locationServiceSubscription != null) {
            locationServiceSubscription.unsubscribe();
        }
    }

    private void checkForLocationServices() {
        LocationManager mgr =
                (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        // TODO Check for other providers, and send different accuracy types based on it
        // TODO Could the check go in a static method in observablelocationprovider
        // TODO use googleapiservices?
        if (mgr.isProviderEnabled(LocationManager.NETWORK_PROVIDER) ||
                mgr.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            getLocation();
            // TODO Have the location observable use the enabled provider
        } else {
            //getLocation();

        }
    }

    private void getLocation() {
        setRetainInstance(true);
        // TODO Could use the find API to search by name, or with zip,
        locationServiceSubscription = LocationObservableProvider.retrieveLocationObservable(getActivity())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        location -> mCallbacks
                                .onGpsCoordsChosen(location.getLatitude(), location.getLongitude()),
                        Throwable::printStackTrace,
                        this::dismiss
                );
    }


}
