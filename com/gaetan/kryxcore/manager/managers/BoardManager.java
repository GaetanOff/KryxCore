package com.gaetan.kryxcore.manager.managers;

import com.gaetan.api.UnitUtil;
import com.gaetan.api.message.Message;
import com.gaetan.kryxcore.data.PlayerData;
import com.gaetan.kryxcore.manager.Manager;
import com.gaetan.kryxcore.manager.ManagerHandler;
import fr.minuskube.netherboard.bukkit.BPlayerBoard;
import me.clip.placeholderapi.PlaceholderAPI;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;

public final class BoardManager extends Manager {
    /**
     * Reference to the Vault Eco api
     */
    private final Economy economy;

    /**
     * Reference to the Vault chat api
     */
    public Chat chat;

    /**
     * Constructor for the BoardManager class.
     * Note: This will launch the runnable for the scoreboard updater
     *
     * @param handler Reference to the ManagerHandler
     */
    public BoardManager(final ManagerHandler handler) {
        super(handler);

        this.chat = this.handler.getCorePlugin().getServer().getServicesManager().getRegistration(Chat.class).getProvider();
        this.economy = this.handler.getCorePlugin().getServer().getServicesManager().getRegistration(Economy.class).getProvider();

        //Implementing Asynchronous
        //new BoardRunnable(this.handler.getCorePlugin()).runTaskTimerAsynchronously(this.handler.getCorePlugin(), 0L, this.handler.getConfigManager().getScoreboardUpdate());
    }

    /**
     * Setter to set the board.
     *
     * @param board     The reference to the board user
     * @param line      The line
     * @param boardSize The board size
     */
    public void setBoard(final BPlayerBoard board, final String line, final int boardSize) {
        final ConfigManager configManager = this.handler.getConfigManager();
        final PlayerData playerData = this.handler.getCorePlugin().getPlayer(board.getPlayer());

        board.set(Message.tl(line)
                        .replace("%player_name%", board.getPlayer().getName())
                        .replace("%faction%", PlaceholderAPI.setPlaceholders(board.getPlayer(), "%factionsuuid_faction_name%"))
                        .replace("%power%", PlaceholderAPI.setPlaceholders(board.getPlayer(), "%factionsuuid_faction_power%"))
                        .replace("%grade%", Message.tl(this.chat.getPlayerPrefix(board.getPlayer()).replace("[", "").replace("]", "")))
                        .replace("%money%", UnitUtil.moneyFormat(this.economy.getBalance(board.getPlayer())))
                        .replace("%vote_party%", configManager.getVotePartyNow() + "/" + configManager.getVotePartyMax())
                        .replace("%vote_party_needed%", String.valueOf(configManager.getVotePartyMax() - configManager.getVotePartyNow()))
                        .replace("%connected%", String.valueOf(this.handler.getCorePlugin().getServer().getOnlinePlayers().size()))
                        .replace("%player_ping%", String.valueOf(((CraftPlayer) playerData.getPlayer()).getHandle().ping))
                        .replace("%player_points%", PlaceholderAPI.setPlaceholders(board.getPlayer(), "%playerpoints_points%")),
                boardSize);
    }
}
