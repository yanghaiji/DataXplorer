package com.javayh.agent.server.listener;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.IService;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * 持久化监听器
 * 方边统一的输出,进行诗酒花
 *
 * @author haiji
 */
@Slf4j
public class OutboundQueueListener<T> implements Runnable {

    private final BlockingQueue<T> queue;

    private final Integer dataThroughput;

    private final IService<T> iService;

    public OutboundQueueListener(BlockingQueue<T> queue, Integer dataThroughput, IService<T> iService) {
        this.queue = queue;
        this.dataThroughput = dataThroughput;
        this.iService = iService;
    }

    @Override
    public void run() {
        try {
            LinkedBlockingDeque<T> sendData = new LinkedBlockingDeque<>();
            // 获取一批需要推送的数据
            queue.drainTo(sendData, calculateDataToProcess());
            if (CollectionUtils.isNotEmpty(sendData)) {
                iService.saveBatch(sendData);
            }

        } catch (Exception e) {
            log.error("{}", e.getMessage(), e);
            // 处理中断异常，可以选择退出监听器线程
            // 恢复中断状态
            Thread.currentThread().interrupt();
        }
    }


    private int calculateDataToProcess() {
        // 根据队列长度和dataThroughput来计算需要处理的数据条数
        int queueSize = queue.size();
        return Math.min(queueSize, dataThroughput);
    }
}
