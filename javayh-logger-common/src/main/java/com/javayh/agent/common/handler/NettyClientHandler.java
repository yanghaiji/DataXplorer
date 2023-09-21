package com.javayh.agent.common.handler;

import com.javayh.agent.common.bean.cache.AgentCache;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NettyClientHandler extends ChannelInboundHandlerAdapter {

    /**
     * 当通道就绪就会触发该方法
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(Unpooled.copiedBuffer("javayh logger agent ", CharsetUtil.UTF_8));
    }

    /**
     * 当通道有读取事件时，会触发
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 启动一个新的线程来发送数据
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(() -> {
            while (true) {
                try {
                    ctx.channel().eventLoop().execute(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                String poll = AgentCache.queue.poll();
                                if (Objects.nonNull(poll)) {
                                    ctx.writeAndFlush(Unpooled.copiedBuffer(poll, CharsetUtil.UTF_8));
                                    System.out.println("channel code=" + ctx.channel().hashCode());
                                }
                            } catch (Exception ex) {
                                System.out.println("发生异常" + ex.getMessage());
                            }
                        }
                    });
                } catch (Exception e) {
                    // 处理异常，可以记录日志或采取其他措施
                    e.printStackTrace();
                }
            }
        });
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}