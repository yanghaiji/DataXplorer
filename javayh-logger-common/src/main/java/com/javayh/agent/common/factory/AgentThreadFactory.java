package com.javayh.agent.common.factory;


import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * 线程的拒绝策略
 *
 * @author haiji
 */
public class AgentThreadFactory implements ThreadFactory {

    private final ThreadFactory defaultFactory = Executors.defaultThreadFactory();

    @Override
    public Thread newThread(Runnable r) {
        Thread thread = defaultFactory.newThread(r);
        thread.setName("AgentThread-" + thread.getId());
        return thread;
    }
}