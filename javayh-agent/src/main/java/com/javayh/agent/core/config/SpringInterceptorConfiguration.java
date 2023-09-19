package com.javayh.agent.core.config;

import com.javayh.agent.core.context.AppNamingContext;
import com.javayh.agent.core.servlet.AgentChannelFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>
 *
 * </p>
 *
 * @author hai ji
 * @version 1.0.0
 * @since 2022-06-27
 */
@Configuration
@ConditionalOnClass(name = {"org.springframework.web.servlet.config.annotation.WebMvcConfigurer"})
public class SpringInterceptorConfiguration {

    private static final Logger log = LoggerFactory.getLogger(SpringInterceptorConfiguration.class);

    @Autowired
    private AppNamingContext appNamingContext;

    @Bean
    public WebMvcConfiguration addWebInterceptor() {
        if(log.isInfoEnabled()){
            log.info("Define web interceptors RequestWebInterceptorConfiguration");
        }
        return new WebMvcConfiguration(appNamingContext);
    }

    @Bean
    public AgentChannelFilter channelFilter() {
        return new AgentChannelFilter();
    }

//    @Bean(AgentConstant.AGENT_DISPATCHER_SERVLET)
//    @Qualifier(DispatcherServletAutoConfiguration.DEFAULT_DISPATCHER_SERVLET_BEAN_NAME)
//    public DispatcherServlet dispatcherServlet() {
//        return new AgentDispatcherServlet();
//    }

}
