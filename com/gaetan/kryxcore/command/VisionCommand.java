package com.gaetan.kryxcore.command;

import com.gaetan.api.command.utils.annotation.Command;
import com.gaetan.api.command.utils.command.Context;
import com.gaetan.api.command.utils.target.CommandTarget;
import com.gaetan.api.message.Message;
import com.gaetan.api.runnable.TaskUtil;
import com.gaetan.kryxcore.CorePlugin;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public final class VisionCommand {
    /**
     * Reference to the main class
     */
    private final CorePlugin corePlugin;

    /**
     * Constructor for the VisionCommand class
     *
     * @param corePlugin Reference to the main class
     */
    public VisionCommand(final CorePlugin corePlugin) {
        this.corePlugin = corePlugin;
    }

    /**
     * Command to set the vision
     *
     * @param context The command argument
     */
    @Command(name = "vision", permission = "vision.use", target = CommandTarget.PLAYER)
    public void handleCommand_Vote(final Context<ConsoleCommandSender> context) {
        this.corePlugin.getManagerHandler().getThreadManager().getThreadPool().execute(() -> {
            final Player player = (Player) context.getSender();

            if (player.hasPotionEffect(PotionEffectType.NIGHT_VISION)) {
                //Sync cuz we are not on the main thread before !
                TaskUtil.run(() -> player.removePotionEffect(PotionEffectType.NIGHT_VISION));
                Message.tell(player, Message.RED + "Vous avez désactivé le mode vision.");
                return;
            }

            //Sync cuz we are not on the main thread before !
            TaskUtil.run(() -> player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 255)));
            Message.tell(player, Message.GREEN + "Vous avez reçu activé le mode vision.");
        });
    }

}
