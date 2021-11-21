package com.gaetan.kryxcore.manager.managers;

import com.gaetan.kryxcore.manager.Manager;
import com.gaetan.kryxcore.manager.ManagerHandler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public final class ThreadManager extends Manager {
    /**
     * Reference to the ES Thread Pool
     */
    private final ExecutorService threadPool;

    /**
     * Reference to the ES Runnable Thread Pool
     */
    private final ScheduledExecutorService threadRunnablePool;

    /**
     * Constructor for the ThreadManager class
     *
     * @param handler Reference to the ManagerHandler
     */
    public ThreadManager(final ManagerHandler handler) {
        super(handler);

        //Launch the pool for action
        this.threadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        //Launch the pool for runnable
        this.threadRunnablePool = Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors());
    }

    /**
     * Getter to get the reference to the ES Thread Pool.
     *
     * @return The reference to the ES Thread Pool.
     */
    public ExecutorService getThreadPool() {
        return this.threadPool;
    }

    /**
     * Getter to get the reference to the ES Runnable Thread Pool.
     *
     * @return The reference to the ES Runnable Thread Pool.
     */
    public ScheduledExecutorService getThreadRunnablePool() {
        return this.threadRunnablePool;
    }
}
