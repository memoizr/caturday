package com.lovecats.catlover.capsules.common;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by user on 10/03/15.
 */
public class Config {
    private static final String API_VERSION = "/api/v1";
    private static Context mContext;
    private static Application app;
    public static final int PAGINATION_LIMIT = 25;

    public Config(Application app) {
        this.app = app;
    }

    public Config(Context context) {
        mContext = context;
    }

    public static String getEndpoint() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(app);
        String rootUrl;
        rootUrl = prefs.getString("example_text", null);

        if (rootUrl == null) {
            SharedPreferences.Editor editor = prefs.edit();
            String address = "http://196.18.44.82:3000";
            editor.putString("example_text", address);
            rootUrl = address;
        }

        String endpoint = rootUrl + API_VERSION;
        return endpoint;
    }

    public static String getAuthToken() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(app);
        String authToken = prefs.getString("authToken", null);
        return authToken;
    }

    public static void setAuthToken(String authToken) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(app);
        prefs.edit().putString("authToken", authToken);
    }
}
