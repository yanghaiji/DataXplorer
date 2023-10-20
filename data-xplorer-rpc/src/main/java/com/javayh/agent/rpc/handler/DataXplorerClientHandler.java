package com.javayh.agent.rpc.handler;

import com.javayh.agent.common.cache.LoggerSendCache;
import com.javayh.agent.common.configuration.DataXplorerProperties;
import com.javayh.agent.common.executor.AgentExecutor;
import com.javayh.agent.rpc.listener.ChannelListener;
import com.javayh.agent.rpc.network.DataXplorerClient;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

/**
 * @author haiji
 */
@Slf4j
public class DataXplorerClientHandler extends ChannelInboundHandlerAdapter {

    private final DataXplorerClient loggerAgentClient;
    private final DataXplorerProperties dataXplorerProperties;

    private final Boolean showLog;

    public DataXplorerClientHandler(DataXplorerClient loggerAgentClient, DataXplorerProperties dataXplorerProperties) {
        this.loggerAgentClient = loggerAgentClient;
        this.dataXplorerProperties = dataXplorerProperties;
        this.showLog = dataXplorerProperties.getShowLog();
    }


    /**
     * 当通道就绪就会触发该方法
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(LoggerSendCache.build());
        AgentExecutor.shutdown();
        new ChannelListener().listener(ctx, dataXplorerProperties);
    }

    /**
     * 当通道有读取事件时，会触发
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (showLog) {
            log.info("{}", msg);
        }
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("exceptionCaught {}", cause.getMessage(), cause);
        ctx.close();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.error("Channel inactive: {}", ctx.channel());
        loggerAgentClient.scheduleReconnect("channel inactive");
        ctx.fireChannelInactive();
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt)
            throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent idleStateEvent = (IdleStateEvent) evt;
            if (idleStateEvent.state() == IdleState.WRITER_IDLE) {
                log.info("It's been 30 seconds without receiving any messages.");
                // 向服务端发送消息
                ctx.writeAndFlush(LoggerSendCache.build());
            }

        }
        super.userEventTriggered(ctx, evt);
    }

}