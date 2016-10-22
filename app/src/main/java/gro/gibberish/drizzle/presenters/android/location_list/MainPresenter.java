package gro.gibberish.drizzle.presenters.android.location_list;

public interface MainPresenter {
    void init(MainView mainview);

    void onListClick(String id);

    void onResume();
}
