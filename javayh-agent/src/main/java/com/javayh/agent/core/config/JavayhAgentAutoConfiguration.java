package com.javayh.agent.core.config;

import com.javayh.agent.core.context.AppNamingContext;
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
@Import({SpringInterceptorConfiguration.class, AppNamingContext.class})
public @interface JavayhAgentAutoConfiguration {
}
