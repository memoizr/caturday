package com.lovecats.catlover.common;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import lombok.Getter;

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
        String rootUrl = prefs.getString("example_text", null);
        String endpoint = rootUrl + API_VERSION;
        return endpoint;
    }
}
