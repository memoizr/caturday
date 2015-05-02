package com.caturday.app.util.concurrent;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class WorkExecutor implements ThreadExecutor {

    private static final int INITIAL_POOL_SIZE = 3;
    private static final int MAX_POOL_SIZE = 5;
    private static final int KEEP_ALIVE_TIME = 10;
    private static final TimeUnit KEEP_ALIVE_TIME_UNIT = TimeUnit.SECONDS;
    private final BlockingQueue<Runnable> workQueue;
    private final ThreadPoolExecutor threadPoolExecutor;
    private final ThreadFactory threadFactory;

    public WorkExecutor() {

        this.workQueue = new LinkedBlockingQueue<>();
        this.threadFactory = new JobThreadFactory();
        this.threadPoolExecutor = new ThreadPoolExecutor(INITIAL_POOL_SIZE, MAX_POOL_SIZE,
                KEEP_ALIVE_TIME, KEEP_ALIVE_TIME_UNIT, this.workQueue, this.threadFactory);
    }

    @Override
    public void execute(Runnable runnable) {

        if (runnable == null) {
            throw new IllegalArgumentException("Runnable to execute cannot be null");
        }

        this.threadPoolExecutor.execute(runnable);
    }

    private static class JobThreadFactory implements ThreadFactory {

        private static final String THREAD_NAME = "caturday_";
        private static int threadCount = 0;

        @Override
        public Thread newThread(Runnable runnable) {
            int counter = threadCount;
            threadCount++;
            return new Thread(runnable, THREAD_NAME + counter);
        }
    }
}
