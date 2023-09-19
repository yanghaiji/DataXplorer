package com.javayh.agent.core.context;

import org.springframework.core.env.Environment;

/**
 * @author 获取当前服务名
 */
public class AppNamingContext {

    private final Environment environment;

    public AppNamingContext(Environment environment) {
        this.environment = environment;
    }

    public String getAppNaming() {
        return environment.getProperty("spring.application.name");
    }
}
