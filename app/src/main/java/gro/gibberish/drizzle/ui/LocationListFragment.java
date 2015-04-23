package gro.gibberish.drizzle.ui;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import gro.gibberish.drizzle.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LocationListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LocationListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LocationListFragment extends RecyclerViewFragment {

    private static final String LOCATIONS = "locations";
    private String mLocations;

    private OnFragmentInteractionListener mListener;

    /**
     * Creates a new fragment containing a recycler view
     *
     * @param location List of locations with format "zip + ,us" ie 20202,us,30303,us
     * @return A new instance of fragment LocationListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LocationListFragment newInstance(String location) {
        LocationListFragment fragment = new LocationListFragment();
        Bundle args = new Bundle();
        args.putString(LOCATIONS, location);
        fragment.setArguments(args);
        return fragment;
    }

    public LocationListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mLocations = getArguments().getString(LOCATIONS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //TODO actually get data. probably from file
        View result = inflater.inflate(R.layout.fragment_location_list, container, false);
        setLayoutManager(new LinearLayoutManager(getActivity().getBaseContext()));
        setAdapter(new WeatherListAdapter(null));

        return result;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onLocationChosen(Uri uri);
    }

}
