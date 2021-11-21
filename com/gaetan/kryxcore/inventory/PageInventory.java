package com.gaetan.kryxcore.inventory;

import com.gaetan.api.item.ItemBuilder;
import com.gaetan.api.message.Message;
import com.gaetan.kryxcore.CorePlugin;
import com.gaetan.kryxcore.enums.Lang;
import com.google.common.collect.Lists;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public final class PageInventory {
    private final String title;
    private final List<ItemStack> contents;
    private final ItemStack oldPage;
    private final ItemStack nextPage;
    private int page;
    private int totalPages;

    /**
     * Constructor for the PageInventory class.
     *
     * @param title Title of the paginated inventory
     */
    public PageInventory(final String title) {
        this.page = 1;
        this.totalPages = 1;
        this.title = title;
        this.contents = new ArrayList<>();
        this.nextPage = new ItemBuilder(Material.ARROW).setName(ChatColor.GREEN + "Page suivante ->").toItemStack();
        this.oldPage = new ItemBuilder(Material.ARROW).setName(ChatColor.GREEN + "<- Page précédent").toItemStack();
    }

    public void addItem(final ItemStack item) {
        this.contents.add(item);
        this.setTotalPages();
    }

    public void openInventory(final Player player) {
        if (this.totalPages == 1) {
            final Inventory inventory = Bukkit.createInventory(null, this.calcSize(this.contents.size()), this.title);
            int slot = 0;
            for (final ItemStack item : this.contents) {
                inventory.setItem(slot++, item);
            }
            player.openInventory(inventory);
            return;
        }
        int startPoint = (this.page - 1) * 45;
        final List<ItemStack> invContents = Lists.newArrayList();
        try {
            ItemStack item2;
            while ((item2 = this.contents.get(startPoint++)) != null) {
                invContents.add(item2);
                if (startPoint - (this.page - 1) * 45 == 45)
                    break;

            }
        } catch (final IndexOutOfBoundsException ignored) {
        }
        final Inventory inventory2 = Bukkit.createInventory(null, 54, this.title);
        int slot2 = 0;
        for (final ItemStack invItem : invContents) {
            inventory2.setItem(slot2++, invItem);
        }
        if (this.page > 1)
            inventory2.setItem(45, this.oldPage);

        if (this.page < this.getPages(this.contents.size()))
            inventory2.setItem(53, this.nextPage);

        player.openInventory(inventory2);
    }

    public void executeClickEvent(final CorePlugin plugin, final Player player, final int slot, final InventoryClickEvent event) {
        event.setCancelled(true);
        if (slot == 45 && this.page > 1) {
            --this.page;
            this.openInventory(player);
        }
        if (slot == 53 && this.page < this.getPages(this.contents.size())) {
            ++this.page;
            this.openInventory(player);
        }
        if (event.getCurrentItem() != null && event.getCurrentItem().getType() == Material.SKULL_ITEM && event.getCurrentItem().hasItemMeta() && event.getCurrentItem().getItemMeta().hasDisplayName() && event.getCurrentItem().getItemMeta().hasLore()) {
            final Player target = Bukkit.getPlayer(ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()).replace("» ", ""));

            if (target == null) {
                player.closeInventory();
                Message.tell(player, Lang.STAFF_TP_UNK.getText());
                return;
            }

            player.closeInventory();
            player.chat("/tp " + target.getName());
        }

    }

    public List<ItemStack> getContents() {
        return this.contents;
    }

    private void setTotalPages() {
        this.totalPages = (this.contents.size() > 54) ? (this.contents.size() / 45) : 1;
    }

    private int calcSize(final int size) {
        return ((size - 1) / 9 + 1) * 9;
    }

    private int getPages(final int size) {
        if (size % 45 == 0)
            return size / 45;

        return (int) Math.ceil((double) size / 45);
    }
}
