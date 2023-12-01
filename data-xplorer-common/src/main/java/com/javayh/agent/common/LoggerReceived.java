package com.javayh.agent.common;

import com.javayh.agent.common.bean.TrackLogger;
import com.javayh.agent.common.bean.proto.CustomTrackLoggerProto;
import com.javayh.agent.common.factory.LoggerFactory;
import com.javayh.agent.common.handler.OperationHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * 数据发送
 * </p>
 *
 * @author hai ji
 * @version 1.0.0
 * @since 2023-09-21
 */
@Slf4j
public class LoggerReceived {


    /**
     * 自定义埋点日志
     *
     * @param parameter 参数
     * @param type      执行的
     * @param ex        异常
     */
    public static void received(Map<String, Object> parameter, OperationHandler type, Throwable ex) {
        CustomTrackLoggerProto.CustomTrackLogger collector = new LoggerFactory().createBean(parameter, type, ex);
        if (Objects.nonNull(collector)) {
            if (log.isDebugEnabled()) {
                log.debug("LoggerReceived Debug : {}", collector);
            }
        }
    }

    /**
     * 重载实现
     * <p>
     * 方便自定义埋点收集更多的日志参数和异常信息
     *
     * @param logger {@link TrackLogger}
     */
    public static void received(TrackLogger logger) {
        received(logger.getParameter(), logger.getType(), logger.getThrowable());
    }
}