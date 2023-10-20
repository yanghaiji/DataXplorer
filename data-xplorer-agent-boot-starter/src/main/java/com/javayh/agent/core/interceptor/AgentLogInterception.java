package com.javayh.agent.core.interceptor;


import com.google.protobuf.util.Timestamps;
import com.javayh.agent.common.bean.proto.LoggerCollectorProto;
import com.javayh.agent.common.bean.proto.MessageTypeProto;
import com.javayh.agent.common.cache.AgentCacheQueue;
import com.javayh.agent.common.constant.LoggerSourceType;
import com.javayh.agent.common.constant.LoggerType;
import com.javayh.agent.common.context.AppNamingContext;
import com.javayh.agent.common.context.TraceContext;
import com.javayh.agent.common.servlet.AgentHttpServletRequestWrapper;
import com.javayh.agent.common.utils.IpUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;


/**
 * @author haiji
 */
public class AgentLogInterception implements HandlerInterceptor {

    private final AppNamingContext appNamingContext;

    public AgentLogInterception(AppNamingContext appNamingContext) {
        this.appNamingContext = appNamingContext;
    }

    private static final Logger log = LoggerFactory.getLogger(AgentLogInterception.class);

    private static final ThreadLocal<StopWatch> STOP_WATCH_THREAD_LOCAL = new ThreadLocal<>();
    private static final ThreadLocal<String> BODY = new ThreadLocal<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        AgentHttpServletRequestWrapper requestWrapper = new AgentHttpServletRequestWrapper(request);
        BODY.set(requestWrapper.getBody());
        StopWatch stopWatch = new StopWatch();
        STOP_WATCH_THREAD_LOCAL.set(stopWatch);
        stopWatch.start();
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    /**
     * 结束后结算耗时
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        StopWatch stopWatch = STOP_WATCH_THREAD_LOCAL.get();
        stopWatch.stop();
        String url = request.getRequestURI();
        String query = request.getQueryString();
        String method = request.getMethod();
        String ip = IpUtil.getIpAddr(request);
        String body = BODY.get();
        LoggerCollectorProto.LoggerCollector collector = LoggerCollectorProto.LoggerCollector.newBuilder()
                .setUrl(url)
                .setQuery(reset(query))
                .setBody(reset(body))
                .setIp(ip)
                .setAppName(appNamingContext.getAppNaming())
                .setActionTime(stopWatch.getTime())
                .setType(LoggerType.INTERCEPTOR.value())
                .setMethod(method)
                .setCreateTime(Timestamps.fromMillis(System.currentTimeMillis())).setTraceId(TraceContext.getTraceId())
                .setSourceType(LoggerSourceType.AUTOMATIC.value())
                .setMessageType(MessageTypeProto.MessageType.LOGGER_COLLECTOR)
                // TODO: 2023/9/19 根据实际的项目进行集成
                .setCreateBy("javayh-agent")
                .build();
        if (Objects.nonNull(ex)) {
            collector.toBuilder().setErrorMsg(ExceptionUtils.getStackTrace(ex));
        }
        if (log.isDebugEnabled()) {
            log.debug("agent 拦截日志 : {}", collector);
        }
        if (log.isInfoEnabled()) {
            log.info("agent 拦截日志 : {}", collector);
        }
        STOP_WATCH_THREAD_LOCAL.remove();
        BODY.remove();
        TraceContext.remove();
        AgentCacheQueue.MSG_CACHE_DE.offer(collector);
    }

    private String reset(String value) {
        return StringUtils.isEmpty(value) ? "" : value;
    }


}
