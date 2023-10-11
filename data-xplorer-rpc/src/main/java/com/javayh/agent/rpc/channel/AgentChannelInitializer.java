package com.javayh.agent.rpc.channel;

import com.javayh.agent.common.bean.proto.LoggerCollectorProto;
import com.javayh.agent.common.configuration.DataXplorerProperties;
import com.javayh.agent.rpc.handler.AgentClientHandler;
import com.javayh.agent.rpc.network.LoggerAgentClient;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.timeout.IdleStateHandler;

/**
 * <p>
 * channel 处理器
 * </p>
 *
 * @author hai ji
 * @version 1.0.0
 * @since 2023-09-21
 */
public class AgentChannelInitializer extends ChannelInitializer<SocketChannel> {

    private final LoggerAgentClient loggerAgentClient;
    private final DataXplorerProperties dataXplorerProperties;

    public AgentChannelInitializer(LoggerAgentClient loggerAgentClient, DataXplorerProperties dataXplorerProperties) {
        this.loggerAgentClient = loggerAgentClient;
        this.dataXplorerProperties = dataXplorerProperties;
    }

    /**
     * This method will be called once the {@link Channel} was registered. After the method returns this instance
     * will be removed from the {@link ChannelPipeline} of the {@link Channel}.
     *
     * @param ch the {@link Channel} which was registered.
     * @throws Exception is thrown if an error occurs. In that case it will be handled by
     *                   {@link #exceptionCaught(ChannelHandlerContext, Throwable)} which will by default close
     *                   the {@link Channel}.
     */
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        //加入自己的处理器
        ch.pipeline()
                // 10 秒没发送消息 将IdleStateHandler 添加到 ChannelPipeline 中
                .addLast(new IdleStateHandler(0, 30, 0))
                // 添加对象编码器
                .addLast(new ProtobufEncoder())
                // 添加对象解码器
                .addLast(new ProtobufDecoder(LoggerCollectorProto.LoggerCollector.getDefaultInstance()))
                .addLast(new AgentClientHandler(loggerAgentClient, dataXplorerProperties));
    }
}
