package gro.gibberish.drizzle.presenters.android.location_list;

import java.util.List;

import gro.gibberish.drizzle.data_external.weather_beans_owm.LocationModel;

public interface MainView {
    void showProgressBar();

    void hideProgressBar();

    void fillRecyclerView(List<LocationModel> data);
}
