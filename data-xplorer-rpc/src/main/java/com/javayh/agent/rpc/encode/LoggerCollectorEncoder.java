package com.javayh.agent.rpc.encode;

import com.javayh.agent.common.bean.proto.LoggerCollectorProto;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.extern.slf4j.Slf4j;

/**
 * @author haiji
 */
@Slf4j
public class LoggerCollectorEncoder extends MessageToByteEncoder<LoggerCollectorProto.LoggerCollector> {
    @Override
    protected void encode(ChannelHandlerContext ctx, LoggerCollectorProto.LoggerCollector msg, ByteBuf out) {
        try {
            // 将 MessageTypeProto.MessageType 转换为整数值，并编码到 ByteBuf
            int messageTypeValue = msg.getMessageType().getNumber();
            out.writeInt(messageTypeValue);
            byte[] data = msg.toByteArray();
            // 将消息体的长度写入 ByteBuf
            out.writeInt(data.length);
            // 将 MessageBody 对象编码为字节数据并写入 ByteBuf
            out.writeBytes(data);
        } catch (Exception e) {
            log.error("LoggerCollectorEncoder {}", e.getMessage(), e);
        }
    }
}