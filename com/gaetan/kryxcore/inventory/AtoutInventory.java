package com.gaetan.kryxcore.inventory;

import com.gaetan.api.inventory.GuiBuilder;
import com.gaetan.api.message.Message;
import com.gaetan.kryxcore.CorePlugin;
import com.gaetan.kryxcore.data.PlayerData;
import com.gaetan.kryxcore.enums.Lang;
import com.gaetan.kryxcore.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

import java.util.stream.IntStream;

public final class AtoutInventory implements GuiBuilder {
    /**
     * Reference to the main class
     */
    private final CorePlugin corePlugin;

    /**
     * Constructor for the AtoutInventory class.
     *
     * @param corePlugin Reference to the main class
     */
    public AtoutInventory(final CorePlugin corePlugin) {
        this.corePlugin = corePlugin;
    }

    /**
     * Name of this inventory
     */
    @Override
    public String name() {
        return Lang.ATOUT_INVENTORY_NAME.getText();
    }

    /**
     * Size of this inventory
     */
    @Override
    public int size() {
        return 54;
    }

    @Override
    public void contents(final Player player, final Inventory inventory) {
        final PlayerData playerData = this.corePlugin.getPlayer(player);
        final int size = inventory.getSize();

        IntStream.range(0, size)
                .parallel()
                .filter(i -> i < 2 || (i > 6 && i < 10) || i == 17 || i == size - 18 || (i > size - 11 && i < size - 7) || i > size - 3)
                .forEach(i -> inventory.setItem(i, new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (byte) 4).setName("  ").toItemStack()));

        inventory.setItem(20, new ItemBuilder(Material.SUGAR).addEnchant(Enchantment.DURABILITY, 1).setName(Message.YELLOW + Message.BOLD + "\u2726 " + Message.GOLD + Message.BOLD + Lang.ATOUT_INVENTORY_SPEED.getText() + Message.YELLOW + Message.BOLD + " \u2726")
                .setLore("",
                        Message.GOLD + "\u2503 " + Message.WHITE + "Vous permet d'avoir un effet",
                        Message.GOLD + "\u2503 " + Message.WHITE + "de speed permanent.",
                        "",
                        Message.GOLD + Message.BOLD + "» " + Message.WHITE + "Accès: " + (player.hasPermission("atout.speed") ? Message.GREEN + "\u2714" : Message.RED + "\u2718" + Message.GRAY + " (/boutique)")
                        , Message.GOLD + Message.BOLD + "» " + Message.WHITE + "Activé: " + (playerData.isAtoutSpeed() ? Message.GREEN + "\u2714" : Message.RED + "\u2718"),
                        "",
                        Message.GREEN + "Cliquez pour " + (playerData.isAtoutSpeed() ? "désactiver" : "activer"))
                .hideEnchant()
                .hidePotionEffect()
                .hideAttributes()
                .toItemStack());

        inventory.setItem(21, new ItemBuilder(Material.DIAMOND_HELMET).addEnchant(Enchantment.DURABILITY, 1).setName(Message.YELLOW + Message.BOLD + "\u2726 " + Message.GOLD + Message.BOLD + Lang.ATOUT_INVENTORY_WB.getText() + Message.YELLOW + Message.BOLD + " \u2726")
                .setLore("",
                        Message.GOLD + "\u2503 " + Message.WHITE + "Vous permet d'avoir un effet",
                        Message.GOLD + "\u2503 " + Message.WHITE + "de WaterBreathing permanent.",
                        "",
                        Message.GOLD + Message.BOLD + "» " + Message.WHITE + "Accès: " + (player.hasPermission("atout.wb") ? Message.GREEN + "\u2714" : Message.RED + "\u2718" + Message.GRAY + " (/boutique)")
                        , Message.GOLD + Message.BOLD + "» " + Message.WHITE + "Activé: " + (playerData.isAtoutWater() ? Message.GREEN + "\u2714" : Message.RED + "\u2718"),
                        "",
                        Message.GREEN + "Cliquez pour " + (playerData.isAtoutWater() ? "désactiver" : "activer"))
                .hideEnchant()
                .hidePotionEffect()
                .hideAttributes()
                .toItemStack());


