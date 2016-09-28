package edu.uoc.monuments.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by UOC on 28/09/2016.
 */
public class ApplicationUtils {

    // SharedPreferences name
    private final static String SHARED_PREFERENCES_NAME = "LOGIN_PREFERENCES";
    // SharedPreferences key
    private final static String SHARED_IS_LOGGED = "SHARED_IS_LOGGED";

    /**
     * Set the user login state
     * @param context
     * @param logged
     */
    public static void setUserLoginState(Context context, boolean logged) {
        SharedPreferences sharedPref = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(SHARED_IS_LOGGED, logged);
        editor.commit();
    }

    /**
     * Check if user is logged
     * @param context
     * @return
     */
    public static boolean isLogged(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        return sharedPref.getBoolean(SHARED_IS_LOGGED, false);
    }

}
