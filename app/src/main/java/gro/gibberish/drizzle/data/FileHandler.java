package gro.gibberish.drizzle.data;

import android.util.Log;

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

/**
 * Change this
 */
public class FileHandler {
    private FileHandler() {}

    /**
     * Serializes an object to a file
     *
     * @param data The object to be serialized
     * @param path The path to the to-be-created file, uses a File object since getCacheDir() returns one
     * @param fileName The name of the file to be created
     * @param <T> The type of object being serialized
     */
    public static <T extends Serializable> void saveSerializedObjectToFile(T data, File path, String fileName) {
        try {
            FileOutputStream fos = new FileOutputStream(
                    new File(path, fileName), false);
            ObjectOutputStream out = new ObjectOutputStream(fos);

            out.writeObject(data);
            out.close();
        }
        catch (FileNotFoundException e) {
            System.err.println("FileNotFoundException " + e.getMessage());
        }
        catch (IOException e) {
            System.err.println("IOException " + e.getMessage());
        }
    }

    /**
     * Retrieves a serialized object from file
     *
     * @param type The class type to be deserialized
     * @param path The path to the desired file, uses a File object since getCacheDir() returns one
     * @param fileName The name of the file to be deserialized
     * @param <T> The class type to be deserialized
     * @return An object of type <T> containing created from the specified file
     */
    /* The warning is from the cast from the file object, to the desired type. I don't know a good
     * way to avoid this, and I can't move it to be more localized with the try statement. Perhaps I could
     * create a separate object in the try block, and then assign it to the data variable. So, make
     * sure you pass the class which matches the serialized object. No pressure.
     */
    @SuppressWarnings("unchecked")
    public static <T> T getSerializedObjectFromFile(Class<T> type, File path, String fileName) {
        Log.d("weather from files!", "true");
        T data = null;

        try {
            FileInputStream fis = new FileInputStream(
                    new File(path, fileName));
            ObjectInputStream in = new ObjectInputStream(fis);

            data = (T) in.readObject();
            in.close();
        }
        catch (FileNotFoundException e) {
            System.err.println("FileNotFoundException " + e.getMessage());
        }
        catch (IOException e) {
            System.err.println("IOException " + e.getMessage());
        }
        catch (ClassNotFoundException e) {
            System.err.println("ClassNotFoundException " + e.getMessage());
        }
        return data;
    }

    /**
     * Retrieves a serialized object from file
     *
     * @param type The class type to be deserialized
     * @param path The path to the desired file, uses a File object since getCacheDir() returns one
     * @param fileName The name of the file to be deserialized
     * @param <T> The class type to be deserialized
     * @return An observable which emits an object of type <T> containing created from the specified file
     */
    /* The warning is from the cast from the file object, to the desired type. I don't know a good
     * way to avoid this, and I can't move it to be more localized with the try statement. Perhaps I could
     * create a separate object in the try block, and then assign it to the data variable. So, make
     * sure you pass the class which matches the serialized object. No pressure.
     */
    @SuppressWarnings("unchecked")
    public static <T> Observable<T> getSerializedObjectObservable(Class<T> type, File path, String fileName) {
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
                }
                catch (IOException | ClassNotFoundException e) {
                    throw OnErrorThrowable.from(e);
                }
            }
        }).subscribeOn(Schedulers.io());
    }

    /**
     * Serializes an object to a file
     *
     * @param data The object to be serialized
     * @param path The path to the to-be-created file, uses a File object since getCacheDir() returns one
     * @param fileName The name of the file to be created
     * @param <T> The type of object being serialized
     * @return An observable which emits nothing, but will provide errors.
     */
    public static <T extends Serializable> Observable<Void> saveSerializedObjectObservable(T data, File path, String fileName) {
        return Observable.create(new Observable.OnSubscribe<Void>() {
            @Override
            public void call(Subscriber<? super Void> subscriber) {
                try {
                    FileOutputStream fos = new FileOutputStream(
                            new File(path, fileName), false);
                    ObjectOutputStream out = new ObjectOutputStream(fos);

                    out.writeObject(data);
                    out.close();
                }
                catch (FileNotFoundException e) {
                    throw OnErrorThrowable.from(e);
                }
                catch (IOException e) {
                    throw OnErrorThrowable.from(e);
                }
                subscriber.onCompleted();
            }

        }).subscribeOn(Schedulers.io());
    }

}
