package com.caturday.app.capsules.common;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Config {
    private static final String API_VERSION = "/api/v1";
    private static Context mContext;
    private static Application app;
    private static String DEFAULT_ENDPOINT = "http://protected-lake-7372.herokuapp.com";
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
            String address = DEFAULT_ENDPOINT;
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
