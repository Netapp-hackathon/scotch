package com.netapp.scotch;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;

/**
 * Created by shivamk on 09-Dec-15.
 */
public class Utils {
    @NonNull
    public static String getEndpoint(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString("cloud_server_endpoint", "0.0.0.0:0000");
    }
    @NonNull
    public static String getToken(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString("authToken", "");
    }
}
