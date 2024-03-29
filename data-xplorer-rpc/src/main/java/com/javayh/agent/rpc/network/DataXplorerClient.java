package com.javayh.agent.rpc.network;

import com.javayh.agent.common.configuration.DataXplorerProperties;
import com.javayh.agent.rpc.channel.DataXplorerChannelInitializer;
import com.javayh.agent.rpc.listener.ConnectionListener;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.TimeUnit;

/**
 * 客户端
 *
 * @author haiji
 */
@Slf4j
public class DataXplorerClient {

    @Autowired
    private DataXplorerProperties dataXplorerProperties;
    private Bootstrap bootstrap;
    private EventLoopGroup group;


    public void init() {
        //客户端需要一个事件循环组
        group = new NioEventLoopGroup();
        //创建客户端启动对象
        // bootstrap 可重用, 只需在NettyClient实例化的时候初始化即可.
        bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, true)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000)
                .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                .option(ChannelOption.WRITE_BUFFER_HIGH_WATER_MARK, 64 * 1024)
                .option(ChannelOption.WRITE_BUFFER_LOW_WATER_MARK, 32 * 1024)
                .option(ChannelOption.RCVBUF_ALLOCATOR, AdaptiveRecvByteBufAllocator.DEFAULT)
                .option(ChannelOption.MESSAGE_SIZE_ESTIMATOR, DefaultMessageSizeEstimator.DEFAULT)
                .option(ChannelOption.ALLOW_HALF_CLOSURE, false)
                .option(ChannelOption.SO_SNDBUF, 1048576)
                .option(ChannelOption.SO_RCVBUF, 1048576)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.TCP_NODELAY, true)
                .option(ChannelOption.WRITE_BUFFER_WATER_MARK, new WriteBufferWaterMark(32 * 1024, 64 * 1024))
                .handler(new LoggingHandler(LogLevel.INFO))
                .handler(new DataXplorerChannelInitializer(DataXplorerClient.this, dataXplorerProperties));

    }

    public void connect() {
        //启动客户端去连接服务器端
        //关于 ChannelFuture 要分析，涉及到netty的异步模型
        ChannelFuture channelFuture = bootstrap.connect(dataXplorerProperties.getHost(), dataXplorerProperties.getPort())
                .addListener(new ConnectionListener(DataXplorerClient.this));

        log.info("DataXplorer continuously serving you");
        //给关闭通道进行监听
        try {
            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            log.error("DataXplorerClient {}", e.getMessage(), e);
            channelFuture.channel().closeFuture();
        }
    }


    public void scheduleReconnect(String msg) {
        log.error(msg);
        // 重连逻辑
        group.schedule(this::connect, 10, TimeUnit.SECONDS);
    }


}