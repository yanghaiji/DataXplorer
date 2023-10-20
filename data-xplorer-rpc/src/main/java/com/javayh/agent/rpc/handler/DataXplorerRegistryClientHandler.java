package com.javayh.agent.rpc.handler;

import com.javayh.agent.common.bean.proto.MessageBodyProto;
import com.javayh.agent.common.bean.proto.MessageTypeProto;
import com.javayh.agent.common.configuration.DataXplorerProperties;
import com.javayh.agent.rpc.network.DataXplorerClient;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author haiji
 */
@Slf4j
public class DataXplorerRegistryClientHandler extends ChannelInboundHandlerAdapter {

    private final DataXplorerClient loggerAgentClient;
    private final DataXplorerProperties dataXplorerProperties;

    private final String appName;
    private volatile ChannelHandlerContext context;

    public DataXplorerRegistryClientHandler(DataXplorerClient loggerAgentClient, DataXplorerProperties dataXplorerProperties) {
        this.loggerAgentClient = loggerAgentClient;
        this.dataXplorerProperties = dataXplorerProperties;
        this.appName = dataXplorerProperties.getAppName();
    }

    /**
     * 当通道就绪就会触发该方法
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(MessageBodyProto.MessageBody.newBuilder()
                .setType(MessageTypeProto.MessageType.MESSAGE_BODY)
                .setOnlineAppName(appName).setIsActive(true).build());
        ctx.fireChannelActive();
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("exceptionCaught {}", cause.getMessage(), cause);
        ctx.close();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(MessageBodyProto.MessageBody.newBuilder()
                .setType(MessageTypeProto.MessageType.MESSAGE_BODY)
                .setOnlineAppName(appName).setIsActive(false).build());
        super.channelInactive(ctx);
    }
}