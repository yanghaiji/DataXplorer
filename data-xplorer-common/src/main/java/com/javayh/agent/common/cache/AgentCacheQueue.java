package com.javayh.agent.common.cache;

import com.javayh.agent.common.bean.proto.CustomTrackLoggerProto;
import com.javayh.agent.common.bean.proto.LoggerCollectorProto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;

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
    public static CacheQueue<CustomTrackLoggerProto.CustomTrackLogger> CUS_TRACK_CACHE_DE = new CacheQueue<>();


    public static class CacheQueue<L> extends LinkedBlockingDeque<L> {

        /**
         * @param loggerCollector {@link LoggerCollectorProto}
         * @throws NullPointerException if the specified element is null
         */
        @Override
        public boolean offer(@NonNull L loggerCollector) {
            boolean offer = super.offer(loggerCollector);
            log.debug("Current number of cached messages : {}", size());
            return offer;
        }


        @NonNull
        @Override
        public L take() throws InterruptedException {
            L take = super.take();
            log.debug("Current number of cached messages : {}", size());
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
