package com.javayh.agent.rpc;

import com.javayh.agent.common.bean.LoggerCollector;
import com.javayh.agent.common.exception.ChannelListenerException;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

public class LoggerAgentClient {
    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();

            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
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

                                            ctx.channel().eventLoop().execute(() -> {
                                                try {

                                                    ctx.writeAndFlush(LoggerCollector.builder().ip("tyu").appName("tyu").id(2345678L).sourceType(1).actionTime(2L).method("yui").build());
                                                    //ctx.writeAndFlush(Unpooled.copiedBuffer(JSONObject.toJSONString(data), CharsetUtil.UTF_8));
                                                } catch (Exception ex) {
                                                    ex.printStackTrace();
                                                    throw new ChannelListenerException(ex);
                                                }
                                            });

                                        }
                                    });
                        }
                    });

            ChannelFuture channelFuture = bootstrap.connect("localhost", 8888).sync();
            final LoggerCollector loggerCollector1 = LoggerCollector.builder().id(2113L).appName("dfghjk").build();
            channelFuture.channel().writeAndFlush(loggerCollector1);
            // 等待直到连接关闭
            channelFuture.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }
    }
}
