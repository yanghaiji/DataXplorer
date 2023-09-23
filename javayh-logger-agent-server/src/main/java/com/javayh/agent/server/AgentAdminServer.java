package com.javayh.agent.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * <p>
 * 日志收集
 * </p>
 *
 * @author hai ji
 * @version 1.0.0
 * @since 2023-09-20
 */
@SpringBootApplication
public class AgentAdminServer {

    public static void main(String[] args) {
        SpringApplication.run(AgentAdminServer.class, args);
    }

}
