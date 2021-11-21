package com.gaetan.kryxcore.runnable.multithreading;

import com.gaetan.api.message.Message;

import java.util.List;

public final class AutoRunnable implements Runnable {
    /**
     * Auto Message messages
     */
    private final List<String> messageList;

    /**
     * Counter
     */
    private int counter;

    /**
     * Constructor for the AutoRunnable runnable.
     *
     * @param messageList Auto Message messages
     */
    public AutoRunnable(final List<String> messageList) {
        this.messageList = messageList;
    }

    /**
     * Auto-Message
     */
    @Override
    public void run() {
        Message.tellToEveryone(Message.tl(this.messageList.get(this.counter).replace("\n", "\n")));

        this.counter = this.counter == this.messageList.size() - 1 ? 0 : this.counter + 1;
    }
}
