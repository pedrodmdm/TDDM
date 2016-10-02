package edu.uoc.library;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.util.Log;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import edu.uoc.library.calback.GetCallback;
import edu.uoc.library.calback.SaveCallback;
import edu.uoc.library.model.Monument;
import edu.uoc.library.model.MonumentList;
import edu.uoc.library.utils.FileUtils;
import edu.uoc.library.utils.LibraryConstants;

/**
 * Created by SGAR810 on 23/09/2016.
 */
public class LibraryManager {

    private static LibraryManager instance;
    private static Context context;

    private static final int SLEEP_TIME = 2000;

    private LibraryManager(Context context) {
        // To avoid to use the constructor in other classes. getInstance method must be used.
        LibraryManager.context = context;
        // Create assets folder if doesn't exist. This is not a good practice to do this things here!
        if (!FileUtils.existFolders(context)) {
            FileUtils.createAssetsApp(context);
        }
    }

    public synchronized static LibraryManager getInstance(Context context) {
        // Singleton instance
        if (instance == null) {
            instance = new LibraryManager(context);
        }
        return instance;
    }

    /**
     * Method to save a new monument
     */
    public void saveMonumentInBackground(
            final String name,
            final String country,
            final String description,
            final Bitmap image,
            final SaveCallback callback) {
        new Thread(new Runnable() {
            public void run() {
                try {
                    // Hardcoded sleep to simulate a server response
                    Thread.sleep(SLEEP_TIME);
                    // Get monument
                    Monument monument = getMonument(name, country, description, image);
                    callback.onSuccess(monument);
                } catch (Exception e) {
                    Log.e(LibraryConstants.TAG, e.getMessage());
                    callback.onFailure(e);
                }
            }
        }).start();
    }

    /**
     * Method to save a new monument
     */
    public boolean saveMonument(
            String name,
            String country,
            String description,
            Bitmap image) {
        try {
            // Hardcoded sleep to simulate a server response
            Thread.sleep(SLEEP_TIME);
            // Get monument
            Monument monument = getMonument(name, country, description, image);
            FileUtils.saveMonumentToDatabaseContent(context, monument);
            return true;
        } catch (Exception e) {
            Log.e(LibraryConstants.TAG, e.getMessage());
            return false;
        }
    }

    /**
     * Method to get all the stored monuments
     */
    public void getAllMonuments(final GetCallback<List<Monument>> callback) {
        Log.d(LibraryConstants.TAG, "Starting method to get all the stored methods by thread id: " + Thread.currentThread().getId());
        new Thread(new Runnable() {
            public void run() {
                try {
                    // Hardcoded sleep to simulate a server response
                    Thread.sleep(SLEEP_TIME);
                    Gson gson = new Gson();
                    Log.d(LibraryConstants.TAG, "Getting the result by thread id: " + Thread.currentThread().getId());
                    final MonumentList monuments = gson.fromJson(
                            FileUtils.getMonumentDatabaseContent(context),
                            MonumentList.class);
                    // To use callback in the main thread
                    new Handler(context.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            Log.d(LibraryConstants.TAG, "Return the succeed result by thread id: " + Thread.currentThread().getId());
                            callback.onSuccess(monuments.getMonuments());
                        }
                    });
                } catch (final Exception e) {
                    Log.e(LibraryConstants.TAG, e.getMessage());
                    // To use callback in the main thread
                    new Handler(context.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            Log.d(LibraryConstants.TAG, "Return the failed exception by thread id: " + Thread.currentThread().getId());
                            callback.onFailure(e);
                        }
                    });
                }
            }
        }).start();
    }

    /**
     * Method to get all the stored monuments
     */
    public ArrayList<Monument> getAllMonuments() {
        Log.d(LibraryConstants.TAG, "Starting method to get all the stored methods by thread id: " + Thread.currentThread().getId());
        try {
            // Hardcoded sleep to simulate a server response
            Thread.sleep(SLEEP_TIME);
            Gson gson = new Gson();
            Log.d(LibraryConstants.TAG, "Getting the result by thread id: " + Thread.currentThread().getId());
            MonumentList monuments = gson.fromJson(
                    FileUtils.getMonumentDatabaseContent(context),
                    MonumentList.class);
            Log.d(LibraryConstants.TAG, "Return the succeed result by thread id: " + Thread.currentThread().getId());
            return (ArrayList<Monument>) monuments.getMonuments();
        } catch (Exception e) {
            Log.d(LibraryConstants.TAG, "Return the failed exception by thread id: " + Thread.currentThread().getId());
            Log.e(LibraryConstants.TAG, e.getMessage());
            return null;
        }
    }

    /**
     * Method to get a monument using the id
     */
    public void getMonumentById(final int id, final GetCallback<Monument> callback) {
        new Thread(new Runnable() {
            public void run() {
                try {
                    Monument monument;
                    // Hardcoded sleep to simulate a server response
                    Thread.sleep(SLEEP_TIME);
                    Gson gson = new Gson();
                    MonumentList monuments = gson.fromJson(
                            FileUtils.getMonumentDatabaseContent(context),
                            MonumentList.class);
                    for (Monument m : monuments.getMonuments()) {
                        if (m.getId() == id) {
                            callback.onSuccess(m);
                            break;
                        }
                    }
                } catch (Exception e) {
                    Log.e(LibraryConstants.TAG, e.getMessage());
                    callback.onFailure(e);
                }
            }
        }).start();
    }

    /**
     * Method to get a monument using the id
     */
    public Monument getMonumentById(int id) {
        try {
            // Hardcoded sleep to simulate a server response
            Thread.sleep(SLEEP_TIME);
            Gson gson = new Gson();
            MonumentList monuments = gson.fromJson(
                    FileUtils.getMonumentDatabaseContent(context),
                    MonumentList.class);
            for (Monument m : monuments.getMonuments()) {
                if (m.getId() == id) {
                    return m;
                }
            }
        } catch (Exception e) {
            Log.e(LibraryConstants.TAG, e.getMessage());
        }
        return null;
    }

    /**
     * Method to return a bitmap of the database image
     * @param imageName
     * @return
     */
    public Bitmap getImage(String imageName) {
        return FileUtils.getMonumentImage(context, imageName);
    }

    /**
     * Return a monument item depending with introduced data
     * @param name
     * @param country
     * @param description
     * @param image
     * @return
     */
    private Monument getMonument(
            String name,
            String country,
            String description,
            Bitmap image) {
        Monument monument = new Monument();
        // Set id
        monument.setId(new Gson().fromJson(
                FileUtils.getMonumentDatabaseContent(context),
                MonumentList.class).getMonuments().size());
        // Set name
        monument.setName(name);
        // Set country
        monument.setCountry(country);
        // Set description
        monument.setName(description);
        // Set image
        monument.setImagePath(FileUtils.saveBitmapFile(context, image, monument.getId()));
        return monument;
    }
}
