package com.javayh.agent.common.factory;

import com.alibaba.fastjson.JSONObject;
import com.google.protobuf.util.Timestamps;
import com.javayh.agent.common.bean.proto.CustomTrackLoggerProto;
import com.javayh.agent.common.bean.proto.LoggerCollectorProto;
import com.javayh.agent.common.bean.proto.MessageTypeProto;
import com.javayh.agent.common.cache.AgentCacheQueue;
import com.javayh.agent.common.constant.LoggerSourceType;
import com.javayh.agent.common.context.AppNamingContext;
import com.javayh.agent.common.context.SpringBeanContext;
import com.javayh.agent.common.context.TraceContext;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.util.Map;
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
    public CustomTrackLoggerProto.CustomTrackLogger createBean(Map<String,Object> parameter, String type, String createBy, Throwable ex) {
        AppNamingContext namingContext = SpringBeanContext.getBean(AppNamingContext.class);
        CustomTrackLoggerProto.CustomTrackLogger.Builder loggerBuilder = CustomTrackLoggerProto.CustomTrackLogger.newBuilder()
                .setAppName(namingContext.getAppNaming())
                .setOperationType(type)
                .setCreateBy(createBy)
                .setMessageType(MessageTypeProto.MessageType.LOGGER_COLLECTOR)
                .setTraceId(TraceContext.getTraceId())
                .setCreateTime(Timestamps.fromMillis(System.currentTimeMillis()));

        // 将 parameter 存入 KeyValues 字段
        if (parameter != null && !parameter.isEmpty()) {

            loggerBuilder.setRequestParameter(JSONObject.toJSONString(parameter));
        }
        // 处理异常信息
        if (Objects.nonNull(ex)) {
            loggerBuilder.setErrorMsg(ExceptionUtils.getStackTrace(ex));
        }
        return loggerBuilder.build();

    }
}
