package com.gaetan.kryxcore.manager.managers;

import com.gaetan.api.PlayerUtil;
import com.gaetan.api.XPUtil;
import com.gaetan.api.item.ItemBuilder;
import com.gaetan.api.message.Message;
import com.gaetan.api.runnable.TaskUtil;
import com.gaetan.kryxcore.enums.Lang;
import com.gaetan.kryxcore.manager.Manager;
import com.gaetan.kryxcore.manager.ManagerHandler;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public final class BottleXPManager extends Manager {
    /**
     * Constructor for the BottleXPManager class.
     *
     * @param handler Reference to the ManagerHandler
     */
    public BottleXPManager(final ManagerHandler handler) {
        super(handler);
    }

    /**
     * This will give the bottle to the player.
     *
     * @param player The player will have the bottle
     */
    public void giveBottle(final Player player) {
        if (PlayerUtil.isInventoryFull(player)) {
            Message.tell(player, Lang.INVENTORY_FULL.getText());
            return;
        }

        final int countExp = XPUtil.getPlayerExp(player);
        if (countExp == 0) {
            Message.tell(player, Lang.NO_XP.getText());
            return;
        }

        //Sync cuz we are not on the main thread before !
        TaskUtil.run(() -> {
            player.getInventory().addItem(new ItemBuilder(Material.EXP_BOTTLE).setLore("", ChatColor.YELLOW + "Expérience: " + countExp, "", ChatColor.GRAY + "Clic droit pour récupèrer les niveaux.").setName(ChatColor.GOLD.toString() + ChatColor.BOLD + "BottleXP" + ChatColor.GRAY + " (" + player.getLevel() + " lvl)").toItemStack());
            XPUtil.saveXP(player);
        });
    }

    /**
     * This will reset the bottle xp to the player.
     *
     * @param player The player will have the reset
     * @param strExp The exp in the lore
     */
    public void useBottle(final Player player, final String strExp) {
        final int valueExp = Integer.parseInt(strExp);
        if (valueExp <= 0)
            return;

        XPUtil.changeExp(player, valueExp);
        if (player.getItemInHand().getAmount() > 1) {
            player.getItemInHand().setAmount(player.getItemInHand().getAmount() - 1);
            player.setItemInHand(player.getItemInHand());
        } else {
            player.setItemInHand(new ItemStack(Material.AIR));
        }

        Message.tell(player, Lang.XP_ADD.getText());
    }
}
