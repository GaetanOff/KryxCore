package com.gaetan.kryxcore.runnable.multithreading;

import com.gaetan.api.message.Message;
import com.gaetan.api.runnable.TaskUtil;
import com.gaetan.kryxcore.CorePlugin;
import com.gaetan.kryxcore.enums.Lang;
import com.gaetan.kryxcore.utils.Cuboid;

public final class MoneyZRunnable implements Runnable {
    /**
     * Reference to the main class
     */
    private final CorePlugin corePlugin;

    private final Cuboid cuboid;

    /**
     * Constructor for the MoneyZRunnable runnable.
     *
     * @param corePlugin Reference to te main class
     */
    public MoneyZRunnable(final CorePlugin corePlugin, final Cuboid cuboid) {
        this.corePlugin = corePlugin;
        this.cuboid = cuboid;
    }

    @Override
    public void run() {
        this.corePlugin.getServer().getOnlinePlayers().forEach(player -> {
            if (this.cuboid.contains(player.getLocation())) {
                this.corePlugin.getManagerHandler().getEconomy().depositPlayer(player, 500.0);
                Message.tell(player, Lang.MONEY_ZONE_EARN.getText().replace("%s", "§6500$"));
            }
        });
    }
}
