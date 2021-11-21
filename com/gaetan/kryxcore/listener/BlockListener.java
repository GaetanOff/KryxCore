package com.gaetan.kryxcore.listener;

import com.gaetan.kryxcore.CorePlugin;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public final class BlockListener implements Listener {
    /**
     * Reference to the main class
     */
    private final CorePlugin corePlugin;

    /**
     * Constructor for the BlockListener class.
     *
     * @param corePlugin Reference to te main class
     */
    public BlockListener(final CorePlugin corePlugin) {
        this.corePlugin = corePlugin;
        this.corePlugin.getServer().getPluginManager().registerEvents(this, this.corePlugin);
    }

    /**
     * When a player place a block.
     * Note: This will cancel the place blocks for the staffmode
     * and disable the creation of Wither
     */
    @EventHandler
    public void onPlace(final BlockPlaceEvent event) {
        if (this.corePlugin.getPlayer(event.getPlayer()).isStaffMode() || (event.getBlockPlaced().getType() == Material.SKULL && event.getBlockPlaced().getRelative(0, -1, 0).getType() == Material.SOUL_SAND))
            event.setCancelled(true);
    }

    /**
     * When a player break a block.
     * Note: This will cancel the break blocks for the staffmode
     */
    @EventHandler
    public void onBreak(final BlockBreakEvent event) {
        if (this.corePlugin.getPlayer(event.getPlayer()).isStaffMode())
            event.setCancelled(true);
    }
}