        inventory.setItem(22, new ItemBuilder(Material.DIAMOND_PICKAXE).addEnchant(Enchantment.DURABILITY, 1).setName(Message.YELLOW + Message.BOLD + "\u2726 " + Message.GOLD + Message.BOLD + Lang.ATOUT_INVENTORY_HASTE.getText() + Message.YELLOW + Message.BOLD + " \u2726")
                .setLore("",
                        Message.GOLD + "\u2503 " + Message.WHITE + "Vous permet d'avoir un effet",
                        Message.GOLD + "\u2503 " + Message.WHITE + "de haste permanent.",
                        "",
                        Message.GOLD + Message.BOLD + "» " + Message.WHITE + "Accès: " + (player.hasPermission("atout.haste") ? Message.GREEN + "\u2714" : Message.RED + "\u2718" + Message.GRAY + " (/boutique)")
                        , Message.GOLD + Message.BOLD + "» " + Message.WHITE + "Activé: " + (playerData.isAtoutHaste() ? Message.GREEN + "\u2714" : Message.RED + "\u2718"),
                        "",
                        Message.GREEN + "Cliquez pour " + (playerData.isAtoutHaste() ? "désactiver" : "activer"))
                .hideEnchant()
                .hidePotionEffect()
                .hideAttributes()
                .toItemStack());

        inventory.setItem(23, new ItemBuilder(Material.BLAZE_POWDER).addEnchant(Enchantment.DURABILITY, 1).setName(Message.YELLOW + Message.BOLD + "\u2726 " + Message.GOLD + Message.BOLD + Lang.ATOUT_INVENTORY_STR.getText() + Message.YELLOW + Message.BOLD + " \u2726")
                .setLore("",
                        Message.GOLD + "\u2503 " + Message.WHITE + "Vous permet d'avoir un effet",
                        Message.GOLD + "\u2503 " + Message.WHITE + "de force permanent.",
                        "",
                        Message.GOLD + Message.BOLD + "» " + Message.WHITE + "Accès: " + (player.hasPermission("atout.str") ? Message.GREEN + "\u2714" : Message.RED + "\u2718" + Message.GRAY + " (/boutique)")
                        , Message.GOLD + Message.BOLD + "» " + Message.WHITE + "Activé: " + (playerData.isAtoutStrenght() ? Message.GREEN + "\u2714" : Message.RED + "\u2718"),
                        "",
                        Message.GREEN + "Cliquez pour " + (playerData.isAtoutStrenght() ? "désactiver" : "activer"))
                .hideEnchant()
                .hidePotionEffect()
                .hideAttributes()
                .toItemStack());

        inventory.setItem(24, new ItemBuilder(Material.MAGMA_CREAM).addEnchant(Enchantment.DURABILITY, 1).setName(Message.YELLOW + Message.BOLD + "\u2726 " + Message.GOLD + Message.BOLD + Lang.ATOUT_INVENTORY_FIRE.getText() + Message.YELLOW + Message.BOLD + " \u2726")
                .setLore("",
                        Message.GOLD + "\u2503 " + Message.WHITE + "Vous permet d'avoir un effet",
                        Message.GOLD + "\u2503 " + Message.WHITE + "de fire résistance permanent.",
                        "",
                        Message.GOLD + Message.BOLD + "» " + Message.WHITE + "Accès: " + (player.hasPermission("atout.fire") ? Message.GREEN + "\u2714" : Message.RED + "\u2718" + Message.GRAY + " (/boutique)")
                        , Message.GOLD + Message.BOLD + "» " + Message.WHITE + "Activé: " + (playerData.isAtoutFireRes() ? Message.GREEN + "\u2714" : Message.RED + "\u2718"),
                        "",
                        Message.GREEN + "Cliquez pour " + (playerData.isAtoutFireRes() ? "désactiver" : "activer"))
                .hideEnchant()
                .hidePotionEffect()
                .hideAttributes()
                .toItemStack());

