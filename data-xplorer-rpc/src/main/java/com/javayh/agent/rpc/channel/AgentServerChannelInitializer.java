package com.javayh.agent.rpc.channel;

import com.javayh.agent.common.configuration.DataXplorerProperties;
import com.javayh.agent.rpc.encode.MessageDecoder;
import com.javayh.agent.rpc.handler.AgentRegistryServerHandler;
import com.javayh.agent.rpc.handler.AgentServerHandler;
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
public class AgentServerChannelInitializer extends ChannelInitializer<SocketChannel> {


    private final DataXplorerProperties dataXplorerProperties;

    public AgentServerChannelInitializer(DataXplorerProperties dataXplorerProperties) {
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
        ch.pipeline()
                .addLast(new MessageDecoder())
                .addLast(new AgentRegistryServerHandler(dataXplorerProperties))
//                .addLast(new ProtobufVarint32FrameDecoder())
//                .addLast(new ProtobufDecoder(MessageBodyProto.MessageBody.getDefaultInstance()))
//                .addLast(new ProtobufVarint32LengthFieldPrepender())
//                .addLast(new ProtobufEncoder())
//                .addLast(new AgentRegistryServerHandler(dataXplorerProperties))
                .addLast(new AgentServerHandler(dataXplorerProperties))
                .addLast(new IdleStateHandler(30, 30, 35));
    }
}
