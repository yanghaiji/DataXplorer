package com.javayh.agent.common.handler;

import com.javayh.agent.common.executor.task.AgentTask;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author haiji
 */
@Slf4j
public class AgentRecoveryHandler implements RejectedExecutionHandler {

    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        log.error("Task rejected. Creating a new thread to execute it.");
        new AgentTask(r).start();
    }

}


