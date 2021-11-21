package com.gaetan.kryxcore.listener.cooldown;

import com.gaetan.api.message.Message;
import com.gaetan.kryxcore.CorePlugin;
import com.gaetan.kryxcore.enums.Lang;
import com.massivecraft.factions.Board;
import com.massivecraft.factions.FLocation;
import com.massivecraft.factions.Faction;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.UUID;
import java.util.WeakHashMap;

public final class Enderpearl implements Listener {
    /*
     * Reference to the main class
     */
    private final CorePlugin corePlugin;

    private final WeakHashMap<UUID, Long> lastThrow = new WeakHashMap<>();
    private final Long time;
    private final String msg = Lang.COOLDOWN.getText();

    /*
     * Constructor for the Enderpearl class.
     *
     * @param corePlugin Reference to te main class
     */
    public Enderpearl(final CorePlugin corePlugin) {
        this.corePlugin = corePlugin;
        this.corePlugin.getServer().getPluginManager().registerEvents(this, this.corePlugin);

        this.time = (long) corePlugin.getManagerHandler().getConfigManager().getCooldownEnderpearl();
    }

    @EventHandler
    public void onPlayerUseEP(final PlayerInteractEvent e) {
        if (!e.isCancelled() || e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (e.getItem() != null && e.getItem().getType() != null && e.getItem().getType() == Material.ENDER_PEARL) {
                final Player p = e.getPlayer();
                final Location location = p.getLocation();
                final FLocation fLocation = new FLocation(location);
                final Faction otherFaction = Board.getInstance().getFactionAt(fLocation);
                final long now = System.currentTimeMillis();

                if (otherFaction.isWarZone()) {
                    e.setCancelled(true);
                    Message.tell(p, Lang.EP_DISABLED_WZ.getText());
                    return;
                }

                if (!this.validthrow(p, now)) {
                    e.setCancelled(true);
                    p.updateInventory();
                } else {
                    this.lastThrow.put(p.getUniqueId(), now);
                }
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
