package com.gaetan.kryxcore.listener;

import com.gaetan.kryxcore.CorePlugin;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public final class EntityListener implements Listener {
    /**
     * Reference to the main class
     */
    private final CorePlugin corePlugin;

    /**
     * Constructor for the EntityListener class.
     *
     * @param corePlugin Reference to te main class
     */
    public EntityListener(final CorePlugin corePlugin) {
        this.corePlugin = corePlugin;
        this.corePlugin.getServer().getPluginManager().registerEvents(this, this.corePlugin);
    }

    /**
     * When a entity receive a damage
     */
    @EventHandler
    public void onDamage(final EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            if (event.getCause() == EntityDamageEvent.DamageCause.FALL) {
                if (this.corePlugin.getPlayer((Player) event.getEntity()).isAtoutJumpBoost())
                    event.setCancelled(true);
            }
        }
    }
}
