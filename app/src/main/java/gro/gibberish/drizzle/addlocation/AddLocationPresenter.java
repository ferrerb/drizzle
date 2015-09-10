package gro.gibberish.drizzle.addlocation;

public interface AddLocationPresenter {
    void findLocationWithZip(String zipCode);

    void findLocationWithGps();

    void init(AddLocationView addLocationView);
}
