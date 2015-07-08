package gro.gibberish.drizzle.mainlist;

import java.util.List;

import gro.gibberish.drizzle.models.LocationModel;

public interface MainView {
    void showProgressBar();

    void hideProgressBar();

    void fillRecyclerView(List<LocationModel> data);
}
