package com.javayh.agent.common.factory;

import com.alibaba.fastjson.JSONObject;
import com.google.protobuf.util.Timestamps;
import com.javayh.agent.common.bean.proto.CustomTrackLoggerProto;
import com.javayh.agent.common.bean.proto.LoggerCollectorProto;
import com.javayh.agent.common.bean.proto.MessageTypeProto;
import com.javayh.agent.common.cache.AgentCacheQueue;
import com.javayh.agent.common.context.AppNamingContext;
import com.javayh.agent.common.context.SpringBeanContext;
import com.javayh.agent.common.context.TraceContext;
import com.javayh.agent.common.handler.OperationHandler;
import com.javayh.agent.common.repository.UserInfoRepository;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
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
     * @param ex        异常
     * @return {@link LoggerCollectorProto}
     */
    public CustomTrackLoggerProto.CustomTrackLogger createBean(Map<String, Object> parameter, OperationHandler type, Throwable ex) {
        AppNamingContext namingContext = SpringBeanContext.getBean(AppNamingContext.class);
        UserInfoRepository userInfoRepository = SpringBeanContext.getBean(UserInfoRepository.class);
        // 读取请求中的id，已形成完成的链路闭合环
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String traceId = request.getParameter("TraceId");
        if (StringUtils.isEmpty(traceId)) {
            traceId = request.getHeader("TraceId");
        }
        String userName = userInfoRepository.queryUserName();
        CustomTrackLoggerProto.CustomTrackLogger.Builder loggerBuilder = CustomTrackLoggerProto.CustomTrackLogger.newBuilder()
                .setAppName(namingContext.getAppNaming())
                .setOperationType(type.operation())
                .setCreateBy(userName)
                .setMessageType(MessageTypeProto.MessageType.CUSTOM_TRACK)
                .setTraceId(StringUtils.isNotEmpty(traceId) ? traceId : TraceContext.getTraceId())
                .setCreateTime(Timestamps.fromMillis(System.currentTimeMillis()));
        if (parameter != null && !parameter.isEmpty()) {
            loggerBuilder.setRequestParameter(JSONObject.toJSONString(parameter));
        }
        // 处理异常信息
        if (Objects.nonNull(ex)) {
            loggerBuilder.setErrorMsg(ExceptionUtils.getStackTrace(ex));
        }
        CustomTrackLoggerProto.CustomTrackLogger customTrackLogger = loggerBuilder.build();
        AgentCacheQueue.CUS_TRACK_CACHE_DE.offer(customTrackLogger);
        return customTrackLogger;

    }
}
