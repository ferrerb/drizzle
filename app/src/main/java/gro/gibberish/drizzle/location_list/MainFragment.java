package gro.gibberish.drizzle.location_list;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import gro.gibberish.drizzle.R;
import gro.gibberish.drizzle.common.BaseFragment;
import gro.gibberish.drizzle.weather_beans_owm.LocationModel;
import gro.gibberish.drizzle.ui.OnItemTouchListener;
import gro.gibberish.drizzle.ui.WeatherListAdapter;

public class MainFragment extends BaseFragment implements MainView{
    @Inject MainPresenter mainPresenter;
    private RecyclerView recyclerView;
    private OnItemTouchListener mOnItemClick;
    private List<LocationModel> locationWeatherList = new ArrayList<>();

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
        // TODO Does injection make sense here?
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        // TODO need to send location to detailview (rxbus?)
        // TODO abstract this data or something
        mOnItemClick = (view, position) -> Log.d("Location id", Long.toString(locationWeatherList.get(position).getId()));

        return result;
    }

    @Override
    public void onResume() {
        super.onResume();
        mainPresenter.init(this);
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
        locationWeatherList = data;
        recyclerView.swapAdapter(new WeatherListAdapter(locationWeatherList, mOnItemClick), true);

    }

}
