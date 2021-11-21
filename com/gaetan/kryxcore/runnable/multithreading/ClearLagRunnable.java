package com.gaetan.kryxcore.runnable.multithreading;

import com.gaetan.api.message.Message;
import com.gaetan.api.runnable.TaskUtil;
import com.gaetan.kryxcore.CorePlugin;
import com.gaetan.kryxcore.enums.Lang;

public final class ClearLagRunnable implements Runnable {
    /**
     * Reference to the main class
     */
    private final CorePlugin corePlugin;
    private int counter;

    /**
     * Constructor for the ClearLagRunnable runnable.
     *
     * @param corePlugin Reference to te main class
     */
    public ClearLagRunnable(final CorePlugin corePlugin) {
        this.corePlugin = corePlugin;
        this.counter = 300;
    }

    @Override
    public void run() {
        switch (this.counter) {
            case 180:
                Message.tellToEveryone(Lang.CL_COUNTER.getText().replace("%s", "3 minutes"));
                break;
            case 60:
                Message.tellToEveryone(Lang.CL_COUNTER.getText().replace("%s", "1 minute"));
                break;
            case 30:
                Message.tellToEveryone(Lang.CL_COUNTER.getText().replace("%s", "30 secondes"));
                break;
            case 0:
                //Re-sync cuz we are not in the main thread before !
                TaskUtil.run(() -> this.corePlugin.getManagerHandler().getClearLagManager().clearLagg());
                break;
        }
        if (this.counter == 0)
            this.counter = 300;

        this.counter -= 1;
    }
}
