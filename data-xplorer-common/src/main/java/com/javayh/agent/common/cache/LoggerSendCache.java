package com.javayh.agent.common.cache;


import com.javayh.agent.common.bean.proto.LoggerCollectorProto;
import com.javayh.agent.common.bean.proto.MessageTypeProto;

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

    private static LoggerCollectorProto.LoggerCollector sedData;

    public static LoggerCollectorProto.LoggerCollector build() {
        if (Objects.isNull(sedData)) {
            sedData = LoggerCollectorProto.LoggerCollector.newBuilder()
                    .setId(1L)
                    .setMessageType(MessageTypeProto.MessageType.LOGGER_COLLECTOR)
                    .setTraceId("1").setErrorMsg("Internal message transmission, please ignore.").setIgnore(true).build();
        }
        return sedData;
    }


}
