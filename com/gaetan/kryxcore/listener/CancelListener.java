package com.gaetan.kryxcore.listener;

import com.gaetan.api.message.Message;
import com.gaetan.kryxcore.CorePlugin;
import com.gaetan.kryxcore.enums.Lang;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public final class CancelListener implements Listener {

    /**
     * Constructor for the CancelListener class.
     *
     * @param corePlugin Reference to te main class
     */
    public CancelListener(final CorePlugin corePlugin) {
        /**
         * Reference to the main class
         */
        corePlugin.getServer().getPluginManager().registerEvents(this, corePlugin);
    }

    /**
     * When a player consume an item
     */
    @EventHandler()
    public void onEat(final PlayerItemConsumeEvent event) {
        final Player player = event.getPlayer();

        if (event.getItem() != null) {
            final ItemStack itemStack = event.getItem();

            if (itemStack.getType() == Material.POTION) {
                switch (itemStack.getDurability()) {
                    case 8233:
                    case 8234:
                    case 8266:
                    case 8232:
                    case 8238:
                    case 8264:
                    case 8270: {
                        event.setCancelled(true);
                        player.updateInventory();
                        Message.tell(player, Lang.ITEM_DISABLED.getText());
                        break;
                    }
                }
            }
            if (itemStack.getType() == Material.GOLDEN_APPLE && itemStack.getDurability() == 1) {
                event.setCancelled(true);
                player.updateInventory();
                Message.tell(player, Lang.ITEM_DISABLED.getText());
            }
        }
    }

    /**
     * When a player interact
     */
    @EventHandler
    public void onInteract(final PlayerInteractEvent event) {
        final Player player = event.getPlayer();

        if (event.getItem() != null && (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)) {
            final ItemStack itemStack = event.getItem();

            if (itemStack.getType() == Material.POTION) {
                switch (itemStack.getDurability()) {
                    case 16426:
                    case 16458:
                    case 16430:
                    case 16462:
                    case 16420:
                    case 16452:
                    case 16424:
                    case 16456:
                    case 16388:
                    case 16425: {
                        event.setCancelled(true);
                        player.updateInventory();
                        Message.tell(player, Lang.ITEM_DISABLED.getText());
                        break;
                    }
                }
            }
            if (itemStack.getType() == Material.GOLDEN_APPLE && itemStack.getDurability() == 1) {
                event.setCancelled(true);
                player.updateInventory();
                Message.tell(player, Lang.ITEM_DISABLED.getText());
            }
        }
    }

    /**
     * When a player interact
     * Note: This code is not from me
     */
    @EventHandler
    public void onPlayerInteract(final PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getClickedBlock().getType().equals((Object) Material.DISPENSER)) {
            event.setCancelled(true);
            Message.tell(event.getPlayer(), Lang.ITEM_DISABLED.getText());
        }
    }

    /**
     * When a player splash potion
     * Note: This code is not from me
     */
    @EventHandler
    public void onPotionSplash(final PotionSplashEvent event) {
        if (event.getPotion() != null && event.getPotion().getItem() != null && Arrays.asList((short) 16462, (short) 16430, (short) 16425).contains(event.getPotion().getItem().getDurability())) {
            event.setCancelled(true);
        }
    }
}
