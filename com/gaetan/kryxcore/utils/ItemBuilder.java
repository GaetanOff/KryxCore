//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.gaetan.kryxcore.utils;

import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Arrays;
import java.util.List;

public final class ItemBuilder {
    private ItemStack is;

    public ItemBuilder(final Material m) {
        this(m, 1);
    }

    public ItemBuilder(final ItemStack is) {
        this.is = is;
    }

    public ItemBuilder() {
        this.is = this.is;
    }

    public ItemBuilder(final Material m, final int amount) {
        this.is = new ItemStack(m, amount);
    }

    public ItemBuilder(final Material m, final int amount, final int meta) {
        this.is = new ItemStack(m, amount, (short) meta);
    }

    public ItemBuilder setDurability(final short dur) {
        this.is.setDurability(dur);
        return this;
    }

    public ItemBuilder setName(final String name) {
        final ItemMeta im = this.is.getItemMeta();
        im.setDisplayName(name);
        this.is.setItemMeta(im);
        return this;
    }

    public ItemBuilder addUnsafeEnchantment(final Enchantment ench, final int level) {
        this.is.addUnsafeEnchantment(ench, level);
        return this;
    }

    public ItemBuilder removeEnchantment(final Enchantment ench) {
        this.is.removeEnchantment(ench);
        return this;
    }

    public ItemBuilder setSkullOwner(final String owner) {
        try {
            final SkullMeta im = (SkullMeta) this.is.getItemMeta();
            im.setOwner(owner);
            this.is.setItemMeta(im);
        } catch (final ClassCastException var3) {
        }

        return this;
    }

    public ItemBuilder addEnchant(final Enchantment ench, final int level) {
        final ItemMeta im = this.is.getItemMeta();
        im.addEnchant(ench, level, true);
        this.is.setItemMeta(im);
        return this;
    }

    public ItemBuilder setAmount(final int amount) {
        this.is.setAmount(amount);
        return this;
    }

    public ItemBuilder setDurability(final int durability) {
        this.is.setDurability((short) durability);
        return this;
    }

    public ItemBuilder setInfinityDurability() {
        this.is.setDurability((short) 32767);
        return this;
    }

    public ItemBuilder setLore(final String... lore) {
        final ItemMeta im = this.is.getItemMeta();
        im.setLore(Arrays.asList(lore));
        this.is.setItemMeta(im);
        return this;
    }

    public ItemBuilder removeFlags() {
        return this.removeFlags(ItemFlag.values());
    }

    public ItemBuilder removeFlags(final ItemFlag... flags) {
        final ItemMeta im = this.is.getItemMeta();
        im.addItemFlags(flags);
        this.is.setItemMeta(im);
        return this;
    }

    public ItemBuilder setUnbreakable() {
        final ItemMeta im = this.is.getItemMeta();
        im.spigot().setUnbreakable(true);
        this.is.setItemMeta(im);
        return this;
    }

    public ItemBuilder addEnchantment(final Enchantment enchantment, final int value, final boolean ignoreLevelRestriction) {
        final ItemMeta itemMeta = this.is.getItemMeta();
        itemMeta.addEnchant(enchantment, value, ignoreLevelRestriction);
        this.is.setItemMeta(itemMeta);
        return this;
    }

    public ItemBuilder hideEnchant() {
        final ItemMeta itemMeta = this.is.getItemMeta();
        itemMeta.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_ENCHANTS});
        this.is.setItemMeta(itemMeta);
        return this;
    }

    public ItemBuilder hidePotionEffect() {
        final ItemMeta itemMeta = this.is.getItemMeta();
        itemMeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        this.is.setItemMeta(itemMeta);
        return this;
    }

    public ItemBuilder hideAttributes() {
        final ItemMeta itemMeta = this.is.getItemMeta();
        itemMeta.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_ATTRIBUTES});
        this.is.setItemMeta(itemMeta);
        return this;
    }

    public ItemBuilder setLore(final List<String> lore) {
        final ItemMeta im = this.is.getItemMeta();
        im.setLore(lore);
        this.is.setItemMeta(im);
        return this;
    }

    public ItemBuilder setWoolColor(final DyeColor color) {
        if (!this.is.getType().equals(Material.WOOL)) {
            return this;
        } else {
            this.is.setDurability((short) color.getWoolData());
            return this;
        }
    }

    public ItemBuilder setLeatherArmorColor(final Color color) {
        try {
            final LeatherArmorMeta im = (LeatherArmorMeta) this.is.getItemMeta();
            im.setColor(color);
            this.is.setItemMeta(im);
        } catch (final ClassCastException var3) {
        }

        return this;
    }

    public ItemStack toItemStack() {
        return this.is;
    }
}
