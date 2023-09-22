package com.javayh.agent.core.logger;


import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.LoggingEvent;
import cn.hutool.core.util.StrUtil;
import com.javayh.agent.common.context.TraceContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Marker;


/**
 * <p>
 *
 * </p>
 *
 * @author hai ji
 * @version 1.0.0
 * @since 2022-06-27
 */
@Slf4j
public class LogbackBytesEnhance {


    /**
     * 增强ch.qos.logback.classic.Logger的buildLoggingEventAndAppend方法
     *
     * @param fqcn
     * @param marker
     * @param level
     * @param msg
     * @param params
     * @param t
     * @param logger
     */
    public static void enhance(final String fqcn, final Marker marker, final Level level,
                               final String msg, final Object[] params,
                               final Throwable t, Logger logger) {
        // 日志内容
        String resultLog;
        // 生成traceId
        if (log.isTraceEnabled() && StringUtils.isNotBlank(TraceContext.getTraceId())) {
            // 将traceId和日志内容拼接，这里是重点，traceId正是在这里生成并拼接到日志内容中
            resultLog = StrUtil.format("{} {}", TraceContext.getTraceId(), msg);
        } else {
            resultLog = msg;
        }
        // 增强buildLoggingEventAndAppend
        LoggingEvent loggingEvent = new LoggingEvent(fqcn, logger, level, resultLog, t, params);
        loggingEvent.setMarker(marker);
        logger.callAppenders(loggingEvent);
    }
}
