package com.gaetan.kryxcore.manager.managers;

import com.gaetan.kryxcore.manager.Manager;
import com.gaetan.kryxcore.manager.ManagerHandler;

public final class MoneyZoneManager extends Manager {
    /**
     * Constructor for the MoneyZoneManager class.
     *
     * @param handler Reference to the ManagerHandler
     */
    public MoneyZoneManager(final ManagerHandler handler) {
        super(handler);

        this.setupMoneyZone();
    }

    private void setupMoneyZone() {
        /**final Location loc1 = new Location(this.handler.getCorePlugin().getServer().getWorld("world"), -2.0, 76.0, -210.0);
         final Location loc2 = new Location(this.handler.getCorePlugin().getServer().getWorld("world"), 0.0, 79.0, -212.0);
         final Cuboid cuboid = new Cuboid(loc1, loc2);

         //Implementing Multi-threading
         this.handler.getThreadManager().getThreadRunnablePool().scheduleAtFixedRate(
         new MoneyZRunnable(this.handler.getCorePlugin(), cuboid), 0, 10, TimeUnit.SECONDS
         );
         **/
    }
}
