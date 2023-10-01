package com.javayh.agent.rpc.handler;

import com.javayh.agent.common.bean.LoggerCollector;
import com.javayh.agent.common.context.SpringBeanContext;
import com.javayh.agent.common.repository.LoggerRepository;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.Date;

/**
 * 服务端处理器
 *
 * @author haiji
 */
@Slf4j
@ChannelHandler.Sharable
public class AgentServerHandler extends ChannelInboundHandlerAdapter {

    private static final String YMS = "yyyy-MM-dd HH:mm:ss";

    /**
     * 1. ChannelHandlerContext ctx:上下文对象, 含有 管道pipeline , 通道channel, 地址
     * 2. Object msg: 就是客户端发送的数据 默认Object
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {

            if (msg instanceof LoggerCollector && !((LoggerCollector) msg).isIgnore()) {
                // 在这里处理 LoggerCollector 对象
                LoggerRepository loggerRepository = SpringBeanContext.getBean(LoggerRepository.class);
                loggerRepository.save(msg);
            } else if (log.isInfoEnabled()) {
                log.info("客户端发送消息是: {}", msg);
            }
        } catch (Exception e) {
            log.error("channelRead {}", ExceptionUtils.getStackTrace(e));
        }
    }


    /**
     * 数据读取完毕，进行数据回复
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        LoggerCollector sedData = LoggerCollector.builder().appName("内部消息传递，请忽略").ignore(true).build();
        ctx.writeAndFlush(sedData);
    }

    /**
     * 处理异常, 一般是需要关闭通道
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }


    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt)
            throws Exception {
        LoggerCollector sedData = LoggerCollector.builder().appName("内部消息传递，请忽略").ignore(true).build();
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent idleStateEvent = (IdleStateEvent) evt;
            if (idleStateEvent.state() == IdleState.READER_IDLE) {
                log.info("已经5秒没有收到信息！");
                // 向客户端发送消息
                ctx.writeAndFlush(sedData)
                        .addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
            }
        }

    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("服务上线时间为: {}, IP 为:{}", DateFormatUtils.format(new Date(), YMS), ctx.channel().remoteAddress());
    }
}