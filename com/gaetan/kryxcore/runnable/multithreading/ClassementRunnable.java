package com.gaetan.kryxcore.runnable.multithreading;

import com.gaetan.api.ConfigUtil;
import com.gaetan.kryxcore.CorePlugin;
import com.gaetan.kryxcore.manager.managers.ClassementManager;

import java.util.concurrent.atomic.AtomicInteger;

public final class ClassementRunnable implements Runnable {
    /**
     * Reference to the main class
     */
    private final CorePlugin corePlugin;

    /**
     * Constructor for the ClassementRunnable runnable.
     *
     * @param corePlugin Reference to te main class
     */
    public ClassementRunnable(final CorePlugin corePlugin) {
        this.corePlugin = corePlugin;
    }

    @Override
    public void run() {
        final ClassementManager classementManager = this.corePlugin.getManagerHandler().getClassementManager();

        final AtomicInteger dev = new AtomicInteger(1);
        classementManager.getPvp()
                .entrySet()
                .stream()
                .sorted((a1, a2) -> {
                    final int points1 = a1.getValue();
                    final int points2 = a2.getValue();
                    return points2 - points1;
                })
                .limit(10)
                .forEach(f -> {
                    classementManager.getPvpTop().put(dev.get(), f.getKey());
                    dev.getAndIncrement();
                });

        classementManager.setPvPLore();

        final AtomicInteger devFarm = new AtomicInteger(1);
        classementManager.getFarm()
                .entrySet()
                .stream()
                .sorted((a1, a2) -> {
                    final int points1 = a1.getValue();
                    final int points2 = a2.getValue();
                    return points2 - points1;
                })
                .limit(10)
                .forEach(f -> {
                    classementManager.getFarmTop().put(devFarm.get(), f.getKey());
                    devFarm.getAndIncrement();
                });

        classementManager.setFarmLore();

        for (final String pvp : this.corePlugin.getManagerHandler().getClassementManager().getPvp().keySet()) {
            final ConfigUtil config = new ConfigUtil(this.corePlugin, "/classement/pvp", pvp);

            config.getConfig().set("points", this.corePlugin.getManagerHandler().getClassementManager().getPvp().get(pvp));
            config.save();
        }

        for (final String farm : this.corePlugin.getManagerHandler().getClassementManager().getFarm().keySet()) {
            final ConfigUtil config = new ConfigUtil(this.corePlugin, "/classement/farm", farm);

            config.getConfig().set("points", this.corePlugin.getManagerHandler().getClassementManager().getFarm().get(farm));
            config.save();
        }
    }
}
