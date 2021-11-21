package com.gaetan.kryxcore.runnable.multithreading;

import com.gaetan.kryxcore.CorePlugin;

public final class DayRunnable implements Runnable {
    /**
     * Reference to the main class
     */
    private final CorePlugin corePlugin;

    /**
     * Constructor for the DayRunnable runnable.
     *
     * @param corePlugin Reference to the main class
     */
    public DayRunnable(final CorePlugin corePlugin) {
        this.corePlugin = corePlugin;
    }

    @Override
    public void run() {
        this.corePlugin.getManagerHandler().getDayManager().setDayInAllWorld();
    }
}
