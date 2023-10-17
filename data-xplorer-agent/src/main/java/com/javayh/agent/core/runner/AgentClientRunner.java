package com.javayh.agent.core.runner;

import com.javayh.agent.rpc.network.DataXplorerClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @author haji
 */
@Component
public class AgentClientRunner implements CommandLineRunner {

    private final DataXplorerClient agentClient;

    public AgentClientRunner(DataXplorerClient agentClient) {
        this.agentClient = agentClient;
    }

    @Override
    public void run(String... args) throws Exception {
        agentClient.init();
        agentClient.connect();
    }
}