package com.gaetan.kryxcore.command;

import com.gaetan.api.command.utils.annotation.Command;
import com.gaetan.api.command.utils.command.Context;
import com.gaetan.api.command.utils.target.CommandTarget;
import com.gaetan.api.message.Message;
import com.gaetan.api.runnable.TaskUtil;
import com.gaetan.kryxcore.CorePlugin;
import com.gaetan.kryxcore.enums.Lang;
import com.gaetan.kryxcore.inventory.AtoutInventory;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Stream;

public final class AtoutCommand {
    /**
     * Reference to the main class
     */
    private final CorePlugin corePlugin;

    /**
     * Constructor for the AtoutCommand class
     *
     * @param corePlugin Reference to the main class
     */
    public AtoutCommand(final CorePlugin corePlugin) {
        this.corePlugin = corePlugin;
    }

    /**
     * Command to open the atout gui
     *
     * @param context The command argument
     */
    @Command(name = "atout", aliases = "atouts", target = CommandTarget.PLAYER)
    public void handleCommand(final Context<ConsoleCommandSender> context) {
        this.corePlugin.getManagerHandler().getThreadManager().getThreadPool().execute(() -> {
            final Player player = (Player) context.getSender();
            final AtomicBoolean hasPerms = new AtomicBoolean(false);

            Stream.of("atout.jump", "atout.haste", "atout.speed", "atout.wb", "atout.str", "atout.fire")
                    .filter(player::hasPermission)
                    .forEach(perms -> hasPerms.set(true));

            if (hasPerms.get())
                //Sync cuz we are not on the main thread before !
                TaskUtil.run(() -> this.corePlugin.getManagerHandler().getGuiManager().open(player, AtoutInventory.class));
            else Message.tell(player, Lang.ATOUT_DONT_HAVE_ANY.getText());
        });
    }
}
