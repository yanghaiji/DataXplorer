package com.javayh.agent.core.config;

import com.javayh.agent.common.factory.LoggerFactory;
import com.javayh.agent.core.runner.AgentClientRunner;
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
@Import({SpringInterceptorConfiguration.class, AgentClientRunner.class})
public @interface JavayhAgentAutoConfiguration {
}
