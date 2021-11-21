package com.gaetan.kryxcore.manager.managers;

import com.gaetan.api.message.Message;
import com.gaetan.kryxcore.enums.Lang;
import com.gaetan.kryxcore.manager.Manager;
import com.gaetan.kryxcore.manager.ManagerHandler;
import com.gaetan.kryxcore.runnable.multithreading.ClearLagRunnable;
import org.bukkit.World;
import org.bukkit.entity.Animals;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Monster;

import java.util.List;
import java.util.concurrent.TimeUnit;

public final class ClearLagManager extends Manager {
    /**
     * Constructor for the ClearLagManager class.
     *
     * @param handler Reference to the ManagerHandler
     */
    public ClearLagManager(final ManagerHandler handler) {
        super(handler);

        //Implementing Multi-threading
        this.handler.getThreadManager().getThreadRunnablePool().scheduleAtFixedRate(
                new ClearLagRunnable(this.handler.getCorePlugin()), 0, 1, TimeUnit.SECONDS
        );
    }

    public void clearLagg() {
        final World world = this.handler.getCorePlugin().getServer().getWorld("world");
        final List<Entity> entList = world.getEntities();

        int count = 0;
        for (final Entity current : entList) {
            if (current instanceof Item || current instanceof Monster || current instanceof Animals) {
                current.remove();
                count++;
            }
        }

        Message.tellToEveryone(Lang.CL_CLEAR_SUCESS.getText().replace("%s", String.valueOf(count)));
    }
}
