package com.gaetan.kryxcore.listener;

import com.gaetan.kryxcore.CorePlugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.PortalCreateEvent;

public final class PortalListener implements Listener {
    /**
     * Constructor for the PortalListener class.
     *
     * @param corePlugin Reference to te main class
     */
    public PortalListener(final CorePlugin corePlugin) {
        corePlugin.getServer().getPluginManager().registerEvents(this, corePlugin);
    }

    /**
     * When a new portal created.
     * Note: This will cancel the new creation of portal
     */
    @EventHandler
    public void onPortalCreate(final PortalCreateEvent event) {
        event.setCancelled(true);
    }
}
