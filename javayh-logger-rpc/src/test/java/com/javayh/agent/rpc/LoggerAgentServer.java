package com.javayh.agent.rpc;

import com.javayh.agent.common.bean.LoggerCollector;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

public class LoggerAgentServer {
    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline()
                                    // 添加对象编码器
                                    .addLast(new ObjectEncoder())
                                    // 添加对象解码器
                                    .addLast(new ObjectDecoder(ClassResolvers.cacheDisabled(null)))
                                    .addLast(new SimpleChannelInboundHandler<LoggerCollector>() {
                                        @Override
                                        protected void channelRead0(ChannelHandlerContext ctx, LoggerCollector msg) throws Exception {
                                            // 处理从客户端接收的LoggerCollector对象
                                            System.out.println("Received LoggerCollector: " + msg);
                                        }
                                    });
                        }
                    });

            ChannelFuture channelFuture = serverBootstrap.bind(8888).sync();

            System.out.println("Server is running on port 8888");

            channelFuture.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
