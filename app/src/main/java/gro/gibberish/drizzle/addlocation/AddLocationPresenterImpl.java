package gro.gibberish.drizzle.addlocation;

import javax.inject.Inject;

public class AddLocationPresenterImpl implements AddLocationPresenter {
    private AddLocationView addLocationView;


    @Inject
    public AddLocationPresenterImpl(AddLocationView addLocationView) {
        this.addLocationView = addLocationView;
    }

    @Override
    public void findLocationWithZip(int zipCode) {

    }

    @Override
    public void findLocationWithGps() {

    }
}
