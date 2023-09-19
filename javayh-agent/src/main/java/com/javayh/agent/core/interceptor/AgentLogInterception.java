package com.javayh.agent.core.interceptor;


import com.alibaba.fastjson.JSON;
import com.javayh.agent.core.bean.LoggerCollector;
import com.javayh.agent.core.constant.LoggerType;
import com.javayh.agent.core.context.AppNamingContext;
import com.javayh.agent.core.context.TraceContext;
import com.javayh.agent.core.servlet.AgentHttpServletRequestWrapper;
import com.javayh.agent.core.storage.LogStorageRepository;
import com.javayh.agent.core.utils.IpAddressUtil;
import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;


/**
 * @author haiji
 */
public class AgentLogInterception implements HandlerInterceptor {

    @Resource
    private LogStorageRepository storageRepository;
    private AppNamingContext appNamingContext;

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
        String webName = request.getContextPath();
        String method = request.getMethod();
        String ip = IpAddressUtil.getIpAddr(request);
        String body = BODY.get();
        LoggerCollector collector = LoggerCollector.builder().url(url).query(query).body(body).ip(ip)
                .appName(appNamingContext.getAppNaming())
                .actionTime(stopWatch.getTime())
                .type(LoggerType.INTERCEPTOR.value()).method(method)
                .createTime(new Date()).traceId(TraceContext.getTraceId())
                // TODO: 2023/9/19 根据实际的项目进行集成
                .createBy("javayh-agent")
                .build();
        log.info("埋点日志 : {}", JSON.toJSONString(collector));
        STOP_WATCH_THREAD_LOCAL.remove();
        BODY.remove();
        TraceContext.remove();
    }


}
