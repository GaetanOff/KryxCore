package com.gaetan.kryxcore.runnable.async;

import com.gaetan.api.RandomUtil;
import com.gaetan.api.message.Message;
import com.gaetan.api.runnable.TaskUtil;
import com.gaetan.kryxcore.CorePlugin;
import com.gaetan.kryxcore.manager.managers.ConfigManager;
import com.gaetan.kryxcore.manager.managers.ReactionManager;
import org.bukkit.scheduler.BukkitRunnable;

public final class ReactionRunnable extends BukkitRunnable {
    /**
     * Reference to the main class
     */
    private final CorePlugin corePlugin;

    /**
     * Constructor for the ReactionRunnable runnable.
     *
     * @param corePlugin Reference to the main class
     */
    public ReactionRunnable(final CorePlugin corePlugin) {
        this.corePlugin = corePlugin;
    }

    /**
     * Chat Reaction event
     */
    @Override
    public void run() {
        final ReactionManager reactionManager = this.corePlugin.getManagerHandler().getReactionManager();
        final ConfigManager configManager = this.corePlugin.getManagerHandler().getConfigManager();

        String message = reactionManager.getMessage();
        reactionManager.setCurrentMessage(message);
        for (int i = 0; i < message.length() - 1; message = reactionManager.swap(message, i, RandomUtil.nextInt(message.length() - 1)), ++i) {
        }
        reactionManager.setCurrentTime(System.currentTimeMillis());
        Message.tellToEveryone(configManager.getShowMessage().replace("%message%", message));

        TaskUtil.runLaterAsync(() -> {
            if (reactionManager.currentMessage != null) {
                Message.tellToEveryone(configManager.getReplyMessage().replace("%message%", reactionManager.currentMessage));
                reactionManager.resetMessage();
            }
        }, configManager.getDelayReactionToReplay());
    }
}
