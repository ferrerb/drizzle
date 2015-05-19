package gro.gibberish.drizzle.data;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import java.util.List;

import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Change this
 */
public final class ObservableLocationProvider {
    private ObservableLocationProvider() {}

    public static Observable<Location> retrieveLocationObservable(Context context) {
        return Observable.create(new Observable.OnSubscribe<Location>() {
            @Override
            public void call(Subscriber<? super Location> subscriber) {
                LocationManager mLocation =
                        (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
                LocationListener mLocationListener = new LocationListener() {
                    @Override
                    public void onStatusChanged(String s, int i, Bundle bundle) {}

                    @Override
                    public void onProviderEnabled(String s) {}

                    @Override
                    public void onProviderDisabled(String s) {}

                    @Override
                    public void onLocationChanged(Location l) {
                        subscriber.onNext(l);
                        subscriber.onCompleted();
                    }
                };
                Criteria c = new Criteria();
                c.setAccuracy(Criteria.ACCURACY_COARSE);
                String provider = mLocation.getBestProvider(c, true);
                mLocation.requestLocationUpdates(provider, 0L, 0, mLocationListener);
            }
        }

        ).subscribeOn(Schedulers.newThread());
    }
}
