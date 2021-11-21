package com.gaetan.kryxcore.command;

import com.gaetan.api.command.utils.annotation.Command;
import com.gaetan.api.command.utils.command.Context;
import com.gaetan.api.command.utils.target.CommandTarget;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;

public final class AnvilCommand {
    /**
     * Command to open an inventory of anvil
     * Note: This can only be used by a player with the "anvil.use" permission
     *
     * @param context The command argument
     */
    @Command(name = "anvil", permission = "anvil.use", target = CommandTarget.PLAYER)
    public void handleCommand(final Context<ConsoleCommandSender> context) {
        ((Player) context.getSender())
                .openInventory(Bukkit.createInventory(null, InventoryType.ANVIL));
    }
}
