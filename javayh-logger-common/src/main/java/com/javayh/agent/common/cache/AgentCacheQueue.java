package com.javayh.agent.common.cache;

import com.javayh.agent.common.bean.LoggerCollector;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * <p>
 * 数据缓存池
 * </p>
 *
 * @author hai ji
 * @version 1.0.0
 * @since 2023-09-20
 */
public class AgentCacheQueue {

    public static BlockingQueue<LoggerCollector> MSG_CACHE_DE = new LinkedBlockingDeque<>();

}
