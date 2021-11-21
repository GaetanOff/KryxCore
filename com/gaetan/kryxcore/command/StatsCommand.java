package com.gaetan.kryxcore.command;

import com.gaetan.api.StatsUtil;
import com.gaetan.api.command.utils.annotation.Command;
import com.gaetan.api.command.utils.command.Context;
import com.gaetan.api.command.utils.target.CommandTarget;
import com.gaetan.api.message.Message;
import com.gaetan.kryxcore.CorePlugin;
import com.gaetan.kryxcore.enums.Lang;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public final class StatsCommand {
    /**
     * Reference to the main class
     */
    private final CorePlugin corePlugin;

    /**
     * Constructor for the StatsCommand class
     *
     * @param corePlugin Reference to the main class
     */
    public StatsCommand(final CorePlugin corePlugin) {
        this.corePlugin = corePlugin;
    }

    /**
     * Command to show the stats of a player/yourself
     *
     * @param context command argument
     */
    @Command(name = "stats", aliases = {"elo", "stat", "statistic", "ks"}, target = CommandTarget.PLAYER)
    public void handleCommand(final Context<ConsoleCommandSender> context) {
        this.corePlugin.getManagerHandler().getThreadManager().getThreadPool().execute(() -> {
            final Player player = (Player) context.getSender();

            if (context.getArgs().length == 0) {
                this.statsMessage(player, player);
            } else if (context.getArgs().length == 1) {
                final Player target = player.getServer().getPlayer(context.getArgs()[0]);

                if (target == null) {
                    Message.tell(player, Lang.PLAYER_NULL.getText());
                    return;
                }

                this.statsMessage(player, target);
            }
        });
    }

    /**
     * Send the stats of a specific player.
     *
     * @param player player to send the stats
     * @param target target who stats requested
     */
    private void statsMessage(final Player player, final Player target) {
        Message.tell(player, new String[]{
                "",
                Lang.STATS_OF.getText() + target.getName(),
                "",
                Lang.KILLS.getText() + StatsUtil.getKills(target),
                Lang.DEATHS.getText() + StatsUtil.getDeaths(target),
                Lang.RATIO.getText() + StatsUtil.getRatio(target),
                ""
        });
    }
}
