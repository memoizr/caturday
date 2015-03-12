package com.lovecats.catlover;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by user on 10/03/15.
 */
public class Config {
    private static final String API_VERSION = "/api/v1";
    private static Context mContext;

    public Config(Context context) {
        mContext = context;
    }

    public static String getEndpoint() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
        String rootUrl = prefs.getString("example_text", null);
        String endpoint = rootUrl + API_VERSION;
        return endpoint;
    }
}
