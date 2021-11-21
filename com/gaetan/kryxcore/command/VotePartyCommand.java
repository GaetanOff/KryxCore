package com.gaetan.kryxcore.command;

import com.gaetan.api.command.utils.annotation.Command;
import com.gaetan.api.command.utils.command.Context;
import com.gaetan.api.message.Message;
import com.gaetan.kryxcore.CorePlugin;
import org.bukkit.command.ConsoleCommandSender;

public final class VotePartyCommand {
    /**
     * Reference to the main class
     */
    private final CorePlugin corePlugin;

    /**
     * Constructor for the VotePartyCommand class.
     *
     * @param corePlugin Reference to te main class
     */
    public VotePartyCommand(final CorePlugin corePlugin) {
        this.corePlugin = corePlugin;
    }

    /**
     * Command to show the vote party commands.
     * Note: This can only be used by a sender with voteparty.use permission
     *
     * @param context The command argument
     */
    @Command(name = "voteparty", permission = "voteparty.use")
    public void handleCommand(final Context<ConsoleCommandSender> context) {
        this.corePlugin.getManagerHandler().getThreadManager().getThreadPool().execute(() ->
                context.sendMessage(new String[]{
                        "",
                        Message.GOLD + Message.BOLD + "VoteParty" + Message.GRAY + Message.ITALIC + " (Gaetan#0099)",
                        "",
                        Message.YELLOW + "/voteparty" + Message.GRAY + " - Voir les commandes.",
                        Message.YELLOW + "/voteparty max <nombre>" + Message.GRAY + " - Changer le maximum.",
                        Message.YELLOW + "/voteparty add <nombre>" + Message.GRAY + " - Ajouter un nombre au VP.",
                        Message.YELLOW + "/voteparty remove <nombre>" + Message.GRAY + " - Enlever un nombre au VP.",
                        ""
                }));
    }

    /**
     * Command to set the max of the vote party.
     * Note: This can only be used by a sender with voteparty.max permission
     *
     * @param context The command argument
     */
    @Command(name = "voteparty.max", permission = "voteparty.max")
    public void handleCommand_Max(final Context<ConsoleCommandSender> context, final int max) {
        this.corePlugin.getManagerHandler().getVotePartyManager().setMax(context, max);
    }

    /**
     * Command to add a number in the vote party.
     * Note: This can only be used by a sender with voteparty.add permission
     *
     * @param context The command argument
     */
    @Command(name = "voteparty.add", permission = "voteparty.add")
    public void handleCommand_Add(final Context<ConsoleCommandSender> context, final int numberToAdd) {
        this.corePlugin.getManagerHandler().getVotePartyManager().addVote(context, numberToAdd);
    }

    /**
     * Command to remove a number in the vote party
     * Note: This can only be used by a sender with voteparty.remove permission
     *
     * @param context The command argument
     */
    @Command(name = "voteparty.remove", permission = "voteparty.remove")
    public void handleCommand_Remove(final Context<ConsoleCommandSender> context, final int numberToRemove) {
        this.corePlugin.getManagerHandler().getVotePartyManager().removeVote(context, numberToRemove);
    }
}
