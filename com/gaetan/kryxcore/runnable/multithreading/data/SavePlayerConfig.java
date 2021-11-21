package com.gaetan.kryxcore.runnable.multithreading.data;

import com.gaetan.api.ConfigUtil;
import com.gaetan.api.FastUUID;
import com.gaetan.kryxcore.CorePlugin;
import com.gaetan.kryxcore.data.PlayerData;

public final class SavePlayerConfig implements Runnable {
    /**
     * Reference to the main class
     */
    private final CorePlugin corePlugin;

    /**
     * Reference to the PlayerData
     */
    private final PlayerData playerData;

    /**
     * Constructor for the SavePlayerConfig runnable
     *
     * @param corePlugin Reference to the main class
     * @param playerData Reference to the PlayerData
     */
    public SavePlayerConfig(final CorePlugin corePlugin, final PlayerData playerData) {
        this.corePlugin = corePlugin;
        this.playerData = playerData;
    }

    @Override
    public void run() {
        final String uuid = FastUUID.toString(this.playerData.getPlayer().getUniqueId());

        if (this.playerData.isAtoutHaste() ||
                this.playerData.isAtoutJumpBoost() ||
                this.playerData.isAtoutSpeed() ||
                this.playerData.isAtoutWater() ||
                this.playerData.isAtoutStrenght() ||
                this.playerData.isAtoutFireRes()) {
            final ConfigUtil config = new ConfigUtil(this.corePlugin, "/atouts", uuid);

            config.getConfig().set("atout.haste", this.playerData.isAtoutHaste());
            config.getConfig().set("atout.jump", this.playerData.isAtoutJumpBoost());
            config.getConfig().set("atout.speed", this.playerData.isAtoutSpeed());
            config.getConfig().set("atout.water", this.playerData.isAtoutWater());
            config.getConfig().set("atout.str", this.playerData.isAtoutStrenght());
            config.getConfig().set("atout.fire", this.playerData.isAtoutFireRes());
            config.save();
        }

        final ConfigUtil config = new ConfigUtil(this.corePlugin, "/bagpack", uuid);

        config.getConfig().set("bagpack", this.playerData.getBagPack().getContents());
        config.save();
    }
}
