package com.javayh.agent.rpc.network;

import com.javayh.agent.common.configuration.DataXplorerProperties;
import com.javayh.agent.rpc.channel.AgentChannelInitializer;
import com.javayh.agent.rpc.listener.ConnectionListener;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
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
    private Channel channel;


    public void init() {
        //客户端需要一个事件循环组
        group = new NioEventLoopGroup();
        //创建客户端启动对象
        // bootstrap 可重用, 只需在NettyClient实例化的时候初始化即可.
        bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .handler(new LoggingHandler(LogLevel.INFO))
                .handler(new AgentChannelInitializer(DataXplorerClient.this, dataXplorerProperties));

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
        // 在重连之前，确保关闭旧的连接
        if (channel != null) {
            channel.close();
        }
        log.error(msg);
        // 重连逻辑
        group.schedule(this::connect, 10, TimeUnit.SECONDS);
    }


}