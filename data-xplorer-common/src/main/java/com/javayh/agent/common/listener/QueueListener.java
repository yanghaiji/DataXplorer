package com.javayh.agent.common.listener;

import java.util.concurrent.BlockingQueue;

/**
 * @author haiji
 */
public class QueueListener<T> implements Runnable {

    private final BlockingQueue<T> queue;

    private final QueueListenerCallback<T> callback;

    public QueueListener(BlockingQueue<T> queue, QueueListenerCallback<T> callback) {
        this.queue = queue;
        this.callback = callback;
    }

    @Override
    public void run() {
        while (true) {
            try {
                // 从队列中获取数据，此处会阻塞等待数据
                T data = queue.take();
                // 调用回调方法处理数据
                callback.onDataReceived(data);
            } catch (InterruptedException e) {
                // 处理中断异常，可以选择退出监听器线程
                // 恢复中断状态
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}
