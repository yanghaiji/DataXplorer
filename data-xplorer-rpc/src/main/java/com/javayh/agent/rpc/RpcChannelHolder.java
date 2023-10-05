package com.javayh.agent.rpc;

import io.netty.channel.ChannelHandlerContext;

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
public class RpcChannelHolder {

    /**
     * 初始化容器
     */
    private static final Map<String, ChannelHandlerContext> mapChannel = new ConcurrentHashMap<>(32);

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
    public static void put(String id, ChannelHandlerContext socketChannel) {
        mapChannel.put(id, socketChannel);
    }

    /**
     * <p>
     * 获取链接
     * </p>
     *
     * @param id
     * @return io.netty.channel.socket.nio.ChannelHandlerContext
     * @version 1.0.0
     * @author Dylan-haiji
     * @since 2020/3/10
     */
    public static ChannelHandlerContext get(String id) {
        return mapChannel.get(id);
    }

    /**
     * <p>
     * 获取容器
     * </p>
     *
     * @param
     * @return java.util.Map<java.lang.Long, io.netty.channel.socket.nio.ChannelHandlerContext>
     * @version 1.0.0
     * @author Dylan-haiji
     * @since 2020/3/10
     */
    public static Map<String, ChannelHandlerContext> getMapChannel() {
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
    public static void remove(ChannelHandlerContext nioSocketChannel) {
        mapChannel.entrySet().stream()
                .filter(entry -> entry.getValue() == nioSocketChannel)
                .forEach(entry -> mapChannel.remove(entry.getKey()));
    }

}