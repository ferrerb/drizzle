package gro.gibberish.drizzle.location_list;

public interface MainPresenter {
    void init(MainView mainview);

    void onListClick(String id);

    void onResume();
}
