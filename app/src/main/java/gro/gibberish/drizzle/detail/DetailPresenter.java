package gro.gibberish.drizzle.detail;

import gro.gibberish.drizzle.mainlist.MainView;

public interface DetailPresenter {
    void init(DetailView detailView);

    void onDeleteLocation(String id);

    void onResume();
}
