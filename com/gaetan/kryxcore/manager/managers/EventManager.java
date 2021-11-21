package com.gaetan.kryxcore.manager.managers;

import com.gaetan.kryxcore.manager.Manager;
import com.gaetan.kryxcore.manager.ManagerHandler;

public final class EventManager extends Manager {

    private boolean event;

    /**
     * Constructor for the EventManager class.
     *
     * @param handler Reference to the ManagerHandler
     */
    public EventManager(final ManagerHandler handler) {
        super(handler);

        this.event = false;
    }

    public boolean isEvent() {
        return this.event;
    }

    public void setEvent(final boolean event) {
        this.event = event;
    }
}
