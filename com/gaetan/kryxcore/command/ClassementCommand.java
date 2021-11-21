package com.gaetan.kryxcore.command;

import com.gaetan.api.command.utils.annotation.Command;
import com.gaetan.api.command.utils.command.Context;
import com.gaetan.api.command.utils.target.CommandTarget;
import com.gaetan.api.message.Message;
import com.gaetan.kryxcore.CorePlugin;
import com.gaetan.kryxcore.inventory.ClassementInventory;
import com.gaetan.kryxcore.runnable.multithreading.ClassementRunnable;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public final class ClassementCommand {
    /**
     * Reference to the main class
     */
    private final CorePlugin corePlugin;

    /**
     * Constructor for the ClassementCommand class
     *
     * @param corePlugin Reference to te main class
     */
    public ClassementCommand(final CorePlugin corePlugin) {
        this.corePlugin = corePlugin;
    }

    /**
     * Command to open the classement gui
     *
     * @param context The command argument
     */
    @Command(name = "pvp", aliases = "farm", target = CommandTarget.PLAYER)
    public void handleCommand(final Context<ConsoleCommandSender> context) {
        this.corePlugin.getManagerHandler().getGuiManager().open((Player) context.getSender(), ClassementInventory.class);
    }

    /**
     * Command to add point
     *
     * @param context The command argument
     */
    @Command(name = "kclassement", permission = "kclassement.help", target = CommandTarget.ALL)
    public void handleCommand_Help(final Context<ConsoleCommandSender> context) {
        this.corePlugin.getManagerHandler().getThreadManager().getThreadPool().execute(() ->
                context.sendMessage(new String[]{
                        "",
                        Message.GOLD + Message.BOLD + "KClassement" + Message.GRAY + Message.ITALIC + " (Gaetan#0099)",
                        "",
                        Message.YELLOW + "/kclassement" + Message.GRAY + " - Voir les commandes.",
                        Message.YELLOW + "/kclassement addpoint <faction> <farm/pvp> <points>" + Message.GRAY + " - Ajouter des points.",
                        Message.YELLOW + "/kclassement removepoint <faction> <farm/pvp> <points>" + Message.GRAY + " - Enlever des points.",
                        Message.YELLOW + "/kclassement clear <faction> <farm/pvp>" + Message.GRAY + " - Restaurer une faction.",
                        Message.YELLOW + "/kclassement update" + Message.GRAY + " - Mettre à jour le classement.",
                        ""
                }));
    }

    /**
     * Command to add point
     *
     * @param context The command argument
     */
    @Command(name = "kclassement.addpoint", permission = "kclassement.addpoint", target = CommandTarget.ALL)
    public void handleCommand_AddPoint(final Context<ConsoleCommandSender> context, final String faction, final String type, final int number) {
        this.corePlugin.getManagerHandler().getThreadManager().getThreadPool().execute(() -> {
            if (type.equals("farm")) {
                this.corePlugin.getManagerHandler().getClassementManager().addFarmPoint(faction, number);
                context.sendMessage("Ajout de " + number + " à " + faction + " en " + type);
            } else if (type.equals("pvp")) {
                this.corePlugin.getManagerHandler().getClassementManager().addPvpPoint(faction, number);
                context.sendMessage("Ajout de " + number + " à " + faction + " en " + type);
            } else {
                context.sendMessage("Type est inconnu !");
            }
        });
    }

    /**
     * Command to remove point
     *
     * @param context The command argument
     */
    @Command(name = "kclassement.removepoint", permission = "kclassement.removepoint", target = CommandTarget.ALL)
    public void handleCommand_RemovePoint(final Context<ConsoleCommandSender> context, final String faction, final String type, final int number) {
        this.corePlugin.getManagerHandler().getThreadManager().getThreadPool().execute(() -> {
            if (type.equals("farm")) {
                this.corePlugin.getManagerHandler().getClassementManager().removeFarmPoint(faction, number);
                context.sendMessage("Suppresion de " + number + " à " + faction + " en " + type);
            } else if (type.equals("pvp")) {
                this.corePlugin.getManagerHandler().getClassementManager().removePvPPoint(faction, number);
                context.sendMessage("Suppresion de " + number + " à " + faction + " en " + type);
            } else {
                context.sendMessage("Type est inconnu !");
            }
        });
    }


    /**
     * Command to clear a faction
     *
     * @param context The command argument
     */
    @Command(name = "kclassement.clear", permission = "kclassement.clear", target = CommandTarget.ALL)
    public void handleCommand_RemovePoint(final Context<ConsoleCommandSender> context, final String faction, final String type) {
        this.corePlugin.getManagerHandler().getThreadManager().getThreadPool().execute(() -> {
            if (type.equals("farm")) {
                this.corePlugin.getManagerHandler().getClassementManager().resetFarmFac(faction);
                context.sendMessage(faction + " a été reset en " + type + ".");
            } else if (type.equals("pvp")) {
                this.corePlugin.getManagerHandler().getClassementManager().resetPvPFac(faction);
                context.sendMessage(faction + " a été reset en " + type + ".");
            } else {
                context.sendMessage("Type est inconnu !");
            }
        });
    }

    /**
     * Command to update the classement
     *
     * @param context The command argument
     */
    @Command(name = "kclassement.update", permission = "kclassement.update", target = CommandTarget.ALL)
    public void handleCommand_Update(final Context<ConsoleCommandSender> context) {
        this.corePlugin.getManagerHandler().getThreadManager().getThreadPool().execute(() -> {
            new ClassementRunnable(this.corePlugin).run();
            context.sendMessage("Classement mis à jour !");
        });
    }
}
