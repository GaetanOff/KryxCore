package com.gaetan.kryxcore;

import com.gaetan.api.annotation.GaetanApplication;
import com.gaetan.api.plugin.SimplePlugin;
import com.gaetan.kryxcore.command.*;
import com.gaetan.kryxcore.data.PlayerData;
import com.gaetan.kryxcore.listener.*;
import com.gaetan.kryxcore.listener.cooldown.Enderpearl;
import com.gaetan.kryxcore.listener.cooldown.GoldenCheat;
import com.gaetan.kryxcore.listener.cooldown.GoldenNormal;
import com.gaetan.kryxcore.manager.ManagerHandler;
import com.google.common.collect.Maps;
import org.bukkit.entity.Player;

import java.util.Map;

@GaetanApplication(name = "KryxCore", authors = "GaetanOff", version = "0.3", main = "com.gaetan.kryxcore.CorePlugin", depend = {"Vault", "PlaceholderAPI", "PlayerPoints"})
public final class CorePlugin extends SimplePlugin {
    /**
     * Map to stock the PayerData
     */
    private final Map<Player, PlayerData> players = Maps.newConcurrentMap();

    /**
     * Reference to the ManagerHandler
     */
    private ManagerHandler managerHandler;

    /**
     * Method to launch the plugin
     * Note: This is the same as the classic onEnable
     */
    @Override
    protected void onPluginStart() {
        this.saveDefaultConfig();

        this.managerHandler = new ManagerHandler(this);

        this.registerCommands(
                new StaffCommand(this),
                new BottleXPCommand(this),
                new SocialCommand(this),
                new VotePartyCommand(this),
                new RandomTPCommand(this),
                new InvestCommand(this),
                new ClearLagCommand(this),
                new StatsCommand(this),
                new ABCommand(this),
                new VisionCommand(this),
                new AtoutCommand(this),
                new SpawnCommand(this),
                new ClassementCommand(this),
                new BagPackCommand(this),
                new BroadcastCommand(this),
                new AnvilCommand()
        );

        this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
    }

    /**
     * Method to stop the plugin
     * Note: This is the same as the classic onDisable
     */
    @Override
    protected void onPluginStop() {
        this.players.values().forEach(PlayerData::leave);
        this.managerHandler.unload();
    }

    /**
     * Method to register listener
     * Note: This will be trigger after the loading of the server
     */
    @Override
    protected void registerListener() {
        new PlayerListener(this);
        new PortalListener(this);
        new BlockListener(this);
        new WorldListener(this);
        new EntityListener(this);
        new CancelListener(this);
        new EventListener(this);

        /**
         * Register cooldown
         */
        new GoldenNormal(this);
        new GoldenCheat(this);
        new Enderpearl(this);
    }

    /**
     * This is trigger when the server finished loading
     */
    @Override
    protected void onPluginLoaded() {
        this.getServer().getOnlinePlayers().forEach(player -> {
            final PlayerData playerData = new PlayerData(player, this);

            this.getPlayers().put(player, playerData);
            playerData.loadScoreboard();
        });
    }


    /**
     * Getter to get the PlayerData of a specific player with his Player object.
     *
     * @param player The choosen Player object
     * @return The PlayerData of the choosen Player object
     */
    public PlayerData getPlayer(final Player player) {
        return this.players.get(player);
    }

    /**
     * Getter to get the Map of all PlayerData.
     *
     * @return The map containing all the Player object and PlayerData
     */
    public Map<Player, PlayerData> getPlayers() {
        return this.players;
    }

    /**
     * Getter to get the ManagerHandler reference.
     *
     * @return The reference ManagerHandler
     */
    public ManagerHandler getManagerHandler() {
        return this.managerHandler;
    }
}
