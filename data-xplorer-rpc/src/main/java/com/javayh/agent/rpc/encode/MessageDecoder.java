package com.javayh.agent.rpc.encode;

import com.google.protobuf.InvalidProtocolBufferException;
import com.javayh.agent.common.bean.proto.LoggerCollectorProto;
import com.javayh.agent.common.bean.proto.MessageBodyProto;
import com.javayh.agent.common.bean.proto.MessageTypeProto;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * 统一的解码器
 *
 * @author haiji
 */
public class MessageDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws InvalidProtocolBufferException {

        try {
            if (in.readableBytes() >= 8) {
                // 读取消息类型字段的整数值
                int messageTypeValue = in.readInt();

                // 读取消息体的长度
                int messageLength = in.readInt();
                // 检查消息长度是否为负数
                if (messageLength < 0) {
                    // 处理错误，例如关闭连接或记录错误信息
                    ctx.close(); // 或者其他适当的错误处理
                    return;
                }

                // 检查是否有足够的数据来读取完整的消息
                if (in.readableBytes() < messageLength) {
                    // 如果没有足够的数据，返回并等待更多数据
                    return;
                }

                // 读取完整的消息体
                byte[] messageData = new byte[messageLength];
                in.readBytes(messageData);

                // 直接将整数值转换为 MessageTypeProto.MessageType 枚举
                MessageTypeProto.MessageType messageType = MessageTypeProto.MessageType.valueOf(messageTypeValue);

                if (messageType == MessageTypeProto.MessageType.LOGGER_COLLECTOR) {
                    // 读取 LoggerCollectorProto.LoggerCollector
                    LoggerCollectorProto.LoggerCollector messageBody = LoggerCollectorProto.LoggerCollector.parseFrom(messageData);
                    out.add(messageBody);
                } else if (messageType == MessageTypeProto.MessageType.MESSAGE_BODY) {
                    // 读取 MessageBodyProto.MessageBody
                    MessageBodyProto.MessageBody messageBody = MessageBodyProto.MessageBody.parseFrom(messageData);
                    out.add(messageBody);
                }
            }
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }

    }


}
