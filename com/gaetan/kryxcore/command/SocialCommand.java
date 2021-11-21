package com.gaetan.kryxcore.command;

import com.gaetan.api.command.utils.annotation.Command;
import com.gaetan.api.command.utils.command.Context;
import com.gaetan.api.message.Message;
import com.gaetan.kryxcore.CorePlugin;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public final class SocialCommand {
    /**
     * Reference to the main class
     */
    private final CorePlugin corePlugin;

    /**
     * Constructor for the SocialCommand class
     *
     * @param corePlugin Reference to te main class
     */
    public SocialCommand(final CorePlugin corePlugin) {
        this.corePlugin = corePlugin;
    }

    /**
     * Command to show the vote message
     *
     * @param context The command argument
     */
    @Command(name = "vote")
    public void handleCommand_Vote(final Context<ConsoleCommandSender> context) {
        this.corePlugin.getManagerHandler().getThreadManager().getThreadPool().execute(() ->
                this.corePlugin.getManagerHandler().getConfigManager().getVoteMessage()
                        .forEach(string -> Message.tell((Player) context.getSender(), Message.tl(string)))
        );
    }

    /**
     * Command to show the site message
     *
     * @param context The command argument
     */
    @Command(name = "site")
    public void handleCommand_Site(final Context<ConsoleCommandSender> context) {
        this.corePlugin.getManagerHandler().getThreadManager().getThreadPool().execute(() ->
                this.corePlugin.getManagerHandler().getConfigManager().getSiteMessage()
                        .forEach(string -> Message.tell((Player) context.getSender(), Message.tl(string)))
        );
    }

    /**
     * Command to show the discord message
     *
     * @param context The command argument
     */
    @Command(name = "discord")
    public void handleCommand_Discord(final Context<ConsoleCommandSender> context) {
        this.corePlugin.getManagerHandler().getThreadManager().getThreadPool().execute(() ->
                this.corePlugin.getManagerHandler().getConfigManager().getDiscordMessage()
                        .forEach(string -> Message.tell((Player) context.getSender(), Message.tl(string)))
        );
    }
}
