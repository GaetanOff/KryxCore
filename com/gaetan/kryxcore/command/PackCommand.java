package com.gaetan.kryxcore.command;

import com.gaetan.api.command.utils.annotation.Command;
import com.gaetan.api.command.utils.command.Context;
import com.gaetan.api.command.utils.target.CommandTarget;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public final class PackCommand {

    /**
     * Pack starter
     * Note: This code is not from Gaetan so don't trash if you steal my code hehe
     *
     * @param context command argument
     */
    @Command(name = "starter", permission = "starterpack.use", target = CommandTarget.PLAYER)
    public void handleCommand_Starter(final Context<ConsoleCommandSender> context, final Player target) {
        final Player player = (Player) context.getSender();

        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "spawners add " + target.getName() + " Skeleton 1");
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "spawners add " + target.getName() + " zombie 1");
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "cc give p Rare 2 " + target.getName());
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "cc give p Epique 1 " + target.getName());
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "kit Starter " + target.getName());
        player.sendMessage("§eVous avez donné le starter pack au joueur §d§l" + target.getName() + "§e.");
        target.sendMessage("§eVous avez reçu le starter pack.");
    }
}
