package com.javayh.agent.common.listener;

import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

/**
 * @author haiji
 */
@Slf4j
public class QueueListener<T> implements Runnable {

    private final BlockingQueue<T> queue;

    private final QueueListenerCallback<T> callback;

    private final Integer dataThroughput;

    private final Boolean showLog;

    public QueueListener(BlockingQueue<T> queue, QueueListenerCallback<T> callback,
                         Integer dataThroughput, Boolean showLog) {
        this.queue = queue;
        this.callback = callback;
        this.dataThroughput = dataThroughput;
        this.showLog = showLog;
    }

    @Override
    public void run() {
        try {
            List<T> sendData = new LinkedList<>();
            // 获取一批需要推送的数据
            queue.drainTo(sendData, dataThroughput);
            int size = sendData.size();
            if (showLog) {
                log.info("number of pending data items : {}", queue.size());
                log.info("the number of data items for this push : {}", size);
            }
            for (T data : sendData) {
                // 调用回调方法处理数据
                callback.onDataReceived(data);
                if (showLog) {
                    log.info("pending data count for this push : {}", --size);
                }
            }
        } catch (Exception e) {
            log.error("{}", e.getMessage(), e);
            // 处理中断异常，可以选择退出监听器线程
            // 恢复中断状态
            Thread.currentThread().interrupt();
        }
    }
}
