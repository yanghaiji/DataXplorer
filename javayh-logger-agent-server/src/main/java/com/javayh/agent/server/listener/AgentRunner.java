package com.javayh.agent.server.listener;

import com.javayh.agent.rpc.network.LoggerAgentServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @author haiji
 */
@Component
public class AgentRunner implements CommandLineRunner {

    @Autowired
    private LoggerAgentServer agentServer;


    @Override
    public void run(String... args) throws Exception {
        agentServer.start();
    }
}