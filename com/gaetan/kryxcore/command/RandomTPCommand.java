package com.gaetan.kryxcore.command;

import com.gaetan.api.TimeUtil;
import com.gaetan.api.command.utils.annotation.Command;
import com.gaetan.api.command.utils.command.Context;
import com.gaetan.api.command.utils.target.CommandTarget;
import com.gaetan.api.message.Message;
import com.gaetan.kryxcore.CorePlugin;
import com.gaetan.kryxcore.enums.Lang;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;
import java.util.WeakHashMap;

public final class RandomTPCommand {
    /**
     * Reference to the main class
     */
    private final CorePlugin corePlugin;

    private final WeakHashMap<UUID, Long> lastThrow = new WeakHashMap<>();
    private final Long time;
    private final String msg = Lang.COOLDOWN.getText();

    /**
     * Constructor for the RandomTPCommand class
     *
     * @param corePlugin Reference to te main class
     */
    public RandomTPCommand(final CorePlugin corePlugin) {
        this.corePlugin = corePlugin;

        this.time = (long) corePlugin.getManagerHandler().getConfigManager().getCooldownRandomTP();
    }

    /**
     * Command to use the random teleport
     *
     * @param context The command argument
     */
    @Command(name = "randomtp", aliases = "rtp", target = CommandTarget.PLAYER)
    public void handleCommand(final Context<ConsoleCommandSender> context) {
        final Player player = (Player) context.getSender();
        final long now = System.currentTimeMillis();

        if (this.validthrow(player, now)) {
            final int randomRange = (int) (Math.random() * 2001.0 + 2500.0);
            final Location location = this.corePlugin.getManagerHandler().getRandomTPManager()
                    .findLocation(new Location(player.getWorld(), 1000.0, 0.0, 1000.0), randomRange);

            if (location == null) {
                Message.tell(player, Lang.RANDOMTP_NOLOC.getText());
                return;
            }

            this.lastThrow.put(player.getUniqueId(), now);

            player.playEffect(player.getLocation(), Effect.ENDER_SIGNAL, null);
            player.teleport(location);

        }
    }

    private double remainingCooldown(final Player p, final long throwTime) {
        final Long lastPlayerPearl = this.lastThrow.get(p.getUniqueId());
        return (this.time - (throwTime - lastPlayerPearl)) / 1000.0;
    }

    private boolean validthrow(final Player p, final long throwTime) {
        final Long lastPlayerPearl = this.lastThrow.get(p.getUniqueId());
        if (lastPlayerPearl == null || throwTime - lastPlayerPearl >= this.time) return true;
        this.sendMessageChecked(p, this.msg.replace("{sec}", TimeUtil.getTime((int) (this.remainingCooldown(p, throwTime)))));
        return false;
    }

    private void sendMessageChecked(final Player p, final String message) {
        p.sendMessage(message);
    }
}
