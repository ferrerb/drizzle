package gro.gibberish.drizzle.location_add;

public interface AddLocationPresenter {
    void findLocationWithZip(String zipCode);

    void findLocationWithGps();

    void init(AddLocationView addLocationView);
}
