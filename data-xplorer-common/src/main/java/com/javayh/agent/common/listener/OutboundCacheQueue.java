package com.javayh.agent.common.listener;

import com.javayh.agent.common.bean.proto.LoggerCollectorProto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;

import java.util.concurrent.LinkedBlockingDeque;

/**
 * <p>
 * 数据缓存池
 * <p>
 * 用与临时存储数据,以方便批量操作,减少io操作
 * </p>
 *
 * @author hai ji
 * @version 1.0.0
 * @since 2023-09-20
 */
@Slf4j
public class OutboundCacheQueue {

    public static CacheQueue OUTBOUND_CACHE = new CacheQueue();
    public static CacheQueue DB_CACHE = new CacheQueue();


    public static class CacheQueue<T> extends LinkedBlockingDeque<T> {

        /**
         * @param loggerCollector {@link LoggerCollectorProto}
         * @throws NullPointerException if the specified element is null
         */
        @Override
        public boolean offer(@NonNull T loggerCollector) {
            boolean offer = super.offer(loggerCollector);
            log.debug("Current number of cached messages : {}", size());
            return offer;
        }


        @NonNull
        @Override
        public T take() throws InterruptedException {
            T take = super.take();
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
