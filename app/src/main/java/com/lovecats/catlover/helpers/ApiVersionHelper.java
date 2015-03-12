package com.lovecats.catlover.helpers;

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
