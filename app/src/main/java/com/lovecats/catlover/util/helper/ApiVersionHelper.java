package com.lovecats.catlover.util.helper;

import android.os.Build;

public class ApiVersionHelper {
    public static boolean supportsAPI(int targetAPI) {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= targetAPI)
            return true;
        else
            return false;
    }
}
