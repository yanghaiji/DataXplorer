package com.javayh.agent.rpc.network;

import com.javayh.agent.common.configuration.DataXplorerProperties;
import com.javayh.agent.rpc.channel.AgentServerChannelInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * 参考
 * <url>https://github.com/yanghaiji/health-monitor/blob/main/javayh-health-monitor-server/src/main/java/com/javayh/health/monitor/server/server/HeartBeatServer.java</url>
 *
 * @author haiji
 */
@Slf4j
public class DataXplorerServer {

    private final DataXplorerProperties dataXplorerProperties;

    public DataXplorerServer(DataXplorerProperties dataXplorerProperties) {
        this.dataXplorerProperties = dataXplorerProperties;
    }

    public void start() throws Exception {
        final EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        final EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            //创建服务器端的启动对象，配置参数
            ServerBootstrap bootstrap = new ServerBootstrap();
            //使用链式编程来进行设置
            //设置两个线程组
            bootstrap.group(bossGroup, workerGroup)
                    //使用NioSocketChannel 作为服务器的通道实现
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .option(ChannelOption.SO_LINGER, 0)
                    .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000)
                    .option(ChannelOption.SO_RCVBUF, 1048576)
                    .option(ChannelOption.SO_SNDBUF, 1048576)
                    .option(ChannelOption.WRITE_BUFFER_WATER_MARK, new WriteBufferWaterMark(8 * 1024, 32 * 1024))
                    .option(ChannelOption.RCVBUF_ALLOCATOR, AdaptiveRecvByteBufAllocator.DEFAULT)
                    .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new AgentServerChannelInitializer(dataXplorerProperties));

            //绑定一个端口并且同步, 生成了一个 ChannelFuture 对象
            //启动服务器(并绑定端口)
            Integer port = dataXplorerProperties.getPort();
            String host = dataXplorerProperties.getHost();
            ChannelFuture cf = bootstrap.bind(host, port).sync();
            //给cf 注册监听器，监控我们关心的事件
            cf.addListener((ChannelFutureListener) future -> {
                if (cf.isSuccess()) {
                    log.info("服务监听成功 host : {} , port : {}", host, port);
                } else {
                    log.info("服务监听失败 host : {} , port : {}", host, port);
                }
            });
            //对关闭通道进行监听
            cf.channel().closeFuture().sync();
            log.info("DataXplorer server is ready...");
        } finally {
            bossGroup.shutdownGracefully().syncUninterruptibly();
            workerGroup.shutdownGracefully().syncUninterruptibly();
            log.info("关闭 DataXplorer server 成功");
        }

    }

}