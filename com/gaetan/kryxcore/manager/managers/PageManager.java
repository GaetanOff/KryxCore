package com.gaetan.kryxcore.manager.managers;

import com.gaetan.api.TimeUtil;
import com.gaetan.api.item.ItemUtil;
import com.gaetan.api.message.Message;
import com.gaetan.kryxcore.enums.Lang;
import com.gaetan.kryxcore.inventory.PageInventory;
import com.gaetan.kryxcore.manager.Manager;
import com.gaetan.kryxcore.manager.ManagerHandler;
import org.bukkit.Statistic;

import java.util.ArrayList;
import java.util.List;

public final class PageManager extends Manager {
    private final PageInventory pageInventory;

    /**
     * Constructor for the PageManager class.
     *
     * @param handler Reference to the ManagerHandler
     */
    public PageManager(final ManagerHandler handler) {
        super(handler);

        this.pageInventory = new PageInventory(Lang.STAFF_ONLINE.getText());
    }

    public void setPageInventory() {
        this.pageInventory.getContents().clear();

        this.handler.getCorePlugin().getServer().getOnlinePlayers().stream()
                .filter(player -> player.hasPermission("staff.use"))
                .forEach(player -> {
                    final List<String> loreStaff = new ArrayList<>();

                    loreStaff.add("");
                    loreStaff.add("§7\u27a5 §6Grade§e: " + Message.tl(this.handler.getCorePlugin().getManagerHandler().getChat().getPlayerPrefix(player)));
                    loreStaff.add("§7\u27a5 §6Staff Mode§e: " + (this.handler.getCorePlugin().getPlayer(player).isStaffMode() ? "§aActivé" : "§cDesactivé"));
                    loreStaff.add("§7\u27a5 §6Temps de jeu§e: " + TimeUtil.getTime(player.getStatistic(Statistic.PLAY_ONE_TICK) / 20));
                    loreStaff.add("");
                    loreStaff.add("§7\u27a4 §eCliquez ici pour vous téléporter au staff");

                    this.pageInventory.addItem(ItemUtil.getPlayerHead(player, loreStaff));
                });
    }

    public PageInventory getPageInventory() {
        return this.pageInventory;
    }
}
