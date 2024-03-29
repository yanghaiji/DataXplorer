package com.javayh.agent.core.config;

import cn.hutool.core.util.ReflectUtil;
import com.javayh.agent.common.configuration.DataXplorerProperties;
import com.javayh.agent.common.context.AppNamingContext;
import com.javayh.agent.common.repository.UserInfoRepository;
import com.javayh.agent.core.interceptor.AgentLogInterception;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.lang.reflect.Method;
import java.util.Objects;


/**
 * <p>
 * 自定义拦截器
 * </p>
 *
 * @author hai ji
 * @version 1.0.0
 * @since 2022-06-27
 */
public class WebMvcConfiguration implements WebMvcConfigurer {

    private static final Logger log = LoggerFactory.getLogger(WebMvcConfiguration.class);

    private final AppNamingContext appNamingContext;
    private final UserInfoRepository userInfoRepository;
    private final DataXplorerProperties dataXplorerProperties;

    public WebMvcConfiguration(AppNamingContext appNamingContext, UserInfoRepository userInfoRepository,
                               DataXplorerProperties dataXplorerProperties) {
        this.appNamingContext = appNamingContext;
        this.userInfoRepository = userInfoRepository;
        this.dataXplorerProperties = dataXplorerProperties;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration interceptorRegistration;
        // 拦截的请求路径
        interceptorRegistration = registry.addInterceptor(new AgentLogInterception(appNamingContext, userInfoRepository, dataXplorerProperties))
                .addPathPatterns("/**");
        try {
            if (log.isInfoEnabled()) {
                log.info("耗时拦截器加载成功");
            }
            Method method = ReflectUtil.getMethod(InterceptorRegistration.class, "order", Integer.class);
            if (Objects.nonNull(method)) {
                method.invoke(interceptorRegistration, Ordered.HIGHEST_PRECEDENCE);
            }
        } catch (Exception e) {
            log.error("耗时拦截器加载失败 >>>", e);
        }
    }
}
