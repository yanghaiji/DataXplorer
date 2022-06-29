package com.javayh.agent.core.interceptor;


import com.javayh.agent.core.servlet.AgentHttpServletRequestWrapper;
import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author haiji
 */
public class RequestLogInterception implements HandlerInterceptor {

    private static final Logger log = LoggerFactory.getLogger(RequestLogInterception.class);

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
        String url = request.getRequestURL().toString();
        String query = request.getQueryString();
        String webName = request.getContextPath();
        String clientIp = request.getRemoteAddr();
        String clientHost = request.getRemoteHost();
        String body = BODY.get();
        if (log.isInfoEnabled()){
            log.info("结束URL[{}]的调用,query 参数为: {},body 参数为: {},耗时为:{}毫秒", url, query,body,stopWatch.getTime());
        }
        STOP_WATCH_THREAD_LOCAL.remove();
        BODY.remove();
    }


}
