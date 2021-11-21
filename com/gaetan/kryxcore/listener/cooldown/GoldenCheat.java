package com.gaetan.kryxcore.listener.cooldown;

import com.gaetan.kryxcore.CorePlugin;
import org.bukkit.event.Listener;

public final class GoldenCheat implements Listener {
    /*
     * Reference to the main class
     */
    private final CorePlugin corePlugin;

    /*
     * Constructor for the GoldenCheat class.
     *
     * @param corePlugin Reference to te main class
     */
    public GoldenCheat(final CorePlugin corePlugin) {
        this.corePlugin = corePlugin;
    }
}
