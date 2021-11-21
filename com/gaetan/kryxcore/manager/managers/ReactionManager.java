package com.gaetan.kryxcore.manager.managers;

import com.gaetan.api.RandomUtil;
import com.gaetan.api.message.Message;
import com.gaetan.api.runnable.TaskUtil;
import com.gaetan.kryxcore.manager.Manager;
import com.gaetan.kryxcore.manager.ManagerHandler;
import com.gaetan.kryxcore.runnable.async.ReactionRunnable;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public final class ReactionManager extends Manager {
    /**
     * The current event message
     */
    public String currentMessage;

    /**
     * The current time message
     */
    public long currentTime;

    /**
     * Constructor for the ReactionManager class.
     * Note: This will launch the runnable for the reaction event
     *
     * @param managerHandler Reference to te managerHandler class
     */
    public ReactionManager(final ManagerHandler managerHandler) {
        super(managerHandler);

        new ReactionRunnable(this.handler.getCorePlugin()).runTaskTimerAsynchronously(
                this.handler.getCorePlugin(), 0L, this.handler.getConfigManager().getDelayReaction() * 20L
        );
    }

    /**
     * Swap a word
     *
     * @param str The word
     * @param i   Beginner
     * @param j   Ender
     */
    public String swap(final String str, final int i, final int j) {
        final StringBuilder strB = new StringBuilder(str);
        final char l = strB.charAt(i);
        final char r = strB.charAt(j);
        strB.setCharAt(i, r);
        strB.setCharAt(j, l);
        return strB.toString();
    }

    /**
     * Getter to get a random message in the list
     *
     * @return The random choose message
     */
    public String getMessage() {
        return this.handler.getConfigManager().getReactionMessage().get(RandomUtil.nextInt(this.handler.getConfigManager().getReactionMessage().size() - 1));
    }

    /**
     * Reset the current message
     */
    public void resetMessage() {
        this.currentMessage = null;
    }

    /**
     * Give the reward of the winner
     *
     * @param player The winner
     */
    public void giveReward(final Player player) {
        Message.tellToEveryone(this.handler.getConfigManager().getRewardMessage().replace("%time%", String.valueOf((System.currentTimeMillis() - this.currentTime) / 1000L)).replace("%player%", player.getName()));
        TaskUtil.run(() -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), this.handler.getConfigManager().getRewardCommand().replace("%player%", player.getName())));
        this.resetMessage();
    }

    /**
     * Setter to set the current message.
     *
     * @param currentMessage The current message.
     */
    public void setCurrentMessage(final String currentMessage) {
        this.currentMessage = currentMessage;
    }

    /**
     * Getter to get current message.
     *
     * @return The current message
     */
    public String getCurrentMessage() {
        return this.currentMessage;
    }

    /**
     * Setter to set the current time.
     *
     * @param cuurentTime The current time.
     */
    public void setCurrentTime(final long cuurentTime) {
        this.currentTime = cuurentTime;
    }
}
