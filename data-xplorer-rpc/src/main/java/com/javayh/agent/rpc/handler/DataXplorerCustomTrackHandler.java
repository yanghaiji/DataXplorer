package com.javayh.agent.rpc.handler;

import com.javayh.agent.common.bean.proto.CustomTrackLoggerProto;
import com.javayh.agent.common.bean.proto.LoggerCollectorProto;
import com.javayh.agent.common.cache.LoggerSendCache;
import com.javayh.agent.common.configuration.DataXplorerProperties;
import com.javayh.agent.common.context.SpringBeanContext;
import com.javayh.agent.common.repository.DataStreamSink;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
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
public class DataXplorerCustomTrackHandler extends SimpleChannelInboundHandler<CustomTrackLoggerProto.CustomTrackLogger> {

    private static final String YMS = "yyyy-MM-dd HH:mm:ss";

    private final String appName;
    private final Boolean showLog;

    public DataXplorerCustomTrackHandler(DataXplorerProperties dataXplorerProperties) {
        this.appName = dataXplorerProperties.getAppName();
        this.showLog = dataXplorerProperties.getShowLog();
    }

    /**
     * @param ctx the {@link ChannelHandlerContext} which this {@link SimpleChannelInboundHandler}
     *            belongs to
     * @param msg the message to handle
     * @throws Exception is thrown if an error occurred
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, CustomTrackLoggerProto.CustomTrackLogger msg) throws Exception {
        try {
            // 在这里处理 LoggerCollector 对象
            DataStreamSink dataStreamSink = SpringBeanContext.getBean(DataStreamSink.class);
            dataStreamSink.sink(msg);
            if (showLog) {
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

}
