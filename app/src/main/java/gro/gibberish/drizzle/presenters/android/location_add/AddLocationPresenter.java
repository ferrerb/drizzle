package gro.gibberish.drizzle.presenters.android.location_add;

public interface AddLocationPresenter {
    void findLocationWithZip(String zipCode);

    void findLocationWithGps();

    void init(AddLocationView addLocationView);
}
