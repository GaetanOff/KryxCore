package com.gaetan.kryxcore.command;

import com.gaetan.api.command.utils.annotation.Command;
import com.gaetan.api.command.utils.command.Context;
import com.gaetan.api.command.utils.target.CommandTarget;
import com.gaetan.api.message.Message;
import com.gaetan.api.runnable.TaskUtil;
import com.gaetan.kryxcore.CorePlugin;
import com.gaetan.kryxcore.data.PlayerData;
import com.gaetan.kryxcore.enums.Lang;
import com.gaetan.kryxcore.runnable.async.TeleportRunnable;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public final class SpawnCommand {
    /**
     * Reference to the main class
     */
    private final CorePlugin corePlugin;

    /**
     * Constructor for the SpawnCommand class
     *
     * @param corePlugin Reference to te main class
     */
    public SpawnCommand(final CorePlugin corePlugin) {
        this.corePlugin = corePlugin;
    }

    /**
     * Command to join the spawn
     * Note: The player will have a cooldown before switching if he dosn't have perm to bypass
     *
     * @param context The command argument
     */
    @Command(name = "spawn", target = CommandTarget.PLAYER)
    public void handleCommand(final Context<ConsoleCommandSender> context) {
        this.corePlugin.getManagerHandler().getThreadManager().getThreadPool().execute(() -> {
            final PlayerData playerData = this.corePlugin.getPlayer((Player) context.getSender());

            if (playerData.getPlayer().hasPermission("kryxcore.bypass")) {
                Message.tell(playerData.getPlayer(), Lang.SPAWN_TELEPORTING.getText());

                //Sync cuz we are not on the main thread before !
                TaskUtil.run(() -> playerData.getPlayer().teleport(this.corePlugin.getManagerHandler().getConfigManager().getSpawn()));
            } else {
                if (!playerData.isInvest()) {
                    playerData.setInvest(true);
                    new TeleportRunnable(5, playerData, this.corePlugin, "spawn").runTaskTimerAsynchronously(this.corePlugin, 0L, 20L);
                }
            }
        });
    }

    /**
     * Set the spawn.
     *
     * @param context command argument
     */
    @Command(name = "setspawn", permission = "kryxcore.setspawn", target = CommandTarget.PLAYER)
    public void handleCommand_SetSpawn(final Context<ConsoleCommandSender> context) {
        final Player player = (Player) context.getSender();

        this.corePlugin.getManagerHandler().getConfigManager().setSpawn(player.getLocation());
        Message.tell(player, Lang.SPAWN_SET.getText());
    }
}
