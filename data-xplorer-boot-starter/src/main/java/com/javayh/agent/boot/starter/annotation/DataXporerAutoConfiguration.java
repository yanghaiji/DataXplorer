package com.javayh.agent.boot.starter.annotation;

import com.javayh.agent.common.configuration.DataXplorerProperties;
import com.javayh.agent.common.context.AppNamingContext;
import com.javayh.agent.common.context.SpringBeanContext;
import com.javayh.agent.common.repository.DefaultLoggerRepository;
import com.javayh.agent.rpc.handler.AgentServerHandler;
import com.javayh.agent.rpc.network.LoggerAgentClient;
import com.javayh.agent.rpc.network.LoggerAgentServer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * <p>
 * 自动配置
 * </p>
 *
 * @author hai ji
 * @version 1.0.0
 * @since 2023-09-22
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@Configuration
@Import({DataXplorerProperties.class, AppNamingContext.class, SpringBeanContext.class,
        LoggerAgentServer.class, LoggerAgentClient.class,
        AgentServerHandler.class, DefaultLoggerRepository.class})
public @interface DataXporerAutoConfiguration {
}
