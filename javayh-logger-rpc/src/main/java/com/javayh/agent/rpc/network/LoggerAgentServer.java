package com.javayh.agent.rpc.network;

import com.javayh.agent.common.configuration.DataXplorerProperties;
import com.javayh.agent.rpc.channel.AgentServerChannelInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PreDestroy;

/**
 * 参考
 * <url>https://github.com/yanghaiji/health-monitor/blob/main/javayh-health-monitor-server/src/main/java/com/javayh/health/monitor/server/server/HeartBeatServer.java</url>
 *
 * @author haiji
 */
@Slf4j
public class LoggerAgentServer {

    private final DataXplorerProperties dataXplorerProperties;

    public LoggerAgentServer(DataXplorerProperties dataXplorerProperties) {
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
                    .option(ChannelOption.SO_BACKLOG, 128)
                    //设置保持活动连接状态
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(new AgentServerChannelInitializer());

            //绑定一个端口并且同步, 生成了一个 ChannelFuture 对象
            //启动服务器(并绑定端口)
            Integer port = dataXplorerProperties.getPort();
            ChannelFuture cf = bootstrap.bind(port).sync();
            //给cf 注册监听器，监控我们关心的事件
            cf.addListener((ChannelFutureListener) future -> {
                if (cf.isSuccess()) {
                    log.info("监听端口 {} 成功", port);
                } else {
                    log.info("监听端口 {} 失败", port);
                }
            });
            //对关闭通道进行监听
            cf.channel().closeFuture().sync();
            log.info(".....javayh logger agent server is ready...");
        } finally {
            bossGroup.shutdownGracefully().syncUninterruptibly();
            workerGroup.shutdownGracefully().syncUninterruptibly();
            log.info("关闭 javayh logger agent server 成功");
        }

    }

}