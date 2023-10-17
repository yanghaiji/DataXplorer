package com.javayh.agent.common.listener;

import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author haiji
 */
@Slf4j
public class QueueListener<T> implements Runnable {

    private final BlockingQueue<T> queue;

    private final QueueListenerCallback<T> callback;

    public QueueListener(BlockingQueue<T> queue, QueueListenerCallback<T> callback) {
        this.queue = queue;
        this.callback = callback;
    }

    @Override
    public void run() {
        List<T> sendData = new LinkedList<>();
        // 获取一批需要推送的数据
        queue.drainTo(sendData, 30);
        int size = sendData.size();
        log.debug("待推送的数据条数 : {}", queue.size());
        log.debug("本次需要推送数据条数 : {}", size);
        for (T data : sendData) {
            try {
                // 调用回调方法处理数据
                callback.onDataReceived(data);
                log.debug("本次待推送数据条数 : {}", --size);
                TimeUnit.MILLISECONDS.sleep(300);
            } catch (InterruptedException e) {
                log.error("{}", e.getMessage(), e);
                // 处理中断异常，可以选择退出监听器线程
                // 恢复中断状态
                Thread.currentThread().interrupt();
            }
        }

    }
}
