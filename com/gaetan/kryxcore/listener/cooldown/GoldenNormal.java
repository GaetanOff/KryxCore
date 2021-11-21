package com.gaetan.kryxcore.listener.cooldown;

import com.gaetan.kryxcore.CorePlugin;
import com.gaetan.kryxcore.enums.Lang;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;

import java.util.UUID;
import java.util.WeakHashMap;

public final class GoldenNormal implements Listener {
    /**
     * Reference to the main class
     */
    private final CorePlugin corePlugin;

    private final WeakHashMap<UUID, Long> lastThrow = new WeakHashMap<>();
    private final Long time;
    private final String msg = Lang.COOLDOWN.getText();

    /**
     * Constructor for the GoldenNormal class.
     *
     * @param corePlugin Reference to te main class
     */
    public GoldenNormal(final CorePlugin corePlugin) {
        this.corePlugin = corePlugin;
        this.corePlugin.getServer().getPluginManager().registerEvents(this, this.corePlugin);

        this.time = (long) corePlugin.getManagerHandler().getConfigManager().getCooldownApple();
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerUseEP(final PlayerInteractEvent e) {
        final Player p = e.getPlayer();

        if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (e.getItem() != null && e.getItem().getDurability() == 0 && e.getItem().getType() == Material.GOLDEN_APPLE) {
                final long now = System.currentTimeMillis();
                if (!this.validthrow(p, now)) {
                    e.setCancelled(true);
                    p.updateInventory();
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerConsumeItem(final PlayerItemConsumeEvent e) {
        final Player p = e.getPlayer();

        if (e.getItem().getType() == Material.GOLDEN_APPLE && e.getItem().getDurability() == 0) {
            final long now = System.currentTimeMillis();
            if (this.validthrow(p, now)) {
                this.lastThrow.put(p.getUniqueId(), now);
            } else {
                e.setCancelled(true);
                p.updateInventory();
            }
        }
    }

    public double remainingCooldown(final Player p, final long throwTime) {
        final Long lastPlayerPearl = this.lastThrow.get(p.getUniqueId());
        return (this.time - (throwTime - lastPlayerPearl)) / 1000.0;
    }

    public boolean validthrow(final Player p, final long throwTime) {
        final Long lastPlayerPearl = this.lastThrow.get(p.getUniqueId());
        if (lastPlayerPearl == null || throwTime - lastPlayerPearl >= this.time) return true;
        this.sendMessageChecked(p, this.msg.replace("{sec}", String.format("%.1f", this.remainingCooldown(p, throwTime))));
        return false;
    }

    public void sendMessageChecked(final Player p, final String message) {
        p.sendMessage(message);
    }
}
