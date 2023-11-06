package com.javayh.agent.boot.starter.annotation;

import com.javayh.agent.common.DatabaseService;
import com.javayh.agent.common.configuration.DataXplorerProperties;
import com.javayh.agent.common.context.AppNamingContext;
import com.javayh.agent.common.context.SpringBeanContext;
import com.javayh.agent.common.repository.DefaultDataStreamSink;
import com.javayh.agent.common.repository.DefaultUserInfoRepository;
import com.javayh.agent.rpc.handler.DataXplorerServerHandler;
import com.javayh.agent.rpc.network.DataXplorerClient;
import com.javayh.agent.rpc.network.DataXplorerServer;
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
        DataXplorerServer.class, DataXplorerClient.class,
        DataXplorerServerHandler.class, DefaultDataStreamSink.class, DatabaseService.class,
        DefaultUserInfoRepository.class})
public @interface DataXporerAutoConfiguration {
}
