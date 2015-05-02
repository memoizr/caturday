package com.caturday.app.util.concurrent;

public abstract class WorkerCallback<T> {

    public abstract void done(T result);
}
