package com.lovecats.catlover.util.concurrent;

public abstract class WorkerCallback<T> {

    public abstract void done(T result);
}
