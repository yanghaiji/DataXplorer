package com.javayh.agent.rpc;

/**
 * @author haji
 */

public enum MessageType {

    LOGGER_COLLECTOR(0),
    MESSAGE_BODY(1);


    private final int type;

    MessageType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }
}
