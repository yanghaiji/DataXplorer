package com.javayh.agent.common.cache;

import com.javayh.agent.common.bean.LoggerCollector;

import java.util.Objects;

/**
 * <p>
 *
 * </p>
 *
 * @author hai ji
 * @version 1.0.0
 * @since 2023-10-02
 */
public class LoggerSendCache {

    private static LoggerCollector sedData;

    public static LoggerCollector build() {
        if (Objects.isNull(sedData)) {
            sedData = LoggerCollector.builder().appName("Internal message transmission, please ignore.").ignore(true).build();
        }
        return sedData;
    }


}
