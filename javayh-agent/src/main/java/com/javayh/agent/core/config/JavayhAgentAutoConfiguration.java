package com.javayh.agent.core.config;

import com.javayh.agent.common.context.AppNamingContext;
import com.javayh.agent.common.context.SpringBeanContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author ZZ0DFI672
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@Configuration
@Import({SpringInterceptorConfiguration.class, AppNamingContext.class, SpringBeanContext.class})
public @interface JavayhAgentAutoConfiguration {
}
