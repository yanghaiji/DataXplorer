package com.javayh.agent.common.factory;

import com.javayh.agent.common.bean.LoggerCollector;
import com.javayh.agent.common.cache.AgentCacheQueue;
import com.javayh.agent.common.constant.LoggerSourceType;
import com.javayh.agent.common.constant.LoggerType;
import com.javayh.agent.common.context.AppNamingContext;
import com.javayh.agent.common.context.SpringBeanContext;
import com.javayh.agent.common.context.TraceContext;
import com.javayh.agent.common.utils.IpUtil;
import org.apache.commons.lang3.exception.ExceptionUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
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
     * @return {@link LoggerCollector}
     */
    public LoggerCollector createBean(String parameter, Integer type, String createBy, Throwable ex) {
        AppNamingContext namingContext = SpringBeanContext.getBean(AppNamingContext.class);
        //HttpServletRequest servletRequest = SpringBeanContext.getBean(HttpServletRequest.class);
//        LoggerCollector loggerCollector = LoggerCollector.builder().query(parameter).body(parameter)
//                .type(LoggerType.INTERCEPTOR.value()).type(type)
//                .appName(namingContext.getAppNaming())
////                .ip(IpUtil.getIpAddr(servletRequest))
//                .createTime(new Date()).traceId(TraceContext.getTraceId())
//                .sourceType(LoggerSourceType.AUTOMATIC.value())
//                .createBy(createBy)
//                .build();
//        if (Objects.nonNull(ex)) {
//            loggerCollector.setErrorMsg(ExceptionUtils.getStackTrace(ex));
//        }
//        AgentCacheQueue.MSG_CACHE_DE.offer(loggerCollector);
        return null;
    }


}
