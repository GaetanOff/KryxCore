package com.gaetan.kryxcore.manager.managers;

import com.gaetan.api.ConfigUtil;
import com.gaetan.api.message.Message;
import com.gaetan.kryxcore.manager.Manager;
import com.gaetan.kryxcore.manager.ManagerHandler;
import com.gaetan.kryxcore.runnable.multithreading.ClassementRunnable;
import com.google.common.collect.Maps;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public final class ClassementManager extends Manager {
    /**
     * Map to stock the classement
     */
    private final Map<String, Integer> pvp = Maps.newConcurrentMap();
    private final Map<String, Integer> farm = Maps.newConcurrentMap();

    private final Map<Integer, String> pvpTop = Maps.newConcurrentMap();
    private final Map<Integer, String> farmTop = Maps.newConcurrentMap();
    private final List<String> lorePvP = new ArrayList<>();
    private final List<String> loreFarm = new ArrayList<>();

    /**
     * Constructor for the Manager class.
     *
     * @param handler Reference to the ManagerHandler
     */
    public ClassementManager(final ManagerHandler handler) {
        super(handler);

        this.handler.getThreadManager().getThreadPool().execute(() -> {
            this.loadFarm();
            this.loadPvP();
        });

        //Implementing Multi-threading
        this.handler.getThreadManager().getThreadRunnablePool().scheduleAtFixedRate(
                new ClassementRunnable(this.handler.getCorePlugin()), 0, 10, TimeUnit.MINUTES
        );
    }

    private void loadPvP() {
        final File file = new File(this.handler.getCorePlugin().getDataFolder() + "/classement/pvp");
        if (file.exists()) {
            for (final File files : Objects.requireNonNull(file.listFiles())) {
                final String fileName = files.getName().replace(".yml", "");
                final ConfigUtil config = new ConfigUtil(this.handler.getCorePlugin(), "/classement/pvp", fileName);

                this.pvp.put(fileName, config.getConfig().getInt("points"));
            }
        }
    }

    private void loadFarm() {
        final File file = new File(this.handler.getCorePlugin().getDataFolder() + "/classement/farm");
        if (file.exists()) {
            for (final File files : Objects.requireNonNull(file.listFiles())) {
                final String fileName = files.getName().replace(".yml", "");
                final ConfigUtil config = new ConfigUtil(this.handler.getCorePlugin(), "/classement/farm", fileName);

                this.farm.put(fileName, config.getConfig().getInt("points"));
            }
        }
    }

    public void addPvpPoint(final String faction, final int point) {
        this.pvp.merge(faction, point, Integer::sum);
    }

    public void addFarmPoint(final String faction, final int point) {
        this.farm.merge(faction, point, Integer::sum);
    }

    public void removePvPPoint(final String faction, final int point) {
        if (this.pvp.get(faction) != null) {
            if (this.pvp.get(faction) < point) return;
            this.pvp.put(faction, this.pvp.get(faction) - point);
        }
    }

    public void removeFarmPoint(final String faction, final int point) {
        if (this.farm.get(faction) != null) {
            if (this.farm.get(faction) < point) return;
            this.farm.put(faction, this.farm.get(faction) - point);
        }
    }


    public void resetPvPFac(final String faction) {
        this.pvp.remove(faction);
        new ClassementRunnable(this.handler.getCorePlugin()).run();
        if (new File(this.handler.getCorePlugin().getDataFolder() + "/classement/pvp", faction + ".yml").exists()) {
            final ConfigUtil config = new ConfigUtil(this.handler.getCorePlugin(), "/classement/pvp", faction);

            config.delete();
        }
    }

    public void resetFarmFac(final String faction) {
        this.farm.remove(faction);
        new ClassementRunnable(this.handler.getCorePlugin()).run();
        if (new File(this.handler.getCorePlugin().getDataFolder() + "/classement/farm", faction + ".yml").exists()) {
            final ConfigUtil config = new ConfigUtil(this.handler.getCorePlugin(), "/classement/farm", faction);

            config.delete();
        }
    }

    public void setPvPLore() {
        this.lorePvP.clear();
        this.lorePvP.add(" ");
        this.pvpTop.keySet().forEach(test -> {
            final String fac = this.pvpTop.get(test);
            this.lorePvP.add(Message.GOLD + " #" + test + Message.GRAY + " - " + Message.YELLOW + fac + Message.GRAY + " - " + Message.YELLOW + this.pvp.get(fac) + "pts");
        });
        this.lorePvP.add(" ");
    }

    public void setFarmLore() {
        this.loreFarm.clear();
        this.loreFarm.add(" ");
        this.farmTop.keySet().forEach(test -> {
            final String fac = this.farmTop.get(test);
            this.loreFarm.add(Message.GOLD + " #" + test + Message.GRAY + " - " + Message.YELLOW + fac + Message.GRAY + " - " + Message.YELLOW + this.farm.get(fac) + "pts");
        });
        this.loreFarm.add(" ");
    }

    public Map<String, Integer> getPvp() {
        return this.pvp;
    }

    public Map<String, Integer> getFarm() {
        return this.farm;
    }

    public Map<Integer, String> getPvpTop() {
        return this.pvpTop;
    }

    public Map<Integer, String> getFarmTop() {
        return this.farmTop;
    }

    public List<String> getLorePvP() {
        return this.lorePvP;
    }

    public List<String> getLoreFarm() {
        return this.loreFarm;
    }
}
