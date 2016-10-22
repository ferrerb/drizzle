package gro.gibberish.drizzle.presenters.android.location_detail;

public interface DetailPresenter {
    void init(DetailView detailView);

    void onDeleteLocation(String locationToDelete);

    void onResume();
}
