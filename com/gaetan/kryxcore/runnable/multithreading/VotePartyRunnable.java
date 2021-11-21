package com.gaetan.kryxcore.runnable.multithreading;

import com.gaetan.kryxcore.CorePlugin;

public final class VotePartyRunnable implements Runnable {
    /**
     * Reference to the main class
     */
    private final CorePlugin corePlugin;

    /**
     * Constructor for the VotePartyRunnable runnable.
     *
     * @param corePlugin Reference to the main class
     */
    public VotePartyRunnable(final CorePlugin corePlugin) {
        this.corePlugin = corePlugin;
    }

    @Override
    public void run() {
        this.corePlugin.getManagerHandler().getVotePartyManager().saveVoteParty();
    }
}
