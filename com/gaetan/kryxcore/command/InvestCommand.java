package com.gaetan.kryxcore.command;

import com.gaetan.api.command.utils.annotation.Command;
import com.gaetan.api.command.utils.command.Context;
import com.gaetan.api.command.utils.target.CommandTarget;
import com.gaetan.api.message.Message;
import com.gaetan.kryxcore.CorePlugin;
import com.gaetan.kryxcore.data.PlayerData;
import com.gaetan.kryxcore.enums.Lang;
import com.gaetan.kryxcore.runnable.async.TeleportRunnable;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public final class InvestCommand {
    /**
     * Reference to the main class
     */
    private final CorePlugin corePlugin;

    /**
     * Constructor for the InvestCommand class
     *
     * @param corePlugin Reference to te main class
     */
    public InvestCommand(final CorePlugin corePlugin) {
        this.corePlugin = corePlugin;
    }

    /**
     * Command to join the server invest
     * Note: The player will have a cooldown before switching
     *
     * @param context The command argument
     */
    @Command(name = "invest", target = CommandTarget.PLAYER)
    public void handleCommand(final Context<ConsoleCommandSender> context) {
        this.corePlugin.getManagerHandler().getThreadManager().getThreadPool().execute(() -> {
            final PlayerData playerData = this.corePlugin.getPlayer((Player) context.getSender());

            if (!playerData.isInvest()) {
                playerData.setInvest(true);
                Message.tell(playerData.getPlayer(), Lang.INVEST_TELEPORTING.getText());
                new TeleportRunnable(5, playerData, this.corePlugin, "invest").runTaskTimerAsynchronously(this.corePlugin, 0L, 20L);
            }
        });
    }
}
