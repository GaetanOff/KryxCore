package com.gaetan.kryxcore.command;

import com.gaetan.api.command.utils.annotation.Command;
import com.gaetan.api.command.utils.command.Context;
import com.gaetan.api.command.utils.target.CommandTarget;
import com.gaetan.api.message.Message;
import com.gaetan.kryxcore.CorePlugin;
import org.bukkit.command.ConsoleCommandSender;

public final class ABCommand {
    /**
     * Reference to the main class
     */
    private final CorePlugin corePlugin;

    /**
     * Constructor for the ABCommand class
     *
     * @param corePlugin Reference to the main class
     */
    public ABCommand(final CorePlugin corePlugin) {
        this.corePlugin = corePlugin;
    }

    /**
     * Command to send an action bar
     * Note: This can only be used by a player with the "actionbar.use" permission
     *
     * @param context The command argument
     */
    @Command(name = "actionbar", permission = "actionbar.use", target = CommandTarget.ALL)
    public void handleCommand(final Context<ConsoleCommandSender> context) {
        this.corePlugin.getManagerHandler().getThreadManager().getThreadPool().execute(() -> {
            if (context.getArgs().length == 0)
                return;

            final StringBuilder message = new StringBuilder();

            message.append(context.getArgs()[0]);
            for (int i = 1; i < context.getArgs().length; i++) {
                message.append(" ");
                message.append(context.getArgs()[i]);
            }
            this.corePlugin.getManagerHandler().getActionBarUtil().sendActionBarToAllPlayers(Message.tl(message.toString()));
        });
    }
}
