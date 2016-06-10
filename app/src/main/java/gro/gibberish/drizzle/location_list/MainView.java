package gro.gibberish.drizzle.location_list;

import java.util.List;

import gro.gibberish.drizzle.weather_beans.LocationModel;

public interface MainView {
    void showProgressBar();

    void hideProgressBar();

    void fillRecyclerView(List<LocationModel> data);
}
