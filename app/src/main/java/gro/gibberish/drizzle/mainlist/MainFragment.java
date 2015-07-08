package gro.gibberish.drizzle.mainlist;


import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import gro.gibberish.drizzle.R;
import gro.gibberish.drizzle.models.LocationModel;

public class MainFragment extends Fragment implements MainView{
    RecyclerView recyclerView;
    MainPresenter mainPresenter;

    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            //
        }
        mainPresenter = MainPresenterImpl.newInstance(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View result = inflater.inflate(R.layout.fragment_main, container, false);
        return result;
    }

    @Override
    public void onResume() {
        super.onResume();
        mainPresenter.onResume();
    }

    @Override
    public void showProgressBar() {
        // Show empty RecyclerView progress thing
    }

    @Override
    public void hideProgressBar() {

    }

    @Override
    public void fillRecyclerView(List<LocationModel> data) {

    }

}
