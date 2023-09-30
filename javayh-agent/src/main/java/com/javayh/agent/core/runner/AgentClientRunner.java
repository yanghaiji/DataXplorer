package com.javayh.agent.core.runner;

import com.javayh.agent.rpc.network.LoggerAgentClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @author haji
 */
@Component
public class AgentClientRunner implements CommandLineRunner {

    private final LoggerAgentClient agentClient;

    public AgentClientRunner(LoggerAgentClient agentClient) {
        this.agentClient = agentClient;
    }

    @Override
    public void run(String... args) throws Exception {
        agentClient.init();
        agentClient.connect();
    }
}