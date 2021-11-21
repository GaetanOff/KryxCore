package com.gaetan.kryxcore.runnable.async;

import com.gaetan.kryxcore.CorePlugin;
import fr.minuskube.netherboard.Netherboard;
import fr.minuskube.netherboard.bukkit.BPlayerBoard;
import org.bukkit.scheduler.BukkitRunnable;

public final class BoardRunnable extends BukkitRunnable {
    /**
     * Reference to the main class
     */
    private final CorePlugin corePlugin;

    /**
     * Constructor for the BoardRunnable runnable.
     *
     * @param corePlugin Reference to te main class
     */
    public BoardRunnable(final CorePlugin corePlugin) {
        this.corePlugin = corePlugin;
    }

    /**
     * Content of the scoreboard
     */
    @Override
    public void run() {
        if (this.corePlugin.getManagerHandler().getEventManager().isEvent())
            return;

        this.corePlugin.getServer().getOnlinePlayers().forEach(player -> {
            final BPlayerBoard board = Netherboard.instance().getBoard(player);

            int test = this.corePlugin.getManagerHandler().getConfigManager().getScoreboardLine().size();
            for (final String string : this.corePlugin.getManagerHandler().getConfigManager().getScoreboardLine()) {
                this.corePlugin.getManagerHandler().getBoardManager().setBoard(board, string, test);
                test--;
            }
        });
    }
}
