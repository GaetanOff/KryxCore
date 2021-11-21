package com.gaetan.kryxcore.manager.managers;

import com.gaetan.kryxcore.manager.Manager;
import com.gaetan.kryxcore.manager.ManagerHandler;
import com.gaetan.kryxcore.runnable.multithreading.AutoRunnable;

import java.util.concurrent.TimeUnit;

public final class AutoManager extends Manager {
    /**
     * Constructor for the AutoManager class.
     * Note: This will launch the runnable for the Auto-Message
     *
     * @param handler Reference to the ManagerHandler
     */
    public AutoManager(final ManagerHandler handler) {
        super(handler);

        //Implementing Multi-threading
        this.handler.getThreadManager().getThreadRunnablePool().scheduleAtFixedRate(
                new AutoRunnable(this.handler.getConfigManager().getAutoMessage()),
                0, this.handler.getConfigManager().getDelayAutoMessage(), TimeUnit.SECONDS
        );
    }
}
