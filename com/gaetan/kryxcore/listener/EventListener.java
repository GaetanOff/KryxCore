package com.gaetan.kryxcore.listener;

import com.gaetan.kryxcore.CorePlugin;
import org.bukkit.event.Listener;

public final class EventListener implements Listener {
    /**
     * Reference to the main class
     */
    private final CorePlugin corePlugin;

    /**
     * Constructor for the EventListener class.
     *
     * @param corePlugin Reference to te main class
     */
    public EventListener(final CorePlugin corePlugin) {
        this.corePlugin = corePlugin;
        this.corePlugin.getServer().getPluginManager().registerEvents(this, this.corePlugin);
    }


    /**@EventHandler public void onKothSpawn(final StartEventEvent event) {
    this.corePlugin.getManagerHandler().getEventManager().setEvent(true);
    this.corePlugin.getPlayers().values().forEach(PlayerData::unloadScoreboard);
    }

     @EventHandler public void onKothStop(final StopEventEvent event) {
     this.corePlugin.getManagerHandler().getEventManager().setEvent(false);

     TaskUtil.runLater(() -> this.corePlugin.getPlayers().values().forEach(PlayerData::loadScoreboard), 5L);
     }**/
}
