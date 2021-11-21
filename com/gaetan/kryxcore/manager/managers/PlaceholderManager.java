package com.gaetan.kryxcore.manager.managers;

import com.gaetan.kryxcore.manager.Manager;
import com.gaetan.kryxcore.manager.ManagerHandler;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;

public final class PlaceholderManager extends Manager {
    /**
     * Constructor for the PlaceholderManager class.
     *
     * @param handler Reference to the ManagerHandler
     */
    public PlaceholderManager(final ManagerHandler handler) {
        super(handler);

        this.registerPlaceholders();
    }

    //Register External Placeholder
    public void registerPlaceholders() {
        new PlaceholderExpansion() {
            @Override
            public String getIdentifier() {
                return "KryxCore";
            }

            @Override
            public String getAuthor() {
                return "GaetanDev";
            }

            @Override
            public String getVersion() {
                return "1.0";
            }

            @Override
            public String onPlaceholderRequest(final Player player, final String params) {
                if (params.equalsIgnoreCase("voteparty")) {
                    return PlaceholderManager.this.handler.getConfigManager().getVotePartyNow() +
                            "/" +
                            PlaceholderManager.this.handler.getConfigManager().getVotePartyMax();
                }
                return "";
            }
        }.register();
    }
}
