package gro.gibberish.drizzle.mainlist;


import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import gro.gibberish.drizzle.R;
import gro.gibberish.drizzle.common.BaseFragment;
import gro.gibberish.drizzle.models.LocationModel;

public class MainFragment extends BaseFragment implements MainView{
    @Inject MainPresenter mainPresenter;
    RecyclerView recyclerView;

    public static MainFragment newInstance() {
        return new MainFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View result = inflater.inflate(R.layout.fragment_main, container, false);
        recyclerView = (RecyclerView) result.findViewById(R.id.recycler_list);
        return result;
    }

    @Override
    public void onResume() {
        super.onResume();
        mainPresenter.onResume();
    }

    @Override
    protected List<Object> getModules() {
        return Arrays.<Object>asList(new MainModule(this));
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
