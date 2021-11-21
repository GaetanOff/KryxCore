package com.gaetan.kryxcore.data;

import com.gaetan.api.item.ItemBuilder;
import com.gaetan.api.message.Message;
import com.gaetan.api.runnable.TaskUtil;
import com.gaetan.kryxcore.CorePlugin;
import com.gaetan.kryxcore.enums.Lang;
import com.gaetan.kryxcore.manager.managers.ConfigManager;
import com.gaetan.kryxcore.runnable.multithreading.data.LoadPlayerConfig;
import com.gaetan.kryxcore.runnable.multithreading.data.SavePlayerConfig;
import fr.minuskube.netherboard.Netherboard;
import fr.minuskube.netherboard.bukkit.BPlayerBoard;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public final class PlayerData {
    /**
     * Reference to the main class
     */
    private final CorePlugin corePlugin;

    private final Player player;
    private boolean staffMode;
    private ItemStack[] lastInventory, lastArmor;
    private boolean vanish;
    private boolean freeze;
    private boolean invest;

    private boolean atoutHaste,
            atoutJumpBoost,
            atoutSpeed,
            atoutWater,
            atoutStrenght,
            atoutFireRes;
    private final Inventory bagPack;

    /**
     * Constructor for the PlayerData class.
     *
     * @param corePlugin Reference to te main class
     * @param player     Reference to the Player
     */
    public PlayerData(final Player player, final CorePlugin corePlugin) {
        this.corePlugin = corePlugin;

        this.player = player;
        this.staffMode = false;
        this.freeze = false;
        this.invest = false;

        this.atoutHaste = false;
        this.atoutJumpBoost = false;
        this.atoutSpeed = false;
        this.atoutWater = false;
        this.atoutStrenght = false;
        this.atoutFireRes = false;
        this.bagPack = corePlugin.getServer().createInventory(null, 54, Lang.BAGPACK_INVENTORY_NAME.getText());
    }

    /**
     * Update the players visibility of the player
     */
    public void updateVanish() {
        final ItemStack dye;

        if (this.isVanish()) {
            this.corePlugin.getServer().getOnlinePlayers().stream()
                    .filter(player1 -> this.corePlugin.getPlayer(player1) != null)
                    .forEach((player1) -> {
                        player1.hidePlayer(this.player);
                        if (this.corePlugin.getPlayer(player1).isStaffMode()) {
                            player1.showPlayer(this.player);
                            this.player.showPlayer(player1);
                        }
                    });
            dye = new ItemBuilder(Material.INK_SACK, 1, (short) 10).setName(Lang.VANISH.getText()).toItemStack();
            Message.tell(this.player, Lang.VANISH_ON.getText());
        } else {
            this.corePlugin.getServer().getOnlinePlayers().stream()
                    .filter(player1 -> this.corePlugin.getPlayer(player1) != null)
                    .forEach((player1) -> {
                        player1.showPlayer(this.player);
                        if (this.corePlugin.getPlayer(player1).isStaffMode())
                            this.player.hidePlayer(player1);
                    });
            dye = new ItemBuilder(Material.INK_SACK, 1, (short) 1).setName(Lang.VANISH.getText()).toItemStack();
            Message.tell(this.player, Lang.VANISH_OFF.getText());
        }

        this.player.getInventory().setItem(6, dye);
    }

    /**
     * Execute join message if its enabled
     */
    public void executeJoin() {
        this.corePlugin.getManagerHandler().getThreadManager().getThreadPool().execute(() -> {
            final ConfigManager configManager = this.corePlugin.getManagerHandler().getConfigManager();

            if (configManager.isJoinEnabled()) {
                if (configManager.isJoinClearChat()) Message.tell(this.player, StringUtils.repeat(" \n", 100));

                configManager.getJoinMessages().forEach(message -> Message.tell(this.player, Message.tl(message)));
            }

            new LoadPlayerConfig(this.corePlugin, this).run();

            if (!this.player.hasPlayedBefore()) {
                Message.tellToEveryone(Message.tl("&fBienvenue à &c&l{DISPLAYNAME} &fsur &6&lKryxMC &f!")
                        .replace("{DISPLAYNAME}", this.player.getName())
                );

                //Sync cuz we are not in the main thread before
                TaskUtil.run(() -> {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "kit cowboy " + this.player.getName());
                    this.player.teleport(this.corePlugin.getManagerHandler().getConfigManager().getSpawn());
                });
            }
        });
    }

    /**
     * Load the scoreboard for the player
     */
    public void loadScoreboard() {
        if (this.corePlugin.getManagerHandler().getEventManager().isEvent()) {
            this.unloadScoreboard();
            return;
        }

        final ConfigManager configManager = this.corePlugin.getManagerHandler().getConfigManager();
        final BPlayerBoard board = Netherboard.instance().createBoard(this.player, configManager.getScoreboardTitle());

        int test = configManager.getScoreboardLine().size();
        for (final String string : configManager.getScoreboardLine()) {
            this.corePlugin.getManagerHandler().getBoardManager().setBoard(board, string, test);
            test--;
        }
    }

    /**
     * UnLoad the scoreboard for the player
     */
    public void unloadScoreboard() {
        Netherboard.instance().deleteBoard(this.player);
    }

    /**
     * Execute when a player disconnect
     */
    public void leave() {
        if (this.staffMode) {
            this.player.getInventory().setContents(this.lastInventory);
            this.player.getInventory().setArmorContents(this.lastArmor);
            this.player.teleport(this.corePlugin.getServer().getWorld("world").getSpawnLocation());
            this.player.setGameMode(GameMode.SURVIVAL);
            this.player.updateInventory();
        }

        //this.unloadScoreboard();
        this.corePlugin.getManagerHandler().getThreadManager().getThreadPool().execute(new SavePlayerConfig(this.corePlugin, this));
    }

    /**
     * Setter to set the status of the staff mode
     */
    public void setStaffMode(final boolean state) {
        this.staffMode = state;

        if (state) {
            Message.tell(this.player, Lang.STAFF_ON.getText());

            this.lastInventory = this.player.getInventory().getContents();
            this.lastArmor = this.player.getInventory().getArmorContents();

            this.player.getInventory().clear();
            this.player.getInventory().setArmorContents(null);
            this.player.setGameMode(GameMode.CREATIVE);
            this.player.getInventory().setHeldItemSlot(0);
            this.player.getInventory().setContents(this.corePlugin.getManagerHandler().getItemManager().getStaffItems());
            this.player.getInventory().setHelmet(new ItemStack(Material.DIAMOND_BLOCK));
            this.player.updateInventory();

            this.vanish = true;
            this.updateVanish();
        } else {
            Message.tell(this.player, Lang.STAFF_OFF.getText());

            this.player.getInventory().setContents(this.lastInventory);
            this.player.getInventory().setArmorContents(this.lastArmor);
            this.player.teleport(this.corePlugin.getServer().getWorld("world").getSpawnLocation());
            this.player.setGameMode(GameMode.SURVIVAL);

            this.lastInventory = null;
            this.lastArmor = null;

            this.player.updateInventory();

            if (this.isVanish()) {
                this.corePlugin.getServer().getOnlinePlayers().stream()
                        .filter(players -> this.corePlugin.getPlayer(players) != null)
                        .forEach((player1) -> {
                            player1.showPlayer(this.player);
                            player1.showPlayer(this.player);
                            if (this.corePlugin.getPlayer(player1).isStaffMode())
                                this.player.hidePlayer(player1);
                        });
                this.vanish = false;
            }
        }
    }

    /**
     * Check Atout
     */
    public void checkAtout() {
        TaskUtil.run(() -> {
            if (this.atoutHaste)
                this.player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, Integer.MAX_VALUE, 1));

            if (this.atoutSpeed)
                this.player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 1));

            if (this.atoutStrenght)
                this.player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Integer.MAX_VALUE, 0));

            if (this.atoutFireRes)
                this.player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, Integer.MAX_VALUE, 1));

            if (this.atoutWater)
                this.player.addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, Integer.MAX_VALUE, 1));
        });
    }

    /**
     * Getter
     */
    public boolean isStaffMode() {
        return this.staffMode;
    }

    public boolean isVanish() {
        return this.vanish;
    }

    public boolean isFreeze() {
        return this.freeze;
    }

    public boolean isInvest() {
        return this.invest;
    }

    public Player getPlayer() {
        return this.player;
    }

    public boolean isAtoutHaste() {
        return this.atoutHaste;
    }

    public boolean isAtoutJumpBoost() {
        return this.atoutJumpBoost;
    }

    public boolean isAtoutSpeed() {
        return this.atoutSpeed;
    }

    public boolean isAtoutWater() {
        return this.atoutWater;
    }

    public boolean isAtoutStrenght() {
        return this.atoutStrenght;
    }

    public boolean isAtoutFireRes() {
        return this.atoutFireRes;
    }

    public Inventory getBagPack() {
        return this.bagPack;
    }

    /**
     * Setter
     */
    public void setFreeze(final boolean state) {
        this.freeze = state;
    }

    public void setVanish(final boolean state) {
        this.vanish = state;
    }

    public void setInvest(final boolean invest) {
        this.invest = invest;
    }

    public void setAtoutHaste(final boolean atoutHaste) {
        this.atoutHaste = atoutHaste;
    }

    public void setAtoutJumpBoost(final boolean atoutJumpBoost) {
        this.atoutJumpBoost = atoutJumpBoost;
    }

    public void setAtoutSpeed(final boolean atoutSpeed) {
        this.atoutSpeed = atoutSpeed;
    }

    public void setAtoutWater(final boolean atoutWater) {
        this.atoutWater = atoutWater;
    }

    public void setAtoutStrenght(final boolean atoutStrenght) {
        this.atoutStrenght = atoutStrenght;
    }

    public void setAtoutFireRes(final boolean atoutFireRes) {
        this.atoutFireRes = atoutFireRes;
    }
}
