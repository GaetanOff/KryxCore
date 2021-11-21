package com.gaetan.kryxcore.command;

import com.gaetan.api.command.utils.annotation.Command;
import com.gaetan.api.command.utils.command.Context;
import com.gaetan.api.command.utils.target.CommandTarget;
import com.gaetan.kryxcore.CorePlugin;
import com.gaetan.kryxcore.data.PlayerData;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public final class BagPackCommand {
    /**
     * Reference to the main class
     */
    private final CorePlugin corePlugin;

    /**
     * Constructor for the BagPackCommand class
     *
     * @param corePlugin Reference to the main class
     */
    public BagPackCommand(final CorePlugin corePlugin) {
        this.corePlugin = corePlugin;
    }

    /**
     * Command to open an bag pack
     * Note: This can only be used by a player with the "bagpack.use" permission
     *
     * @param context The command argument
     */
    @Command(name = "bagpack", aliases = "sac", permission = "bagpack.use", target = CommandTarget.PLAYER)
    public void handleCommand(final Context<ConsoleCommandSender> context) {
        final PlayerData playerData = corePlugin.getPlayer((Player) context.getSender());
        
        playerData.getPlayer().openInventory(playerData.getBagPack());
    }
}
