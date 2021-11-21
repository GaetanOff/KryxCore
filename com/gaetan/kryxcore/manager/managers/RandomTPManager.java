package com.gaetan.kryxcore.manager.managers;

import com.gaetan.api.RandomUtil;
import com.gaetan.kryxcore.manager.Manager;
import com.gaetan.kryxcore.manager.ManagerHandler;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

import java.util.function.Predicate;

public final class RandomTPManager extends Manager {
    /**
     * Constructor for the RandomTPManager class.
     *
     * @param handler Reference to the ManagerHandler
     */
    public RandomTPManager(final ManagerHandler handler) {
        super(handler);
    }

    /**
     * Method to find a safe location
     *
     * @param center The center of the map
     * @param range  The range to teleport
     */
    public Location findLocation(final Location center, final int range) {
        for (int i = 0; i < 50; ++i) {
            final Location location = RandomUtil.nextLocation(center, range, false);
            final int highestPointY = this.findHighestBlock(location, material -> !this.isLeaves(material));
            if (highestPointY != -1) {
                location.setY(highestPointY);
                final Block block = location.getBlock();
                if (block.getRelative(BlockFace.DOWN).getType().isSolid()
                        && this.isAir(block)
                        && this.isAir(block.getRelative(BlockFace.UP)))
                    return location;

            }
        }
        return null;
    }

    /**
     * Method to find the highest block
     *
     * @param location  The location
     * @param predicate The material
     */
    private int findHighestBlock(final Location location, final Predicate<Material> predicate) {
        return this.findHighestBlock(location.getWorld(), location.getBlockX(), location.getBlockZ(), predicate);
    }

    /**
     * Method to find the highest block
     *
     * @param world     The world
     * @param x         The material
     * @param z
     * @param predicate The material
     */
    private int findHighestBlock(final World world, final int x, final int z, final Predicate<Material> predicate) {
        for (int y = world.getMaxHeight(); y > 0; --y) {
            final Block block = world.getBlockAt(x, y, z);
            if (block == null || this.isAir(block) || !predicate.test(block.getType()))
                continue;

            return y + 1;
        }
        return -1;
    }

    /**
     * Getter to get if the <block> is in air
     *
     * @param block The <block>
     */
    private boolean isAir(final Block block) {
        return block == null || this.isAir(block.getType());
    }

    /**
     * Getter to get if the <material> is in air
     *
     * @param material The <material>
     */
    private boolean isAir(final Material material) {
        return material == null || this.nameEquals(material, "AIR", "CAVE_AIR", "VOID_AIR");
    }

    /**
     * Getter to get if the <material> is leaves
     *
     * @param material The <material>
     */
    private boolean isLeaves(final Material material) {
        return material.toString().endsWith("_LEAVES") || this.nameEquals(material, "LEAVES", "LEAVES_2");
    }

    /**
     * Getter to get if the name is equals
     *
     * @param mat   The material
     * @param names The names
     */
    private boolean nameEquals(final Material mat, final String... names) {
        final String matName = mat.toString();
        for (final String name : names) {
            if (matName.equals(name)) return true;
        }
        return false;
    }
}
