package com.javayh.agent.server.listener;

import com.javayh.agent.rpc.network.DataXplorerServer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @author haiji
 */
@Component
public class AgentRunner implements CommandLineRunner {

    private final DataXplorerServer agentServer;

    public AgentRunner(DataXplorerServer agentServer) {
        this.agentServer = agentServer;
    }


    @Override
    public void run(String... args) throws Exception {
        agentServer.start();
    }
}