package com.javayh.agent.common.executor;

import com.javayh.agent.common.factory.AgentThreadFactory;
import com.javayh.agent.common.handler.AgentRecoveryHandler;

import java.util.Objects;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * <p>
 * 线程池
 * </p>
 *
 * @author hai ji
 * @version 1.0.0
 * @since 2023-09-22
 */
public class AgentExecutor {

    private static ScheduledExecutorService executorService;

    public static ScheduledExecutorService singe() {
        if (Objects.isNull(executorService)) {
            executorService = new ScheduledThreadPoolExecutor(1, new AgentThreadFactory(), new AgentRecoveryHandler());
        }
        return executorService;
    }

}
