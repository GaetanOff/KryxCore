package com.gaetan.kryxcore.manager.managers;

import com.gaetan.api.runnable.TaskUtil;
import com.gaetan.kryxcore.manager.Manager;
import com.gaetan.kryxcore.manager.ManagerHandler;
import com.gaetan.kryxcore.runnable.multithreading.DayRunnable;

import java.util.concurrent.TimeUnit;

public final class DayManager extends Manager {
    /**
     * Constructor for the DayManager class.
     *
     * @param handler Reference to the ManagerHandler
     */
    public DayManager(final ManagerHandler handler) {
        super(handler);

        //Implementing Multi-threading
        this.handler.getThreadManager().getThreadRunnablePool().scheduleAtFixedRate(
                new DayRunnable(this.handler.getCorePlugin()), 0, 5, TimeUnit.MINUTES
        );
    }


    public void setDayInAllWorld() {
        //Re-sync cuz we are not in the main thread before !
        TaskUtil.run(() -> this.handler.getCorePlugin().getServer().getWorlds().forEach(world -> world.setTime(0L)));
    }
}
