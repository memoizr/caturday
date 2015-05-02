package com.caturday.app.util.concurrent;

public interface ThreadExecutor {

    void execute(final Runnable runnable);
}
