package com.javayh.agent.core.context;


import org.apache.commons.lang3.StringUtils;

import java.util.UUID;

/**
 * <p>
 * 上下文记录
 * </p>
 *
 * @author hai ji
 * @version 1.0.0
 * @since 2022-06-27
 */
public class TraceContext {

    /**
     * 存储线程的traceId
     */
    private static final ThreadLocal<String> TRANSMITTABLE_THREAD_LOCAL = new ThreadLocal<>();

    /**
     * 如果 ThreadLocal中有traceId就是用否则就生成一个新的
     *
     * @return traceId
     */
    public static String getTraceId() {
        String traceId = TRANSMITTABLE_THREAD_LOCAL.get();
        if (!StringUtils.isNotBlank(traceId)) {
            traceId = UUID.randomUUID().toString();
            TRANSMITTABLE_THREAD_LOCAL.set(traceId);
        }
        return traceId;
    }

    /**
     * 清理 ThreadLocal 中的 traceId
     */
    public static void remove() {
        TRANSMITTABLE_THREAD_LOCAL.remove();
    }
}

