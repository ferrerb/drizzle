package gro.gibberish.drizzle.location_add;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import javax.inject.Inject;

import gro.gibberish.drizzle.R;
import gro.gibberish.drizzle.common.BaseDialogFragment;
// TODO maybe use bottomsheetdialog
public class AddLocationFragment extends BaseDialogFragment implements
        AddLocationView, DialogInterface.OnClickListener {
    @Inject AddLocationPresenter addLocationPresenter;
    private EditText mZipCode;
    private View form;

    public static AddLocationFragment newInstance() {
        return new AddLocationFragment();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        form = getActivity().getLayoutInflater().inflate(R.layout.fragment_add_location, null);
        builder.setView(form);
        mZipCode = (EditText) form.findViewById(R.id.edit_text_zip_code);

        Button mGpsButton = (Button) form.findViewById(R.id.btn_get_gps);
        // TODO check for GPS enabled
        mGpsButton.setOnClickListener(view -> addLocationPresenter.findLocationWithGps());

        addLocationPresenter.init(this);
        return builder.setTitle("Add a location")
                .setPositiveButton(R.string.ok, this)
                .setNegativeButton(R.string.cancel, null).create();
    }

    @Override
    public void onClick(DialogInterface diag, int button) {
        if (button == DialogInterface.BUTTON_POSITIVE) {
            String enteredZip = mZipCode.getText().toString();
            addLocationPresenter.findLocationWithZip(enteredZip);
        }
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
    }


    public void showProgressBar() {
        // TODO show a progress spinner thing when getting GPS coordinates
    }
}
