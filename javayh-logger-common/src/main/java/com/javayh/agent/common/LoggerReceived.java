package com.javayh.agent.common;

import com.javayh.agent.common.bean.LoggerCollector;
import com.javayh.agent.common.factory.LoggerFactory;
import lombok.extern.slf4j.Slf4j;

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
     * @param createBy  创建人
     * @param ex        异常
     */
    public static void received(String parameter, Integer type, String createBy, Throwable ex) {
        LoggerCollector collector = new LoggerFactory().createBean(parameter, type, createBy, ex);
        if (Objects.nonNull(collector)) {
            if (log.isDebugEnabled()) {
                log.debug("自定义拦截日志 : {}", collector);
            }
            if (log.isInfoEnabled()) {
                log.debug("自定义拦截日志 : {}", collector);
            }
        }
    }
}