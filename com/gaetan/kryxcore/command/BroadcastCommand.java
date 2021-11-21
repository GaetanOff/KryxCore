package com.gaetan.kryxcore.command;

import com.gaetan.api.command.utils.annotation.Command;
import com.gaetan.api.command.utils.command.Context;
import com.gaetan.api.command.utils.target.CommandTarget;
import com.gaetan.api.message.Message;
import com.gaetan.kryxcore.CorePlugin;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public final class BroadcastCommand {
    /**
     * Reference to the main class
     */
    private final CorePlugin corePlugin;

    /**
     * Constructor for the BroadcastCommand class
     *
     * @param corePlugin Reference to the main class
     */
    public BroadcastCommand(final CorePlugin corePlugin) {
        this.corePlugin = corePlugin;
    }

    @Command(name = "broadcast", aliases = "bc", permission = "broadcast.use")
    public void handleCommand(final Context<ConsoleCommandSender> context) {
        this.corePlugin.getManagerHandler().getThreadManager().getThreadPool().execute(() -> {
            if (context.getArgs().length == 0)
                return;

            String player = this.corePlugin.getManagerHandler().getConfigManager().getBrServer();
            if (context.getTarget() == CommandTarget.PLAYER)
                player = ((Player) context.getSender()).getName();

            final StringBuilder message = new StringBuilder();

            message.append(context.getArgs()[0]);
            for (int i = 1; i < context.getArgs().length; i++) {
                message.append(" ");
                message.append(context.getArgs()[i]);
            }

            Message.tellToEveryone(Message.tl(this.corePlugin.getManagerHandler()
                    .getConfigManager().getBrMessage().replace("%message%", message).replace("%player%", player)));
        });
    }
}
