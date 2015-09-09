package gro.gibberish.drizzle.addlocation;

public interface AddLocationPresenter {
    void findLocationWithZip(int zipCode);

    void findLocationWithGps();
}
