package com.javayh.agent.common.cache;

import com.javayh.agent.common.bean.proto.LoggerCollectorProto;
import lombok.extern.slf4j.Slf4j;

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
@Slf4j
public class AgentCacheQueue {

    public static CacheQueue<LoggerCollectorProto.LoggerCollector> MSG_CACHE_DE = new CacheQueue<>();


    public static class CacheQueue<L extends LoggerCollectorProto.LoggerCollector> extends LinkedBlockingDeque<LoggerCollectorProto.LoggerCollector> {

        /**
         * @param loggerCollector {@link LoggerCollectorProto}
         * @throws NullPointerException if the specified element is null
         */
        @Override
        public boolean offer(LoggerCollectorProto.LoggerCollector loggerCollector) {
            boolean offer = super.offer(loggerCollector);
            log.debug("当前缓存的消息数量 : {}", size());
            return offer;
        }


        @Override
        public LoggerCollectorProto.LoggerCollector take() throws InterruptedException {
            LoggerCollectorProto.LoggerCollector take = super.take();
            log.debug("当前缓存的消息数量 : {}", size());
            return take;
        }

        /**
         * Returns the number of elements in this deque.
         *
         * @return the number of elements in this deque
         */
        @Override
        public int size() {
            return super.size();
        }
    }

}
