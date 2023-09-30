package com.javayh.agent.rpc.handler;

import com.javayh.agent.common.bean.LoggerCollector;
import com.javayh.agent.rpc.listener.ChannelListener;
import com.javayh.agent.rpc.network.LoggerAgentClient;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author haiji
 */
@Slf4j
public class AgentClientHandler extends ChannelInboundHandlerAdapter {

    private final LoggerAgentClient loggerAgentClient;

    public AgentClientHandler(LoggerAgentClient loggerAgentClient) {
        this.loggerAgentClient = loggerAgentClient;
    }

    /**
     * 当通道就绪就会触发该方法
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        LoggerCollector sedData = LoggerCollector.builder().appName("内部消息传递，请忽略").ignore(true).build();
        ctx.writeAndFlush(sedData);
    }

    /**
     * 当通道有读取事件时，会触发
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 启动一个新的线程来发送数据
        new ChannelListener().listener(ctx);
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        loggerAgentClient.scheduleReconnect("channel inactive");
        ctx.fireChannelInactive();
    }


}