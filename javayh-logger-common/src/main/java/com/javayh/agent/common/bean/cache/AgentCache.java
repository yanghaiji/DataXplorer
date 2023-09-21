package com.javayh.agent.common.bean.cache;

import java.util.Objects;
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
public class AgentCache {

    public static BlockingQueue<String> queue = new LinkedBlockingDeque<>();

    public static void add(String msg) throws InterruptedException {
        if (Objects.isNull(queue)) {
            queue = new LinkedBlockingDeque<String>();
        }
        queue.offer(msg);
    }

    public static String poll() throws InterruptedException {
        if (Objects.isNull(queue)) {
            queue = new LinkedBlockingDeque<String>();
        }
        return queue.poll();
    }

}
