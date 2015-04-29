package gro.gibberish.drizzle.http;

import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;

/**
 * Change this
 */
public class FileHandler {
    private FileHandler() {}

    public static <T extends Serializable> void saveWeatherToFile(T data, String path, String fileName) {
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

    public static <T> T getWeatherFromFile(Class<T> type, String path, String fileName) {
        Log.d("weather from files!", "true");
        T data = null;
        try {
            FileInputStream fis = new FileInputStream(
                    new File(path, fileName));
            ObjectInputStream in = new ObjectInputStream(fis);

            data = (T) in.readObject();
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

}
