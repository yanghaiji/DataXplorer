package com.javayh.agent.rpc.encode;

import com.javayh.agent.common.bean.proto.MessageBodyProto;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class MessageBodyEncoder extends MessageToByteEncoder<MessageBodyProto.MessageBody> {
    @Override
    protected void encode(ChannelHandlerContext ctx, MessageBodyProto.MessageBody msg, ByteBuf out) {
        // 将 MessageTypeProto.MessageType 转换为整数值，并编码到 ByteBuf
        int messageTypeValue = msg.getType().getNumber();
        out.writeInt(messageTypeValue);
        // 将 MessageBody 对象编码为字节数据并写入 ByteBuf
        byte[] data = msg.toByteArray();
        out.writeBytes(Unpooled.wrappedBuffer(data));
    }
}