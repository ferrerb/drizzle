package gro.gibberish.drizzle.data;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;

import java.util.List;

import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Change this
 */
public final class LocationObservableProvider {
    private static LocationManager mLocationManager;
    private static LocationListener mLocationListener;

    private LocationObservableProvider() {
    }

    public static Observable<Location> retrieveLocationObservable(Context ctxt) {
        mLocationManager = (LocationManager) ctxt.getSystemService(Context.LOCATION_SERVICE);
        // TODO also this is ugly. do i need to make the anonymous class here?

        Observable<Location> mObservable = Observable.create(new Observable.OnSubscribe<Location>() {
            @Override
            public void call(Subscriber<? super Location> subscriber) {

                mLocationListener = new LocationListener() {
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
                String provider = mLocationManager.getBestProvider(c, true);
                mLocationManager.requestSingleUpdate(
                        provider, mLocationListener, Looper.myLooper());
                Looper.loop();
            }
        })
         .doOnUnsubscribe(() -> mLocationManager.removeUpdates(mLocationListener));

        return mObservable;
    }
}
