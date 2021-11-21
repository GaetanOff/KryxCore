package com.gaetan.kryxcore.inventory;

import com.gaetan.api.inventory.GuiBuilder;
import com.gaetan.api.message.Message;
import com.gaetan.kryxcore.CorePlugin;
import com.gaetan.kryxcore.enums.Lang;
import com.gaetan.kryxcore.manager.managers.ClassementManager;
import com.gaetan.kryxcore.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.stream.IntStream;

public final class ClassementInventory implements GuiBuilder {
    /**
     * Reference to the main class
     */
    private final CorePlugin corePlugin;

    /**
     * Constructor for the ClassementInventory class.
     *
     * @param corePlugin Reference to the main class
     */
    public ClassementInventory(final CorePlugin corePlugin) {
        this.corePlugin = corePlugin;
    }

    /**
     * Name of this inventory
     */
    @Override
    public String name() {
        return Lang.CLASSEMENT_INVENTORY_NAME.getText();
    }

    /**
     * Size of this inventory
     */
    @Override
    public int size() {
        return 45;
    }

    @Override
    public void contents(final Player player, final Inventory inventory) {
        final int size = inventory.getSize();
        final ClassementManager classementManager = this.corePlugin.getManagerHandler().getClassementManager();

        IntStream.range(0, size)
                .parallel()
                .filter(i -> i < 2 || (i > 6 && i < 10) || i == 17 || i == size - 18 || (i > size - 11 && i < size - 7) || i > size - 3)
                .forEach(i -> inventory.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (byte) 4).setName("  ").toItemStack()));

        inventory.setItem(21, new ItemBuilder(Material.DIAMOND_SWORD)
                .setName(Message.YELLOW + Message.BOLD + "\u2726 " + Message.GOLD + Message.BOLD + Lang.CLASSEMENT_INVENTORY_PVP.getText() + Message.YELLOW + Message.BOLD + " \u2726")
                .setLore(classementManager.getLorePvP())
                .hidePotionEffect()
                .hideAttributes()
                .toItemStack());

        inventory.setItem(23, new ItemBuilder(Material.SEEDS)
                .setName(Message.YELLOW + Message.BOLD + "\u2726 " + Message.GOLD + Message.BOLD + Lang.CLASSEMENT_INVENTORY_FARM.getText() + Message.YELLOW + Message.BOLD + " \u2726")
                .setLore(classementManager.getLoreFarm())
                .hidePotionEffect()
                .hideAttributes()
                .toItemStack());
    }

    @Override
    public void onClick(final Player player, final Inventory inventory, final ItemStack itemStack, final int i) {

    }

    @Override
    public void onClose(final Player player, final Inventory inventory) {

    }
}
