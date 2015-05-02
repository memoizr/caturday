package com.caturday.app.util.concurrent;

public interface PostExecutionThread {

    void post(Runnable runnable);
}
