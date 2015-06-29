package gro.gibberish.drizzle.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import rx.Observable;
import rx.Subscriber;
import rx.exceptions.OnErrorThrowable;
import rx.schedulers.Schedulers;

public class FileHandler {
    private FileHandler() {}

    // Suppresses a warning about casting a deserialized object to its original type
    @SuppressWarnings("unchecked")
    public static <T> Observable<T> getSerializedObjectFromFile(
            Class<T> typeToBeDeserialized, File path, String fileName) {
        return Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(Subscriber<? super T> subscriber) {
                try {
                    T data;
                    FileInputStream fis = new FileInputStream(
                            new File(path, fileName));
                    ObjectInputStream in = new ObjectInputStream(fis);

                    data = (T) in.readObject();
                    in.close();

                    subscriber.onNext(data);
                    subscriber.onCompleted();
                } catch (IOException | ClassNotFoundException e) {
                    throw OnErrorThrowable.from(e);
                }
            }
        }).subscribeOn(Schedulers.io());
    }

    public static <T extends Serializable> Observable<Void> saveSerializableObjectToFile(
            T objectToBeSerialized, File path, String fileName) {
        return Observable.create(new Observable.OnSubscribe<Void>() {
            @Override
            public void call(Subscriber<? super Void> subscriber) {
                try {
                    FileOutputStream fos = new FileOutputStream(
                            new File(path, fileName), false);
                    ObjectOutputStream out = new ObjectOutputStream(fos);

                    out.writeObject(objectToBeSerialized);
                    out.close();
                } catch (FileNotFoundException e) {
                    throw OnErrorThrowable.from(e);
                } catch (IOException e) {
                    throw OnErrorThrowable.from(e);
                }
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io());
    }

    public static Observable<Boolean> deleteFile(File path, String fileName) {
        return Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                try {
                    File file = new File(path, fileName);
                    boolean fileDeleted = file.delete();
                    subscriber.onNext(fileDeleted);
                } catch (SecurityException e) {
                    throw OnErrorThrowable.from(e);
                }
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io());
    }
}
