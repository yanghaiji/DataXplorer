package com.javayh.agent.rpc.handler;

import com.javayh.agent.common.bean.proto.LoggerCollectorProto;
import com.javayh.agent.common.bean.proto.MessageBodyProto;
import com.javayh.agent.common.cache.LoggerSendCache;
import com.javayh.agent.common.configuration.DataXplorerProperties;
import com.javayh.agent.common.context.SpringBeanContext;
import com.javayh.agent.common.repository.LoggerRepository;
import com.javayh.agent.rpc.OnlineServiceHolder;
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

    private final String appName;

    public AgentServerHandler(DataXplorerProperties dataXplorerProperties) {
        this.appName = dataXplorerProperties.getAppName();
    }

    /**
     * 1. ChannelHandlerContext ctx:上下文对象, 含有 管道pipeline , 通道channel, 地址
     * 2. Object msg: 就是客户端发送的数据 默认Object
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {

            if (msg instanceof LoggerCollectorProto.LoggerCollector && !((LoggerCollectorProto.LoggerCollector) msg).getIgnore()) {
                // 在这里处理 LoggerCollector 对象
                LoggerRepository loggerRepository = SpringBeanContext.getBean(LoggerRepository.class);
                loggerRepository.save(msg);
            } else if (msg instanceof MessageBodyProto.MessageBody) {
                String name = ((MessageBodyProto.MessageBody) msg).getAppName();
                OnlineServiceHolder.put(name);
            } else if (log.isInfoEnabled()) {
                log.info("{}", msg);
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
        ctx.writeAndFlush(LoggerSendCache.build());
    }

    /**
     * 处理异常, 一般是需要关闭通道
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("exceptionCaught {}", cause.getMessage(), cause);
        ctx.close();
    }


    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt)
            throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent idleStateEvent = (IdleStateEvent) evt;
            if (idleStateEvent.state() == IdleState.READER_IDLE) {
                log.info("It's been 30 seconds without receiving any messages.");
                // 向客户端发送消息
                ctx.writeAndFlush(LoggerSendCache.build())
                        .addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
            }
        }

    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("服务上线时间为: {}, IP 为:{}", DateFormatUtils.format(new Date(), YMS), ctx.channel().remoteAddress());
    }
}
