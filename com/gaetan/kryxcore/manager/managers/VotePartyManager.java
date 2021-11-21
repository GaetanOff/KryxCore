package com.gaetan.kryxcore.manager.managers;

import com.gaetan.api.command.utils.command.Context;
import com.gaetan.api.message.Message;
import com.gaetan.kryxcore.CorePlugin;
import com.gaetan.kryxcore.enums.Lang;
import com.gaetan.kryxcore.manager.Manager;
import com.gaetan.kryxcore.manager.ManagerHandler;
import com.gaetan.kryxcore.runnable.multithreading.VotePartyRunnable;
import org.bukkit.command.ConsoleCommandSender;

import java.util.concurrent.TimeUnit;

public final class VotePartyManager extends Manager {
    /**
     * Reference to the ConfigManager
     */
    private final ConfigManager configManager;

    /**
     * Constructor for the VotePartyManager class.
     *
     * @param handler Reference to the ManagerHandler
     */
    public VotePartyManager(final ManagerHandler handler) {
        super(handler);

        this.configManager = this.handler.getConfigManager();

        //Implementing Multi-threading
        this.handler.getThreadManager().getThreadRunnablePool().scheduleAtFixedRate(
                new VotePartyRunnable(this.handler.getCorePlugin()), 0, 10, TimeUnit.MINUTES
        );
    }

    /**
     * Set the max vote party number
     *
     * @param sender   The sender
     * @param maxToSet The max number
     */
    public void setMax(final Context<ConsoleCommandSender> sender, final int maxToSet) {
        this.configManager.setVotePartyMax(maxToSet);
        sender.sendMessage(Lang.VOTE_PARTY_MAX.getText().replace("%s", String.valueOf(this.configManager.getVotePartyMax())));
    }

    /**
     * Add a {number} to the vote party
     *
     * @param sender      The sender
     * @param numberToAdd The {number}
     */
    public void addVote(final Context<ConsoleCommandSender> sender, final int numberToAdd) {
        this.configManager.setVotePartyNow(this.configManager.getVotePartyNow() + numberToAdd);
        sender.sendMessage(Lang.VOTE_PARTY_ADD.getText().replace("%s", String.valueOf(this.configManager.getVotePartyNow())));

        this.checkReward();
    }

    /**
     * Remove a {number} to the vote party
     *
     * @param sender         The sender
     * @param numberToRemove The {number}
     */
    public void removeVote(final Context<ConsoleCommandSender> sender, final int numberToRemove) {
        this.configManager.setVotePartyNow(this.configManager.getVotePartyNow() - numberToRemove);
        sender.sendMessage(Lang.VOTE_PARTY_REMOVE.getText().replace("%s", String.valueOf(this.configManager.getVotePartyNow())));
    }

    /**
     * Check if the vote party has ended and give the reward
     */
    private void checkReward() {
        if (this.configManager.getVotePartyNow() >= this.configManager.getVotePartyMax()) {
            this.handler.getCorePlugin().getServer().getOnlinePlayers().forEach(players -> {
                this.configManager.setVotePartyNow(0);
                this.handler.getEconomy().depositPlayer(players, this.configManager.getVotePartyReward());
                Message.tell(players, this.configManager.getVotePartyRewardMessage());
            });
        }
    }

    public void saveVoteParty() {
        final CorePlugin corePlugin = this.handler.getCorePlugin();
        final ConfigManager configManager = corePlugin.getManagerHandler().getConfigManager();

        corePlugin.getConfig().set("voteparty.now", configManager.getVotePartyNow());
        corePlugin.getConfig().set("voteparty.max", configManager.getVotePartyMax());
        corePlugin.saveConfig();
    }
}
