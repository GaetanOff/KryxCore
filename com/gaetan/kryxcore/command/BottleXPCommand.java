package com.gaetan.kryxcore.command;

import com.gaetan.api.command.utils.annotation.Command;
import com.gaetan.api.command.utils.command.Context;
import com.gaetan.api.command.utils.target.CommandTarget;
import com.gaetan.kryxcore.CorePlugin;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public final class BottleXPCommand {
    /**
     * Reference to the main class
     */
    private final CorePlugin corePlugin;

    /**
     * Constructor for the BottleXPCommand class
     *
     * @param corePlugin Reference to te main class
     */
    public BottleXPCommand(final CorePlugin corePlugin) {
        this.corePlugin = corePlugin;
    }

    /**
     * Command to give a bottlexp with your level
     *
     * @param context The command argument
     */
    @Command(name = "bottlexp", target = CommandTarget.PLAYER)
    public void handleCommand(final Context<ConsoleCommandSender> context) {
        this.corePlugin.getManagerHandler().getThreadManager().getThreadPool().execute(() ->
                this.corePlugin.getManagerHandler().getBottleXPManager().giveBottle((Player) context.getSender())
        );
    }
}
