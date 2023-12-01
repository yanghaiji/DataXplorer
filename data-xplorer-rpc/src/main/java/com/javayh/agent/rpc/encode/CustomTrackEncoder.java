package com.javayh.agent.rpc.encode;

import com.javayh.agent.common.bean.proto.CustomTrackLoggerProto;
import com.javayh.agent.common.bean.proto.MessageBodyProto;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.extern.slf4j.Slf4j;

/**
 * body的编码器
 *
 * @author haiji
 */
@Slf4j
public class CustomTrackEncoder extends MessageToByteEncoder<CustomTrackLoggerProto.CustomTrackLogger> {
    @Override
    protected void encode(ChannelHandlerContext ctx, CustomTrackLoggerProto.CustomTrackLogger msg, ByteBuf out) {
        try {
            int messageTypeValue = msg.getMessageType().getNumber();
            out.writeInt(messageTypeValue);
            byte[] data = msg.toByteArray();
            // 将消息体的长度写入 ByteBuf
            out.writeInt(data.length);
            // 将 CustomTrackLogger 对象编码为字节数据并写入 ByteBuf
            out.writeBytes(data);
        } catch (Exception e) {
            log.error("CustomTrackEncoder {}", e.getMessage(), e);
        }
    }
}