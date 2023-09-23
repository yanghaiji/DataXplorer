package com.javayh.agent.core.config;


import com.javayh.agent.core.interceptor.AgentLogFeignInterceptor;
import feign.RequestInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @author haiji
 */
@Slf4j
public class AgentFeignAutoConfiguration {


    @Bean
    @ConditionalOnMissingBean(AgentLogFeignInterceptor.class)
    public RequestInterceptor feignRequestInterceptor() {
        AgentLogFeignInterceptor interceptor = new AgentLogFeignInterceptor();
        log.info("AgentLogFeignInterceptor [{}]", interceptor);
        return interceptor;
    }


}
