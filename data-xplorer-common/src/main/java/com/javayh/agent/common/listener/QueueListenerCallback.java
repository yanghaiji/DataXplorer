package com.javayh.agent.common.listener;

/**
 * @author haiji
 */
public interface QueueListenerCallback<T> {

    /**
     * 数据发送
     *
     * @param data 待发送的数据
     */
    void onDataReceived(T data);

}
