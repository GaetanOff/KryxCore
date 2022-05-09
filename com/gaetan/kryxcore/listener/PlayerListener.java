package com.gaetan.kryxcore.listener;

import com.gaetan.api.message.Message;
import com.gaetan.api.runnable.TaskUtil;
import com.gaetan.kryxcore.CorePlugin;
import com.gaetan.kryxcore.data.PlayerData;
import com.gaetan.kryxcore.enums.Lang;
import com.gaetan.kryxcore.manager.managers.ReactionManager;
import com.gaetan.kryxcore.manager.managers.StaffManager;
import net.milkbowl.vault.chat.Chat;
import org.bukkit.GameMode;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;

public final class PlayerListener implements Listener {
    /**
     * Reference to the main class
     */
    private final CorePlugin corePlugin;

    /**
     * Reference to the Vault chat api
     */
    public Chat chat;

    /**
     * Constructor for the PlayerListener class.
     *
     * @param corePlugin Reference to te main class
     */
    public PlayerListener(final CorePlugin corePlugin) {
        this.corePlugin = corePlugin;
        this.corePlugin.getServer().getPluginManager().registerEvents(this, this.corePlugin);
        this.chat = this.corePlugin.getServer().getServicesManager().getRegistration(Chat.class).getProvider();
    }

    /**
     * When a player join the server.
     * Note: This will create the custom PlayerData and create the Scoreboard
     * and hide all the vanished players.
     */
    @EventHandler
    public void onJoin(final PlayerJoinEvent event) {
        final Player player = event.getPlayer();
        final PlayerData playerData = new PlayerData(player, this.corePlugin);

        this.corePlugin.getPlayers().put(player, playerData);
        this.corePlugin.getManagerHandler().getStaffManager().load(player);

        //playerData.loadScoreboard();
        playerData.executeJoin();
    }

    /**
     * When a player left the server.
     * Note: This will delete the custom PlayerData
     */
    @EventHandler
    public void onQuit(final PlayerQuitEvent event) {
        this.corePlugin.getPlayers().remove(event.getPlayer()).leave();
    }

    /**
     * When a player talk in the chat.
     * Note: This will listen for the reaction event
     */
    @EventHandler(priority = EventPriority.LOWEST)
    private void onChat(final AsyncPlayerChatEvent event) {
        final ReactionManager reactionManager = this.corePlugin.getManagerHandler().getReactionManager();
        final String message = reactionManager.getCurrentMessage();

        if (message == null || message.isEmpty())
            return;

        if (event.getMessage().equalsIgnoreCase(message)) {
            event.setCancelled(true);
            reactionManager.giveReward(event.getPlayer());
        }
    }

    /**
     * When a player pick up an item.
     * Note: This will cancel the pick up of items for the staffmode
     */
    @EventHandler
    public void onPickUp(final PlayerPickupItemEvent event) {
        if (this.corePlugin.getPlayer(event.getPlayer()) != null && this.corePlugin.getPlayer(event.getPlayer()).isStaffMode())
            event.setCancelled(true);
    }

    /**
     * When a player change world
     */
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onClickInv(final PlayerChangedWorldEvent event) {
        final Player player = event.getPlayer();

        if (this.corePlugin.getPlayer(event.getPlayer()) != null && this.corePlugin.getPlayer(player).isStaffMode())
            TaskUtil.runLater(() -> player.setGameMode(GameMode.CREATIVE), 2L);
    }

    /**
     * When a player drop an item.
     * Note: This will cancel the drop for the staffmode
     */
    @EventHandler
    public void onDrop(final PlayerDropItemEvent event) {
        if (this.corePlugin.getPlayer(event.getPlayer()) != null && this.corePlugin.getPlayer(event.getPlayer()).isStaffMode())
            event.setCancelled(true);
    }

