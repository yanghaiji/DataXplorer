package com.javayh.agent.core.config;

import com.javayh.agent.common.configuration.DataXplorerProperties;
import com.javayh.agent.common.context.AppNamingContext;
import com.javayh.agent.common.repository.UserInfoRepository;
import com.javayh.agent.common.servlet.AgentChannelFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private final AppNamingContext appNamingContext;
    private final UserInfoRepository userInfoRepository;
    private final DataXplorerProperties dataXplorerProperties;

    public SpringInterceptorConfiguration(AppNamingContext appNamingContext, UserInfoRepository userInfoRepository,
                                          DataXplorerProperties dataXplorerProperties) {
        this.appNamingContext = appNamingContext;
        this.userInfoRepository = userInfoRepository;
        this.dataXplorerProperties = dataXplorerProperties;
    }

    @Bean
    public WebMvcConfiguration addWebInterceptor() {
        if (log.isInfoEnabled()) {
            log.info("Define web interceptors RequestWebInterceptorConfiguration");
        }
        return new WebMvcConfiguration(appNamingContext, userInfoRepository,dataXplorerProperties);
    }

    @Bean
    public AgentChannelFilter channelFilter() {
        return new AgentChannelFilter();
    }


}
