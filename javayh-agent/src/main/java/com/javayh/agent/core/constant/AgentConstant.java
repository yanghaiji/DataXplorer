package com.javayh.agent.core.constant;

/**
 * <p>
 *常量配置
 * </p>
 *
 * @author hai ji
 * @version 1.0.0
 * @since 2022-06-27
 */
public interface AgentConstant {
    String PACKAGE_NAME = "com.javayh.agent.core.logger.LogbackBytesEnhance";
    String LOGGER_NAME = "ch.qos.logback.classic.Logger";
    String AGENT_DISPATCHER_SERVLET = "agentDispatcherServlet";
    String S_LINE= "/";
    String H_LINE = "-";

    String[] HEADERS_TO_TRY = {
            "X-Forwarded-For",
            "Proxy-Client-IP",
            "WL-Proxy-Client-IP",
            "HTTP_X_FORWARDED_FOR",
            "HTTP_X_FORWARDED",
            "HTTP_X_CLUSTER_CLIENT_IP",
            "HTTP_CLIENT_IP",
            "HTTP_FORWARDED_FOR",
            "HTTP_FORWARDED",
            "HTTP_VIA",
            "REMOTE_ADDR"
    };


}
