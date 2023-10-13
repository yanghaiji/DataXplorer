package com.javayh.agent.rpc.encode;

import com.google.protobuf.InvalidProtocolBufferException;
import com.javayh.agent.common.bean.proto.LoggerCollectorProto;
import com.javayh.agent.common.bean.proto.MessageBodyProto;
import com.javayh.agent.common.bean.proto.MessageTypeProto;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

/**
 * @author haiji
 */
public class MessageDecoder extends MessageToMessageDecoder<ByteBuf> {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws InvalidProtocolBufferException {
        // 从消息中提取消息类型
        // 读取消息类型字段
        int messageTypeValue = in.readInt();

        // 将消息类型字段的值解释为 MessageType 枚举
        MessageTypeProto.MessageType messageType = MessageTypeProto.MessageType.valueOf(messageTypeValue);


        // 根据消息类型选择合适的解码器
        if (messageType == MessageTypeProto.MessageType.LOGGER_COLLECTOR) {
            // 读取 MessageBodyProto.MessageBody
            byte[] messageData = new byte[in.readableBytes()];
            in.readBytes(messageData);
            LoggerCollectorProto.LoggerCollector messageBody = LoggerCollectorProto.LoggerCollector.parseFrom(messageData);

            // 执行相应的处理逻辑，将 messageBody 添加到 out 列表
            out.add(messageBody);
        } else if (messageType == MessageTypeProto.MessageType.MESSAGE_BODY) {
            // 读取 MessageBodyProto.MessageBody
            byte[] messageData = new byte[in.readableBytes()];
            in.readBytes(messageData);
            MessageBodyProto.MessageBody messageBody = MessageBodyProto.MessageBody.parseFrom(messageData);

            // 执行相应的处理逻辑，将 messageBody 添加到 out 列表
            out.add(messageBody);
        } else {
            // 处理未知消息类型或抛出错误
            ctx.close(); // 或者采取适当的错误处理措施
        }
    }

    public static void main(String[] args) {
        // 将消息类型字段的值解释为 MessageType 枚举
        MessageTypeProto.MessageType messageType = MessageTypeProto.MessageType.valueOf(16);
        System.out.println(messageType);
    }
}
