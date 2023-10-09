package com.javayh.agent.rpc.channel;

import com.javayh.agent.common.bean.proto.LoggerCollectorProto;
import com.javayh.agent.common.bean.proto.MessageBodyProto;
import com.javayh.agent.common.configuration.DataXplorerProperties;
import com.javayh.agent.rpc.handler.AgentServerHandler;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
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
                /*
                 * IdleStateHandler  空闲状态的处理器
                 * long readerIdleTime, 多长时间没有读出数据，就会发送心跳检测，检测是否链接
                 * long writerIdleTime, 多长时间没有写出数据，就会发送心跳检测
                 * long allIdleTime     多长时间没有读写出数据，就会发送心跳检测
                 */
                // 五秒没有收到消息 将IdleStateHandler 添加到 ChannelPipeline 中
                .addLast(new IdleStateHandler(30, 30, 35))
                .addLast(new ProtobufEncoder())
                // 添加对象解码器
                .addLast(new ProtobufDecoder(LoggerCollectorProto.LoggerCollector.getDefaultInstance()))
                .addLast(new AgentServerHandler(dataXplorerProperties));
    }
}
