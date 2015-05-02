package com.caturday.app.util.concurrent;

import android.os.Handler;
import android.os.Looper;

public class MainThread implements PostExecutionThread {

    private final Handler handler;

    public MainThread() {
        this.handler = new Handler(Looper.getMainLooper());
    }

    @Override
    public void post(Runnable runnable) {
        handler.post(runnable);
    }
}
