package com.javayh.agent.rpc.handler;

import com.javayh.agent.common.bean.proto.MessageBodyProto;
import com.javayh.agent.common.configuration.DataXplorerProperties;
import com.javayh.agent.rpc.OnlineServiceHolder;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

/**
 * 服务端处理器
 *
 * @author haiji
 */
@Slf4j
@ChannelHandler.Sharable
public class AgentRegistryServerHandler extends SimpleChannelInboundHandler<MessageBodyProto.MessageBody> {

    private static final String YMS = "yyyy-MM-dd HH:mm:ss";

    private final String appName;

    public AgentRegistryServerHandler(DataXplorerProperties dataXplorerProperties) {
        this.appName = dataXplorerProperties.getAppName();
    }


    /**
     * Is called for each message of type {@link I}.
     *
     * @param ctx the {@link ChannelHandlerContext} which this {@link SimpleChannelInboundHandler}
     *            belongs to
     * @param msg the message to handle
     * @throws Exception is thrown if an error occurred
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageBodyProto.MessageBody message) throws Exception {
        try {
            String name = message.getAppName();
            if (StringUtils.isNoneBlank(name)) {
                if (message.getIsActive()) {
                    OnlineServiceHolder.put(name);
                } else {
                    OnlineServiceHolder.remove(name);
                }
            } else {
                ctx.fireChannelRead(message);
            }
        } catch (Exception e) {
            log.error("channelRead {}", ExceptionUtils.getStackTrace(e));
        }
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
