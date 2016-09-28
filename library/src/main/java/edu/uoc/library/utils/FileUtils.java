package edu.uoc.library.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import edu.uoc.library.model.Monument;
import edu.uoc.library.model.MonumentList;

/**
 * Created by sgar810 on 27/09/2016.
 */
public class FileUtils {
    private static final String TAG = FileUtils.class.getSimpleName();

    // Directory
    private static final String FINAL_DIR = "assets";
    // Folder
    private static final String FOLDER_DATABASE = "database";
    private static final String FOLDER_IMAGES = "images";
    // Files
    private static final String FILE_MONUMENTS = "monuments.json";

    /**
     * Check if assets exists in the internal memory
     * @param context
     * @return
     */
    public static boolean existFolders(Context context) {
        File destDir = context.getDir(FINAL_DIR, Context.MODE_PRIVATE);
        File folderDatabase = new File(destDir, FOLDER_DATABASE);
        File folderImages = new File(destDir, FOLDER_IMAGES);
        return folderDatabase.exists() && folderImages.exists();
    }

    /**
     * Copy the assets of the app in the internal memory of the app
     * @param context
     */
    public static boolean createAssetsApp(Context context) {
        File destDir = context.getDir(FINAL_DIR, Context.MODE_PRIVATE);
        // Copy database asset folder to internal memory
        File folderDatabase = new File(destDir, FOLDER_DATABASE);
        folderDatabase.mkdirs();
        if (!copyFolder(context, folderDatabase, FOLDER_DATABASE)) return false;
        // Copy images asset folder to internal memory
        File folderImages = new File(destDir, FOLDER_IMAGES);
        folderImages.mkdirs();
        if (!copyFolder(context, folderImages, FOLDER_IMAGES)) return false;
        // If all has worked ok return true;
        return true;
    }

    /**
     * Method to return the JSON monuments file
     * @param context
     * @return
     */
    public static String getMonumentDatabaseContent(Context context) {
        File destDir = context.getDir(FINAL_DIR, Context.MODE_PRIVATE);
        File folderDatabase = new File(destDir, FOLDER_DATABASE);
        try {
            return getStringFromFile(new File(folderDatabase, FILE_MONUMENTS).getPath());
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            return null;
        }
    }

    /**
     * Method to return the JSON monuments file
     * @param context
     * @return
     */
    public static void saveMonumentToDatabaseContent(Context context, Monument monument) {
        File destDir = context.getDir(FINAL_DIR, Context.MODE_PRIVATE);
        File folderDatabase = new File(destDir, FOLDER_DATABASE);
        try {
            Gson gson = new Gson();
            MonumentList monuments = gson.fromJson(
                    FileUtils.getMonumentDatabaseContent(context),
                    MonumentList.class);
            monument.setId(monuments.getMonuments().size());
            monuments.getMonuments().add(monument);
            File databaseFile = new File(folderDatabase, FILE_MONUMENTS);
            OutputStreamWriter outputStreamWriter = new FileWriter(databaseFile);
            Gson gsonWriter = new GsonBuilder().setPrettyPrinting().create();
            outputStreamWriter.write(gsonWriter.toJson(monuments));
            outputStreamWriter.close();
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }

    /**
     *
     * @param filePath
     * @return
     * @throws Exception
     */
    public static String getStringFromFile(String filePath) throws Exception {
        File fl = new File(filePath);
        FileInputStream fin = new FileInputStream(fl);
        String ret = convertStreamToString(fin);
        //Make sure you close all streams.
        fin.close();
        return ret;
    }

    /**
     * Method to get the bitmap of the stored image
     * @param context
     * @param imageName
     * @return
     */
    public static Bitmap getMonumentImage(Context context, String imageName) {
        File destDir = context.getDir(FINAL_DIR, Context.MODE_PRIVATE);
        File folderImages = new File(destDir, FOLDER_IMAGES);
        File image = new File(folderImages, imageName);
        if (image.exists()) {
            return BitmapFactory.decodeFile(image.getPath());
        } else {
            return null;
        }
    }

    /**
     * Persist bitmap to file image
     * @param context
     * @param bitmap
     * @param id
     * @return the stored file name
     */
    public static String saveBitmapFile(Context context, Bitmap bitmap, int id) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
        byte[] bitmapData = bos.toByteArray();
        ByteArrayInputStream bs = new ByteArrayInputStream(bitmapData);
        File destDir = context.getDir(FINAL_DIR, Context.MODE_PRIVATE);
        File folderImages = new File(destDir, FOLDER_IMAGES);
        if (copyFile(folderImages, id + ".jpg", bs)) {
            return id + ".jpg";
        } else {
            return "";
        }
    }

    private static String convertStreamToString(InputStream is) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line).append("\n");
        }
        reader.close();
        return sb.toString();
    }

    private static boolean copyFolder(Context context, File destDir, String folderName) {
        try {
            String[] files = context.getAssets().list(folderName);
            for (String fileName : files) {
                InputStream in = context.getAssets().open(folderName + "/" + fileName);
                if (!copyFile(destDir, fileName, in)) return false;
            }
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
            return false;
        }
        return true;
    }

    private static boolean copyFile(File destDir, String fileName, InputStream in) {
        boolean result;
        OutputStream outputStream = null;
        try {
            File file = new File(destDir, fileName);
            outputStream = new FileOutputStream(file);
            int read;
            byte[] bytes = new byte[1024];

            while ((read = in.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }
            outputStream.flush();
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    Log.e(TAG, e.getMessage());
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    Log.e(TAG, e.getMessage());
                }
            }
            result = true;
        }
        return result;
    }
}
