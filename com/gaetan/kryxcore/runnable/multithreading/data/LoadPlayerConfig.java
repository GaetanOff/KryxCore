package com.gaetan.kryxcore.runnable.multithreading.data;

import com.gaetan.api.ConfigUtil;
import com.gaetan.api.FastUUID;
import com.gaetan.kryxcore.CorePlugin;
import com.gaetan.kryxcore.data.PlayerData;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.List;

public final class LoadPlayerConfig implements Runnable {
    /**
     * Reference to the main class
     */
    private final CorePlugin corePlugin;

    /**
     * Reference to the PlayerData
     */
    private final PlayerData playerData;

    /**
     * Constructor for the LoadPlayerConfig runnable
     *
     * @param corePlugin Reference to the main class
     */
    public LoadPlayerConfig(final CorePlugin corePlugin, final PlayerData playerData) {
        this.corePlugin = corePlugin;
        this.playerData = playerData;
    }

    @Override
    public void run() {
        final String uuid = FastUUID.toString(this.playerData.getPlayer().getUniqueId());

        if (new File(this.corePlugin.getDataFolder() + "/atouts", uuid + ".yml").exists()) {
            final ConfigUtil config = new ConfigUtil(this.corePlugin, "/atouts", uuid);

            this.playerData.setAtoutHaste(config.getConfig().getBoolean("atout.haste"));
            this.playerData.setAtoutJumpBoost(config.getConfig().getBoolean("atout.jump"));
            this.playerData.setAtoutSpeed(config.getConfig().getBoolean("atout.speed"));
            this.playerData.setAtoutWater(config.getConfig().getBoolean("atout.water"));
            this.playerData.setAtoutStrenght(config.getConfig().getBoolean("atout.str"));
            this.playerData.setAtoutFireRes(config.getConfig().getBoolean("atout.fire"));
            this.playerData.checkAtout();

            config.delete();
        }

        if (new File(this.corePlugin.getDataFolder() + "/bagpack", uuid + ".yml").exists()) {
            final ConfigUtil config = new ConfigUtil(this.corePlugin, "/bagpack", uuid);

            this.playerData.getBagPack().clear();
            this.playerData.getBagPack().setContents(((List<ItemStack>) config.getConfig().get("bagpack")).toArray(new ItemStack[0]));
        }
    }
}
