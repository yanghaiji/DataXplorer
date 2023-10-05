package com.javayh.agent.rpc;

import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>
 * 用于储存链接
 * </p>
 *
 * @author Dylan-haiji
 * @version 1.0.0
 * @since 2020-03-10 13:46
 */
public class RpcSocketHolder {

    /**
     * 初始化容器
     */
    private static final Map<String, NioSocketChannel> mapChannel = new ConcurrentHashMap<>(32);

    /**
     * <p>
     * 存入
     * </p>
     *
     * @param id
     * @param socketChannel
     * @return void
     * @version 1.0.0
     * @author Dylan-haiji
     * @since 2020/3/10
     */
    public static void put(String id, NioSocketChannel socketChannel) {
        mapChannel.put(id, socketChannel);
    }

    /**
     * <p>
     * 获取链接
     * </p>
     *
     * @param id
     * @return io.netty.channel.socket.nio.NioSocketChannel
     * @version 1.0.0
     * @author Dylan-haiji
     * @since 2020/3/10
     */
    public static NioSocketChannel get(String id) {
        return mapChannel.get(id);
    }

    /**
     * <p>
     * 获取容器
     * </p>
     *
     * @param
     * @return java.util.Map<java.lang.Long, io.netty.channel.socket.nio.NioSocketChannel>
     * @version 1.0.0
     * @author Dylan-haiji
     * @since 2020/3/10
     */
    public static Map<String, NioSocketChannel> getMapChannel() {
        return mapChannel;
    }

    /**
     * <p>
     * 删除链接
     * </p>
     *
     * @param nioSocketChannel
     * @return void
     * @version 1.0.0
     * @author Dylan-haiji
     * @since 2020/3/10
     */
    public static void remove(NioSocketChannel nioSocketChannel) {
        mapChannel.entrySet().stream()
                .filter(entry -> entry.getValue() == nioSocketChannel)
                .forEach(entry -> mapChannel.remove(entry.getKey()));
    }

}