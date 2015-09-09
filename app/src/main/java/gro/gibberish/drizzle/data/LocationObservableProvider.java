package gro.gibberish.drizzle.data;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;

public class LocationObservableProvider {
    private LocationManager locationManager;
    private LocationListener locationListener;

    @Inject
    public LocationObservableProvider(LocationManager locationManager) {
        this.locationManager = locationManager;
    }

    public Observable<Location> retrieveGpsCoordsSingleUpdate() {
        // TODO also this is ugly. do i need to make the anonymous class here?
        Observable<Location> mObservable = Observable.create(new Observable.OnSubscribe<Location>() {
            @Override
            public void call(Subscriber<? super Location> subscriber) {

                locationListener = new LocationListener() {
                    @Override
                    public void onStatusChanged(String s, int i, Bundle bundle) {}

                    @Override
                    public void onProviderEnabled(String s) {}

                    @Override
                    public void onProviderDisabled(String s) {}

                    @Override
                    public void onLocationChanged(Location l) {
                        Looper.myLooper().quit();
                        subscriber.onNext(l);
                        subscriber.onCompleted();
                    }
                };
                Looper.prepare();

                Criteria c = new Criteria();
                c.setAccuracy(Criteria.ACCURACY_COARSE);
                String provider = locationManager.getBestProvider(c, true);
                locationManager.requestSingleUpdate(
                        provider, locationListener, Looper.myLooper());
                Looper.loop();
            }
        })
         .doOnUnsubscribe(() -> locationManager.removeUpdates(locationListener));

        return mObservable;
    }
}