        inventory.setItem(31, new ItemBuilder(Material.DIAMOND_BOOTS).addEnchant(Enchantment.DURABILITY, 1).setName(Message.YELLOW + Message.BOLD + "\u2726 " + Message.GOLD + Message.BOLD + Lang.ATOUT_INVENTORY_JUMP.getText() + Message.YELLOW + Message.BOLD + " \u2726")
                .setLore("",
                        Message.GOLD + "\u2503 " + Message.WHITE + "Vous permet d'avoir un effet",
                        Message.GOLD + "\u2503 " + Message.WHITE + "de nofall permanent.",
                        "",
                        Message.GOLD + Message.BOLD + "» " + Message.WHITE + "Accès: " + (player.hasPermission("atout.jump") ? Message.GREEN + "\u2714" : Message.RED + "\u2718" + Message.GRAY + " (/boutique)")
                        , Message.GOLD + Message.BOLD + "» " + Message.WHITE + "Activé: " + (playerData.isAtoutJumpBoost() ? Message.GREEN + "\u2714" : Message.RED + "\u2718"),
                        "",
                        Message.GREEN + "Cliquez pour " + (playerData.isAtoutJumpBoost() ? "désactiver" : "activer"))
                .hideEnchant()
                .hidePotionEffect()
                .hideAttributes()
                .toItemStack());
    }

    @Override
    public void onClick(final Player player, final Inventory inventory, final ItemStack itemStack, final int i) {
        final PlayerData playerData = this.corePlugin.getPlayer(player);

        if (i == 4) {
            player.closeInventory();
            player.chat("/site");
        } else if (i == 31) {
            player.closeInventory();

            if (player.hasPermission("atout.jump")) {
                if (playerData.isAtoutJumpBoost()) {
                    playerData.setAtoutJumpBoost(false);
                    Message.tell(player, Lang.ATOUT_OFF.getText().replace("%s", Lang.ATOUT_INVENTORY_JUMP.getText()));
                } else {
                    playerData.setAtoutJumpBoost(true);
                    playerData.checkAtout();
                    Message.tell(player, Lang.ATOUT_ON.getText().replace("%s", Lang.ATOUT_INVENTORY_JUMP.getText()));
                }
            } else {
                Message.tell(player, Lang.ATOUT_DONT_HAVE.getText());
            }
        } else if (i == 22) {
            player.closeInventory();

            if (player.hasPermission("atout.haste")) {
                if (playerData.isAtoutHaste()) {
                    playerData.setAtoutHaste(false);
                    player.removePotionEffect(PotionEffectType.FAST_DIGGING);
                    Message.tell(player, Lang.ATOUT_OFF.getText().replace("%s", Lang.ATOUT_INVENTORY_HASTE.getText()));
                } else {
                    playerData.setAtoutHaste(true);
                    playerData.checkAtout();
                    Message.tell(player, Lang.ATOUT_ON.getText().replace("%s", Lang.ATOUT_INVENTORY_HASTE.getText()));
                }
            } else {
                Message.tell(player, Lang.ATOUT_DONT_HAVE.getText());
            }
        } else if (i == 20) {
            player.closeInventory();

            if (player.hasPermission("atout.speed")) {
                if (playerData.isAtoutSpeed()) {
                    playerData.setAtoutSpeed(false);
                    player.removePotionEffect(PotionEffectType.SPEED);
                    Message.tell(player, Lang.ATOUT_OFF.getText().replace("%s", Lang.ATOUT_INVENTORY_SPEED.getText()));
                } else {
                    playerData.setAtoutSpeed(true);
                    playerData.checkAtout();
                    Message.tell(player, Lang.ATOUT_ON.getText().replace("%s", Lang.ATOUT_INVENTORY_SPEED.getText()));
                }
            } else {
                Message.tell(player, Lang.ATOUT_DONT_HAVE.getText());
            }
        } else if (i == 21) {
            player.closeInventory();

            if (player.hasPermission("atout.wb")) {
                if (playerData.isAtoutWater()) {
                    playerData.setAtoutWater(false);
                    player.removePotionEffect(PotionEffectType.WATER_BREATHING);
                    Message.tell(player, Lang.ATOUT_OFF.getText().replace("%s", Lang.ATOUT_INVENTORY_WB.getText()));
                } else {
                    playerData.setAtoutWater(true);
                    playerData.checkAtout();
                    Message.tell(player, Lang.ATOUT_ON.getText().replace("%s", Lang.ATOUT_INVENTORY_WB.getText()));
                }
            } else {
                Message.tell(player, Lang.ATOUT_DONT_HAVE.getText());
            }
        } else if (i == 23) {
            player.closeInventory();

            if (player.hasPermission("atout.str")) {
                if (playerData.isAtoutStrenght()) {
                    playerData.setAtoutStrenght(false);
                    player.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
                    Message.tell(player, Lang.ATOUT_OFF.getText().replace("%s", Lang.ATOUT_INVENTORY_STR.getText()));
                } else {
                    playerData.setAtoutStrenght(true);
                    playerData.checkAtout();
                    Message.tell(player, Lang.ATOUT_ON.getText().replace("%s", Lang.ATOUT_INVENTORY_STR.getText()));
                }
            } else {
                Message.tell(player, Lang.ATOUT_DONT_HAVE.getText());
            }
        } else if (i == 24) {
            player.closeInventory();

            if (player.hasPermission("atout.fire")) {
                if (playerData.isAtoutFireRes()) {
                    playerData.setAtoutFireRes(false);
                    player.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
                    Message.tell(player, Lang.ATOUT_OFF.getText().replace("%s", Lang.ATOUT_INVENTORY_FIRE.getText()));
                } else {
                    playerData.setAtoutFireRes(true);
                    playerData.checkAtout();
                    Message.tell(player, Lang.ATOUT_ON.getText().replace("%s", Lang.ATOUT_INVENTORY_FIRE.getText()));
                }
            } else {
                Message.tell(player, Lang.ATOUT_DONT_HAVE.getText());
            }
        }
    }

    @Override
    public void onClose(final Player player, final Inventory inventory) {

    }
}
