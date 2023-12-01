package com.javayh.agent.rpc.channel;

import com.javayh.agent.common.configuration.DataXplorerProperties;
import com.javayh.agent.rpc.encode.CustomTrackEncoder;
import com.javayh.agent.rpc.encode.LoggerCollectorEncoder;
import com.javayh.agent.rpc.encode.MessageBodyEncoder;
import com.javayh.agent.rpc.handler.DataXplorerClientHandler;
import com.javayh.agent.rpc.handler.DataXplorerRegistryClientHandler;
import com.javayh.agent.rpc.network.DataXplorerClient;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
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
public class DataXplorerChannelInitializer extends ChannelInitializer<SocketChannel> {

    private final DataXplorerClient loggerAgentClient;
    private final DataXplorerProperties dataXplorerProperties;

    public DataXplorerChannelInitializer(DataXplorerClient loggerAgentClient, DataXplorerProperties dataXplorerProperties) {
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
     *                   .addLast(new ProtobufVarint32FrameDecoder(),
     *                   new MessageBodyDecoder(),
     *                   new ProtobufVarint32LengthFieldPrepender(),
     *                   new MessageBodyEncoder(),
     *                   new AgentRegistryClientHandler(loggerAgentClient, dataXplorerProperties))
     *                   <p>
     *                   .addLast(new ProtobufVarint32FrameDecoder(),
     *                   new LoggerCollectorDecoder(),
     *                   new ProtobufVarint32LengthFieldPrepender(),
     *                   new LoggerCollectorEncoder(),
     *                   new AgentClientHandler(loggerAgentClient, dataXplorerProperties))
     *                   .addLast(new IdleStateHandler(0, 30, 0));
     */
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ch.pipeline()
                .addLast(new IdleStateHandler(0, 30, 0))
                .addLast(new MessageBodyEncoder())
                .addLast(new DataXplorerRegistryClientHandler(loggerAgentClient, dataXplorerProperties))
                .addLast(new LoggerCollectorEncoder())
                .addLast(new CustomTrackEncoder())
                .addLast(new DataXplorerClientHandler(loggerAgentClient, dataXplorerProperties));


    }
}
