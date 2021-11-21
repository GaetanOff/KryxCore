package com.gaetan.kryxcore.command;

import com.gaetan.api.command.utils.annotation.Command;
import com.gaetan.api.command.utils.command.Context;
import com.gaetan.api.command.utils.target.CommandTarget;
import com.gaetan.kryxcore.CorePlugin;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public final class StaffCommand {
    /**
     * Reference to the main class
     */
    private final CorePlugin corePlugin;

    /**
     * Constructor for the StaffCommand class.
     *
     * @param corePlugin Reference to te main class
     */
    public StaffCommand(final CorePlugin corePlugin) {
        this.corePlugin = corePlugin;
    }

    /**
     * Command to enable/disable the staffmode.
     * Note: This can only be used by a player with mod.use permission
     *
     * @param context The command argument
     */
    @Command(name = "mod", permission = "mod.use", target = CommandTarget.PLAYER)
    public void handleCommand_Mod(final Context<ConsoleCommandSender> context) {
        this.corePlugin.getManagerHandler().getStaffManager().switchMod((Player) context.getSender());
    }

    /**
     * Command to freeze someone.
     * Note: This can only be used by a player with freeze.use permission
     *
     * @param context The command argument
     */
    @Command(name = "freeze", permission = "freeze.use", target = CommandTarget.PLAYER)
    public void handleCommand_Freeze(final Context<ConsoleCommandSender> context, final Player target) {
        this.corePlugin.getManagerHandler().getStaffManager().switchFreeze((Player) context.getSender(), target);
    }

    /**
     * Command to open the stafflist connected gui.
     * Note: This can only be used by a player with staff.use permission
     *
     * @param context The command argument
     */
    @Command(name = "stafflist", permission = "staff.use", target = CommandTarget.PLAYER)
    public void handleCommand_StaffList(final Context<ConsoleCommandSender> context) {
        this.corePlugin.getManagerHandler().getStaffManager().openSLInventory((Player) context.getSender());
    }
}
