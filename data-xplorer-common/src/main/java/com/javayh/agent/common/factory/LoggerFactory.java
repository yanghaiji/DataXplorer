package com.javayh.agent.common.factory;

import com.javayh.agent.common.bean.proto.LoggerCollectorProto;
import com.javayh.agent.common.bean.proto.MessageTypeProto;
import com.javayh.agent.common.cache.AgentCacheQueue;
import com.javayh.agent.common.constant.LoggerSourceType;
import com.javayh.agent.common.context.AppNamingContext;
import com.javayh.agent.common.context.SpringBeanContext;
import com.javayh.agent.common.context.TraceContext;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.util.Objects;

/**
 * <p>
 * 自定义埋点的收集工厂
 * </p>
 *
 * @author hai ji
 * @version 1.0.0
 * @since 2023-09-21
 */
public class LoggerFactory {

    /**
     * 创建统一的日志收集器
     *
     * @param parameter 参数
     * @param type      执行的
     * @param createBy  创建人
     * @param ex        异常
     * @return {@link LoggerCollectorProto}
     */
    public LoggerCollectorProto.LoggerCollector createBean(String parameter, Integer type, String createBy, Throwable ex) {
        AppNamingContext namingContext = SpringBeanContext.getBean(AppNamingContext.class);
        LoggerCollectorProto.LoggerCollector loggerCollector = LoggerCollectorProto.LoggerCollector.newBuilder()
                .setQuery(parameter)
                .setBody(parameter)
                .setType(type)
                .setCreateBy(createBy)
                .setAppName(namingContext.getAppNaming())
                .setMessageType(MessageTypeProto.MessageType.LOGGER_COLLECTOR)
                .setTraceId(TraceContext.getTraceId())
                .setSourceType(LoggerSourceType.AUTOMATIC.value())
                .build();

        if (Objects.nonNull(ex)) {
            loggerCollector.toBuilder().setErrorMsg(ExceptionUtils.getStackTrace(ex));
        }
        AgentCacheQueue.MSG_CACHE_DE.offer(loggerCollector);
        return loggerCollector;
    }


}
