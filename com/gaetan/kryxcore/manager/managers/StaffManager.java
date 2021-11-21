package com.gaetan.kryxcore.manager.managers;

import com.gaetan.api.RandomUtil;
import com.gaetan.api.message.Message;
import com.gaetan.api.runnable.TaskUtil;
import com.gaetan.kryxcore.CorePlugin;
import com.gaetan.kryxcore.data.PlayerData;
import com.gaetan.kryxcore.enums.Lang;
import com.gaetan.kryxcore.manager.Manager;
import com.gaetan.kryxcore.manager.ManagerHandler;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public final class StaffManager extends Manager {
    /**
     * Reference to the main class
     */
    private final CorePlugin corePlugin;

    /**
     * Constructor for the StaffManager class.
     *
     * @param handler Reference to the ManagerHandler
     */
    public StaffManager(final ManagerHandler handler) {
        super(handler);

        this.corePlugin = this.handler.getCorePlugin();
    }

    /**
     * Load the data when the player join.
     * Note: This will hide all the vanished players.
     *
     * @param player The player
     */
    public void load(final Player player) {
        this.corePlugin.getServer().getOnlinePlayers().stream()
                .filter(players -> this.corePlugin.getPlayer(players) != null)
                .filter(players -> this.corePlugin.getPlayer(players).isVanish())
                .forEach(player::hidePlayer);
    }

    /**
     * Switch the StaffMode status of the player
     *
     * @param player The player
     */
    public void switchMod(final Player player) {
        final PlayerData playerData = this.corePlugin.getPlayer(player);

        playerData.setStaffMode(!playerData.isStaffMode());
    }

    /**
     * Switch the Freeze status of the player
     *
     * @param player The owner of the freeze
     * @param target The target who will be frozen
     */
    public void switchFreeze(final Player player, final Player target) {
        this.handler.getThreadManager().getThreadPool().execute(() -> {
            final PlayerData targetData = this.corePlugin.getPlayer(target);

            targetData.setFreeze(!targetData.isFreeze());
            Message.tell(player,
                    Lang.FREEZE_MESSAGE.getText()
                            .replace("%freeze%", targetData.isFreeze() ? "freeze" : "unfreeze")
                            .replace("%s", target.getName())
            );
        });
    }

    /**
     * See the inventory of the target
     *
     * @param player The player will see the inventory
     * @param target The target
     */
    public void invSee(final Player player, final Player target) {
        player.openInventory(target.getInventory());
    }

    /**
     * Freeze the target
     *
     * @param player The owner of the freeze
     * @param target The target who will be frozen
     */
    public void freeze(final Player player, final Player target) {
        player.chat("/freeze " + target.getName());
    }

    /**
     * Teleport player to a random player
     *
     * @param player The player
     */
    public void randomTP(final Player player) {
        this.handler.getThreadManager().getThreadPool().execute(() -> {
            final ArrayList<Player> players = new ArrayList<>();

            this.corePlugin.getServer().getOnlinePlayers().stream()
                    .filter(player1 -> player1 != player)
                    .filter(player1 -> this.corePlugin.getPlayer(player1) != null)
                    .forEach(players::add);

            if (players.isEmpty()) {
                Message.tell(player, Lang.NOBODY_ONLINE.getText());
                return;
            }

            //Sync cuz we are not on the main thread before !
            TaskUtil.run(() -> player.chat("/tp " + players.get(RandomUtil.nextInt(players.size())).getName()));
        });
    }

    /**
     * Switch the vanish of the player
     *
     * @param player The player
     */
    public void switchVanish(final Player player) {
        final PlayerData playerData = this.corePlugin.getPlayer(player);

        playerData.setVanish(!playerData.isVanish());
        playerData.updateVanish();
    }

    /**
     * Open the StaffList inventory
     *
     * @param player The player
     */
    public void openSLInventory(final Player player) {
        this.corePlugin.getManagerHandler().getPageManager().setPageInventory();
        this.corePlugin.getManagerHandler().getPageManager().getPageInventory().openInventory(player);
        player.updateInventory();
    }
}
