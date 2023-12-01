package com.javayh.agent.rpc.channel;

import com.javayh.agent.common.configuration.DataXplorerProperties;
import com.javayh.agent.rpc.encode.LoggerCollectorEncoder;
import com.javayh.agent.rpc.encode.MessageDecoder;
import com.javayh.agent.rpc.handler.DataXplorerCustomTrackHandler;
import com.javayh.agent.rpc.handler.DataXplorerRegistryServerHandler;
import com.javayh.agent.rpc.handler.DataXplorerServerHandler;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;


/**
 * <p>
 * server channel
 * </p>
 *
 * @author hai ji
 * @version 1.0.0
 * @since 2023-09-22
 */
public class DataXplorerServerChannelInitializer extends ChannelInitializer<SocketChannel> {


    private final DataXplorerProperties dataXplorerProperties;

    public DataXplorerServerChannelInitializer(DataXplorerProperties dataXplorerProperties) {
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
     *                   .addLast(new ProtobufVarint32FrameDecoder())
     *                   .addLast(new ProtobufDecoder(MessageBodyProto.MessageBody.getDefaultInstance()))
     *                   .addLast(new ProtobufVarint32LengthFieldPrepender())
     *                   .addLast(new ProtobufEncoder())
     *                   .addLast(new AgentRegistryServerHandler(dataXplorerProperties))
     */
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ch.pipeline()
                .addLast(new IdleStateHandler(30, 30, 30))
                .addLast(new LoggerCollectorEncoder())
                .addLast(new MessageDecoder())
                .addLast(new DataXplorerRegistryServerHandler(dataXplorerProperties))
                .addLast(new DataXplorerCustomTrackHandler(dataXplorerProperties))
                .addLast(new DataXplorerServerHandler(dataXplorerProperties));
    }
}
