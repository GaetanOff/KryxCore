package com.gaetan.kryxcore.manager.managers;

import com.gaetan.api.message.Message;
import com.gaetan.api.serializer.Serialize;
import com.gaetan.kryxcore.manager.Manager;
import com.gaetan.kryxcore.manager.ManagerHandler;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

public final class ConfigManager extends Manager {
    /**
     * Cache constant for the config
     */
    private final List<String> scoreboardLine, autoMessage, reactionMessage, voteMessage, siteMessage, discordMessage, joinMessages;

    /**
     * Cache constant for the config
     */
    private final String scoreboardTitle, replyMessage, rewardMessage, showMessage, rewardCommand, votePartyRewardMessage,
            brMessage, brServer;

    /**
     * Cache constant for the config
     */
    private final int scoreboardUpdate, delayAutoMessage, delayReaction, delayReactionToReplay, votePartyReward,
            cooldownApple, cooldownAppleCheat, cooldownRandomTP, cooldownEnderpearl;

    private final boolean joinEnabled, joinClearChat;

    /**
     * Cache not constant for the config
     */
    private int votePartyNow, votePartyMax;

    private Location spawn;


    /**
     * Constructor for the PlayerListener class.
     *
     * @param managerHandler Reference to te managerHandler class
     */
    public ConfigManager(final ManagerHandler managerHandler) {
        super(managerHandler);

        final FileConfiguration config = this.handler.getCorePlugin().getConfig();

        this.scoreboardUpdate = config.getInt("scoreboard.update") * 20;
        this.scoreboardLine = config.getStringList("scoreboard.line");
        this.scoreboardTitle = Message.tl(config.getString("scoreboard.title"));

        this.delayAutoMessage = config.getInt("auto-message.delay");
        this.autoMessage = config.getStringList("auto-message.messages");

        this.showMessage = Message.tl(config.getString("chat-reaction.showMessage"));
        this.replyMessage = Message.tl(config.getString("chat-reaction.replyMessage"));
        this.rewardMessage = Message.tl(config.getString("chat-reaction.rewardMessage"));
        this.rewardCommand = config.getString("chat-reaction.reward");
        this.reactionMessage = config.getStringList("chat-reaction.messages");
        this.delayReaction = config.getInt("chat-reaction.delay");
        this.delayReactionToReplay = config.getInt("chat-reaction.delayToReply") * 20;

        this.voteMessage = config.getStringList("commands.vote.message");
        this.siteMessage = config.getStringList("commands.site.message");
        this.discordMessage = config.getStringList("commands.discord.message");

        this.votePartyNow = config.getInt("voteparty.now");
        this.votePartyMax = config.getInt("voteparty.max");
        this.votePartyReward = config.getInt("voteparty.reward.money");
        this.votePartyRewardMessage = Message.tl(config.getString("voteparty.reward.message"));

        this.cooldownApple = config.getInt("cooldown.goldenapple.normal") * 1000;
        this.cooldownAppleCheat = config.getInt("cooldown.goldenapple.cheat") * 1000;
        this.cooldownRandomTP = config.getInt("cooldown.randomtp") * 1000;
        this.cooldownEnderpearl = config.getInt("cooldown.enderpearl") * 1000;

        this.joinEnabled = config.getBoolean("join.enabled");
        this.joinClearChat = config.getBoolean("join.clearchat");
        this.joinMessages = config.getStringList("join.messages");

        this.spawn = config.getString("spawn") == null ? null : Serialize.deserializeLocation(config.getString("spawn"));

        this.brMessage = Message.tl(config.getString("broadcast.message"));
        this.brServer = Message.tl(config.getString("broadcast.server"));
    }

    /**
     * Getter
     */
    public List<String> getScoreboardLine() {
        return this.scoreboardLine;
    }

    public String getScoreboardTitle() {
        return this.scoreboardTitle;
    }

    public List<String> getAutoMessage() {
        return this.autoMessage;
    }

    public int getScoreboardUpdate() {
        return this.scoreboardUpdate;
    }

    public int getDelayAutoMessage() {
        return this.delayAutoMessage;
    }

    public int getVotePartyNow() {
        return this.votePartyNow;
    }

    public int getVotePartyMax() {
        return this.votePartyMax;
    }

    public int getVotePartyReward() {
        return this.votePartyReward;
    }

    public int getCooldownApple() {
        return this.cooldownApple;
    }

    public int getCooldownAppleCheat() {
        return this.cooldownAppleCheat;
    }

    public int getCooldownRandomTP() {
        return this.cooldownRandomTP;
    }

    public int getCooldownEnderpearl() {
        return this.cooldownEnderpearl;
    }

    public String getVotePartyRewardMessage() {
        return this.votePartyRewardMessage;
    }

    public List<String> getReactionMessage() {
        return this.reactionMessage;
    }

    public String getReplyMessage() {
        return this.replyMessage;
    }

    public String getRewardMessage() {
        return this.rewardMessage;
    }

    public String getShowMessage() {
        return this.showMessage;
    }

    public String getRewardCommand() {
        return this.rewardCommand;
    }

    public int getDelayReaction() {
        return this.delayReaction;
    }

    public int getDelayReactionToReplay() {
        return this.delayReactionToReplay;
    }

    public List<String> getVoteMessage() {
        return this.voteMessage;
    }

    public List<String> getSiteMessage() {
        return this.siteMessage;
    }

    public List<String> getDiscordMessage() {
        return this.discordMessage;
    }

    public List<String> getJoinMessages() {
        return this.joinMessages;
    }

    public boolean isJoinClearChat() {
        return this.joinClearChat;
    }

    public boolean isJoinEnabled() {
        return this.joinEnabled;
    }

    public Location getSpawn() {
        return this.spawn;
    }

    public String getBrMessage() {
        return this.brMessage;
    }

    public String getBrServer() {
        return this.brServer;
    }

    /**
     * Setter
     */
    public void setVotePartyNow(final int votePartyNow) {
        this.votePartyNow = votePartyNow;
    }

    public void setVotePartyMax(final int votePartyMax) {
        this.votePartyMax = votePartyMax;
    }

    /**
     * Setter to set the spawn location.
     *
     * @param spawn The spawn location.
     */
    public void setSpawn(final Location spawn) {
        this.spawn = spawn;

        this.handler.getCorePlugin().getConfig().set("spawn", Serialize.serializeLocation(spawn));
        this.handler.getCorePlugin().saveConfig();
    }
}
