package com.javayh.agent.common.executor;

import com.javayh.agent.common.factory.AgentThreadFactory;
import com.javayh.agent.common.handler.AgentRecoveryHandler;

import java.util.Objects;
import java.util.concurrent.*;

/**
 * <p>
 * 线程池
 * </p>
 *
 * @author hai ji
 * @version 1.0.0
 * @since 2023-09-22
 */
public class DataXplorerExecutor {

    private static ScheduledExecutorService executorService;

    public static ScheduledExecutorService singe() {
        if (Objects.isNull(executorService)) {
            executorService = new ScheduledThreadPoolExecutor(2, new AgentThreadFactory(), new AgentRecoveryHandler());
        }
        return executorService;
    }

    public static void shutdown() {
        if (executorService != null && !executorService.isShutdown()) {
            executorService.shutdownNow();
        }
        executorService = null;
    }


    public static ExecutorService executor = new ThreadPoolExecutor(5, 10, 0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<>(), new AgentThreadFactory(),
            new AgentRecoveryHandler());
}