    /**
     * When a player break a block.
     * Note: This will cancel the inventory click for the staffmode
     */
    @EventHandler
    public void onClickInv(final InventoryClickEvent event) {
        final Player player = (Player) event.getWhoClicked();

        if (this.corePlugin.getPlayer(player) != null && this.corePlugin.getPlayer(player).isStaffMode())
            event.setCancelled(true);

        if (event.getClickedInventory() != null && event.getClickedInventory().getTitle().equals(Lang.STAFF_ONLINE.getText()))
            this.corePlugin.getManagerHandler().getPageManager().getPageInventory().executeClickEvent(this.corePlugin, player, event.getSlot(), event);
    }

    /**
     * When a playerinteract on an entity.
     * Note: This will execute all the entity interaction
     */
    @EventHandler
    public void onPlayerInteractOnEntity(final PlayerInteractEntityEvent event) {
        final Player player = event.getPlayer();
        final PlayerData playerData = this.corePlugin.getPlayer(player);

        if (event.getRightClicked() instanceof Player && playerData != null && playerData.isStaffMode()) {
            final StaffManager staffManager = this.corePlugin.getManagerHandler().getStaffManager();
            final Player target = (Player) event.getRightClicked();

            switch (player.getItemInHand().getType()) {
                case BOOK: {
                    staffManager.invSee(player, target);
                    break;
                }
                case ICE: {
                    staffManager.freeze(player, target);
                    break;
                }
            }
        }
    }

    /**
     * When a player interact.
     * Note: This will execute all the interaction
     */
    @EventHandler
    public void onPlayerInteract(final PlayerInteractEvent event) {
        final Player player = event.getPlayer();
        final PlayerData playerData = this.corePlugin.getPlayer(player);
        final ItemStack itemStack = event.getItem();

        if (player.getItemInHand() != null && player.getItemInHand().containsEnchantment(Enchantment.KNOCKBACK)) {
            player.getItemInHand().removeEnchantment(Enchantment.KNOCKBACK);
            Message.tell(player, Lang.KB_DISABLED.getText());
        }

        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.LEFT_CLICK_AIR && event.getItem() != null && event.getItem().getItemMeta() != null && event.getItem().getItemMeta().getDisplayName() != null) {
            final StaffManager staffManager = this.corePlugin.getManagerHandler().getStaffManager();

            switch (player.getItemInHand().getType()) {
                case EYE_OF_ENDER: {
                    if (playerData.isStaffMode()) {
                        event.setCancelled(true);
                        player.updateInventory();
                        staffManager.randomTP(player);
                    }
                    break;
                }
                case INK_SACK: {
                    if (playerData.isStaffMode()) {
                        event.setCancelled(true);
                        staffManager.switchVanish(player);
                    }
                    break;
                }
                case CHEST: {
                    if (playerData.isStaffMode()) {
                        event.setCancelled(true);
                        staffManager.openSLInventory(player);
                    }
                    break;
                }
                case REDSTONE_TORCH_ON: {
                    if (playerData.isStaffMode()) {
                        event.setCancelled(true);
                        player.chat("/mod");
                    }
                    break;
                }
                case EXP_BOTTLE: {
                    if (itemStack.hasItemMeta() && itemStack.getItemMeta().getLore().get(1).contains("Expérience")) {
                        this.corePlugin.getManagerHandler().getBottleXPManager()
                                .useBottle(player, itemStack.getItemMeta().getLore().get(1).replace(Message.YELLOW + "Expérience: ", ""));
                        event.setCancelled(true);
                        break;
                    }
                }
            }
        }
    }

    /**
     * When a player move.
     * Note: This will cancel the movement for frozen players
     */
    @EventHandler
    public void onMove(final PlayerMoveEvent event) {
        if (event.getFrom().getX() != event.getTo().getX() || event.getFrom().getZ() != event.getTo().getZ()) {
            final Player player = event.getPlayer();

            if (this.corePlugin.getPlayer(player).isFreeze()) {
                event.setTo(event.getFrom());
                Message.tell(player, Lang.FREEZE_TARGET_MESSAGE.getText());
            }
        }
    }

    /**
     * When a player respawn
     */
    @EventHandler
    public void onRespawn(final PlayerRespawnEvent event) {
        TaskUtil.runLater(() -> event.getPlayer().teleport(this.corePlugin.getManagerHandler().getConfigManager().getSpawn()), 3L);
    }
}
