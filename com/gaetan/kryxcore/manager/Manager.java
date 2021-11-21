package com.gaetan.kryxcore.manager;

public class Manager {
    /**
     * Reference to the ManagerHandler class
     */
    protected ManagerHandler handler;

    /**
     * Constructor for the Manager class.
     *
     * @param handler Reference to the ManagerHandler
     */
    public Manager(final ManagerHandler handler) {
        this.handler = handler;
    }
}
