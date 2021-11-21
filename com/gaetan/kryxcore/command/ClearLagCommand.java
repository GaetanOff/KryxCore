package com.gaetan.kryxcore.command;

import com.gaetan.api.command.utils.annotation.Command;
import com.gaetan.api.command.utils.command.Context;
import com.gaetan.api.command.utils.target.CommandTarget;
import com.gaetan.kryxcore.CorePlugin;
import org.bukkit.command.ConsoleCommandSender;

public final class ClearLagCommand {
    /**
     * Reference to the main class
     */
    private final CorePlugin corePlugin;

    /**
     * Constructor for the ClearLagCommand class
     *
     * @param corePlugin Reference to te main class
     */
    public ClearLagCommand(final CorePlugin corePlugin) {
        this.corePlugin = corePlugin;
    }

    /**
     * Command to toggle the clear-lag
     * Note: This can only be used by a player with the "clearlag.use" permission
     *
     * @param context The command argument
     */
    @Command(name = "clearlag", permission = "clearlag.use", target = CommandTarget.ALL)
    public void handleCommand(final Context<ConsoleCommandSender> context) {
        this.corePlugin.getManagerHandler().getClearLagManager().clearLagg();
    }
}
