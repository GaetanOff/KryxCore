package com.gaetan.kryxcore.manager.managers;

import com.gaetan.api.item.ItemBuilder;
import com.gaetan.kryxcore.enums.Lang;
import com.gaetan.kryxcore.manager.Manager;
import com.gaetan.kryxcore.manager.ManagerHandler;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public final class ItemManager extends Manager {
    /**
     * The staff mode lobby items
     */
    private ItemStack[] staffItems;

    /**
     * Constructor for the PlayerListener class.
     * Note: This will cache the Staff Items
     *
     * @param managerHandler Reference to te managerHandler class
     */
    public ItemManager(final ManagerHandler managerHandler) {
        super(managerHandler);

        this.loadStaffItems();
    }

    /**
     * Load the default staff items
     */
    private void loadStaffItems() {
        this.staffItems = new ItemStack[]{
                new ItemBuilder(Material.WOOD_SWORD).setName(Lang.KB_SWORD.getText()).addEnchant(Enchantment.KNOCKBACK, 10).setUnbreakable().toItemStack(),
                new ItemBuilder(Material.COMPASS).setName(Lang.WTP.getText()).toItemStack(),
                new ItemBuilder(Material.EYE_OF_ENDER).setName(Lang.TELEPORTATION.getText()).toItemStack(),
                new ItemBuilder(Material.CHEST).setName(Lang.STAFF.getText()).toItemStack(),
                new ItemBuilder(Material.BOOK).setName(Lang.INSPECTER_INV.getText()).toItemStack(),
                new ItemBuilder(Material.ICE).setName(Lang.FREEZE.getText()).toItemStack(),
                null,
                null,
                new ItemBuilder(Material.REDSTONE_TORCH_ON).setName(Lang.QUIT.getText()).toItemStack()
        };
    }

    /**
     * Getter to get the staff items
     *
     * @return The staff items
     */
    public ItemStack[] getStaffItems() {
        return this.staffItems;
    }
}
