package com.gaetan.kryxcore.runnable.async;

import com.gaetan.api.message.Message;
import com.gaetan.api.runnable.TaskUtil;
import com.gaetan.kryxcore.CorePlugin;
import com.gaetan.kryxcore.data.PlayerData;
import com.gaetan.kryxcore.enums.Lang;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public final class TeleportRunnable extends BukkitRunnable {
    private final Player player;
    private final PlayerData playerData;
    private final CorePlugin corePlugin;
    private final Location loc;
    private final String type;
    private int counter;

    public TeleportRunnable(final int counter, final PlayerData playerData, final CorePlugin corePlugin, final String type) {
        this.counter = counter;
        this.playerData = playerData;
        this.player = playerData.getPlayer();
        this.corePlugin = corePlugin;
        this.loc = playerData.getPlayer().getLocation();
        this.type = type;
        Message.tell(this.player, Lang.INVEST_COUNTER.getText());
    }

    @Override
    public void run() {
        if (this.counter > 0) {
            if (this.playerData.isInvest() && this.loc.distance(this.player.getLocation()) < 0.2) {
                --this.counter;
            } else {
                Message.tell(this.player, Lang.INVEST_CANCELLED.getText());
                this.playerData.setInvest(false);
                this.cancel();
            }
        } else {
            this.cancel();
            this.playerData.setInvest(false);
            if (this.type.equals("spawn"))
                TaskUtil.run(() -> this.player.teleport(this.corePlugin.getManagerHandler().getConfigManager().getSpawn()));
            else this.sendtoServer(this.player, "invest");
        }
    }

    public void sendtoServer(final Player player, final String server) {
        final ByteArrayOutputStream b = new ByteArrayOutputStream();
        final DataOutputStream out = new DataOutputStream(b);
        try {
            out.writeUTF("Connect");
            out.writeUTF(server);
        } catch (final IOException e) {
            e.printStackTrace();
        }
        player.sendPluginMessage(this.corePlugin, "BungeeCord", b.toByteArray());
    }
}
