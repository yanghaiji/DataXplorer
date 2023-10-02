package com.javayh.agent.rpc;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>
 * 在线服务
 * </p>
 *
 * @author Dylan-haiji
 * @version 1.0.0
 * @since 2020-03-10 13:46
 */
public class OnlineServiceHolder {

    /**
     * 初始化容器
     */
    private static final Map<String, Integer> ONLINE_SERVICE = new ConcurrentHashMap<>(32);


    public static void put(String id) {
        if (ONLINE_SERVICE.containsKey(id)) {
            Integer integer = ONLINE_SERVICE.get(id);
            ONLINE_SERVICE.put(id, integer + 1);
        } else {
            ONLINE_SERVICE.put(id, 1);
        }

    }


    public static Integer get(String id) {
        return ONLINE_SERVICE.get(id);
    }


    public static Map<String, Integer> getOnlineService() {
        return ONLINE_SERVICE;
    }


    public static void remove(String key) {
        ONLINE_SERVICE.forEach((k, v) -> {
            if (k.equals(key)) {
                Integer count = ONLINE_SERVICE.get(key);
                ONLINE_SERVICE.put(key, count - 1);
            }
        });

    }

}