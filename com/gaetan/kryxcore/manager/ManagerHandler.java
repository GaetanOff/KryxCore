package com.gaetan.kryxcore.manager;

import com.gaetan.api.inventory.GuiManager;
import com.gaetan.kryxcore.CorePlugin;
import com.gaetan.kryxcore.inventory.AtoutInventory;
import com.gaetan.kryxcore.inventory.ClassementInventory;
import com.gaetan.kryxcore.manager.managers.*;
import com.gaetan.kryxcore.utils.ActionBarUtil;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;

public class ManagerHandler {
    /**
     * Reference to the main class
     */
    private final CorePlugin corePlugin;

    /**
     * Manager reference
     */
    private final ThreadManager threadManager;
    private final ItemManager itemManager;
    private final ConfigManager configManager;
    private final ReactionManager reactionManager;
    private final AutoManager autoManager;
    private final BoardManager boardManager;
    private final BottleXPManager bottleXPManager;
    private final StaffManager staffManager;
    private final VotePartyManager votePartyManager;
    private final RandomTPManager randomTPManager;
    private final DayManager dayManager;
    private final MoneyZoneManager moneyZoneManager;
    private final ClearLagManager clearLagManager;
    private final EventManager eventManager;
    private final PageManager pageManager;
    private final GuiManager guiManager;
    private final ClassementManager classementManager;
    private final PlaceholderManager placeholderManager;

    /**
     * Reference to the Vault Eco api
     */
    private final Economy economy;

    /**
     * Reference to the Vault Chat api
     */
    private final Chat chat;

    /**
     * Reference to the ActionBarUtil api
     */
    private final ActionBarUtil actionBarUtil;

    /**
     * Constructor for the ManagerHandler class.
     *
     * @param corePlugin Reference to the main class
     */
    public ManagerHandler(final CorePlugin corePlugin) {
        this.corePlugin = corePlugin;

        //Thread Manager
        this.threadManager = new ThreadManager(this);

        //Config Manager
        this.configManager = new ConfigManager(this);

        //Item Manager
        this.itemManager = new ItemManager(this);

        //Reaction Manager
        this.reactionManager = new ReactionManager(this);

        //Ayto Manager
        this.autoManager = new AutoManager(this);

        //Board Manager
        this.boardManager = new BoardManager(this);

        //Bottlexp Manager
        this.bottleXPManager = new BottleXPManager(this);

        //Staff Manager
        this.staffManager = new StaffManager(this);

        //VoteParty Manager
        this.votePartyManager = new VotePartyManager(this);

        //RandomTP Manager
        this.randomTPManager = new RandomTPManager(this);

        //Day Manager
        this.dayManager = new DayManager(this);

        //MoneyZone Manager
        this.moneyZoneManager = new MoneyZoneManager(this);

        //ClearLag Manager
        this.clearLagManager = new ClearLagManager(this);

        //Event Manager
        this.eventManager = new EventManager(this);

        //Page Manager
        this.pageManager = new PageManager(this);

        //Classement Manager
        this.classementManager = new ClassementManager(this);

        //Placeholder Manager
        this.placeholderManager = new PlaceholderManager(this);

        //Vault Eco api
        this.economy = this.corePlugin.getServer().getServicesManager().getRegistration(Economy.class).getProvider();

        //Vault Chat api
        this.chat = this.corePlugin.getServer().getServicesManager().getRegistration(Chat.class).getProvider();

        //ActionBar
        this.actionBarUtil = new ActionBarUtil(this.corePlugin);

        //Gui Manager
        this.guiManager = new GuiManager(this.corePlugin);
        this.guiManager.addMenu(new AtoutInventory(this.corePlugin));
        this.guiManager.addMenu(new ClassementInventory(this.corePlugin));
    }

    /**
     * Unload all the managers
     */
    public void unload() {
        this.votePartyManager.saveVoteParty();
    }

    /**
     * Getter to get the CorPlugin reference.
     *
     * @return The reference CorPlugin
     */
    public CorePlugin getCorePlugin() {
        return this.corePlugin;
    }

    /**
     * Getter to get all the manager reference
     *
     * @return The reference to the managers
     */
    public ThreadManager getThreadManager() {
        return this.threadManager;
    }

    public ItemManager getItemManager() {
        return this.itemManager;
    }

    public ConfigManager getConfigManager() {
        return this.configManager;
    }

    public ReactionManager getReactionManager() {
        return this.reactionManager;
    }

    public AutoManager getAutoManager() {
        return this.autoManager;
    }

    public BoardManager getBoardManager() {
        return this.boardManager;
    }

    public BottleXPManager getBottleXPManager() {
        return this.bottleXPManager;
    }

    public StaffManager getStaffManager() {
        return this.staffManager;
    }

    public VotePartyManager getVotePartyManager() {
        return this.votePartyManager;
    }

    public RandomTPManager getRandomTPManager() {
        return this.randomTPManager;
    }

    public DayManager getDayManager() {
        return this.dayManager;
    }

    public MoneyZoneManager getMoneyZoneManager() {
        return this.moneyZoneManager;
    }

    public ClearLagManager getClearLagManager() {
        return this.clearLagManager;
    }

    public EventManager getEventManager() {
        return this.eventManager;
    }

    public PageManager getPageManager() {
        return this.pageManager;
    }

    public GuiManager getGuiManager() {
        return this.guiManager;
    }

    public ClassementManager getClassementManager() {
        return this.classementManager;
    }

    /**
     * Getter to get the Vault Eco api reference.
     *
     * @return The reference Vault Eco api
     */
    public Economy getEconomy() {
        return this.economy;
    }

    /**
     * Getter to get the Vault Chat api reference.
     *
     * @return The reference Vault Chat api
     */
    public Chat getChat() {
        return this.chat;
    }

    /**
     * Getter to get the ActionBarUtil api reference.
     *
     * @return The reference ActionBarUtil api
     */
    public ActionBarUtil getActionBarUtil() {
        return this.actionBarUtil;
    }
}
