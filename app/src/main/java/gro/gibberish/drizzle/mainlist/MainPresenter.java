package gro.gibberish.drizzle.mainlist;

public interface MainPresenter {
    void init(MainView mainview);

    void onListClick(String id);

    void onResume();
}
