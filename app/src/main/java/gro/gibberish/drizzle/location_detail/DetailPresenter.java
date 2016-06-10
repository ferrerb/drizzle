package gro.gibberish.drizzle.location_detail;

public interface DetailPresenter {
    void init(DetailView detailView);

    void onDeleteLocation(String locationToDelete);

    void onResume();
}
