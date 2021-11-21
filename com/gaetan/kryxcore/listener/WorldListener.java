package com.gaetan.kryxcore.listener;

import com.gaetan.kryxcore.CorePlugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.WeatherChangeEvent;

public final class WorldListener implements Listener {
    /**
     * Constructor for the WorldListener class.
     *
     * @param corePlugin Reference to te main class
     */
    public WorldListener(final CorePlugin corePlugin) {
        corePlugin.getServer().getPluginManager().registerEvents(this, corePlugin);
    }

    /**
     * When the weather change state
     */
    @EventHandler
    public void onWeatherChange(final WeatherChangeEvent event) {
        event.setCancelled(true);
    }
}
