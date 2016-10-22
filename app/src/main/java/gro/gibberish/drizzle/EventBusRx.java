package gro.gibberish.drizzle;

import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.ReplaySubject;
import rx.subjects.Subject;

public enum EventBusRx {
    INSTANCE;

    private final Subject<Object, Object> publishSubject =
            PublishSubject.create().toSerialized();

    private final Subject<Object, Object> stickySubject =
            ReplaySubject.create().toSerialized();

    // Probably don't need this
    public static EventBusRx getInstance() {
        return INSTANCE;
    }

    public Observable<Object> get() {
        return publishSubject;
    }

    public Observable<Object> getSticky() {
        return stickySubject;
    }

    public void post(Object o) {
        publishSubject.onNext(o);
    }

    public void postSticky(Object o) {
        stickySubject.onNext(o);
    }
}
