package com.javayh.agent.rpc.encode;

import com.google.protobuf.InvalidProtocolBufferException;
import com.javayh.agent.common.bean.proto.MessageBodyProto;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class MessageBodyDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws InvalidProtocolBufferException {
        // 从 ByteBuf 中解码字芴数据并转换为 MessageBody 对象
        if (in.readableBytes() < 4) {
            // 数据不足，等待更多数据
            return;
        }

        int dataLength = in.readInt();
        if (in.readableBytes() < dataLength) {
            // 数据不完整，等待更多数据
            return;
        }

        byte[] data = new byte[dataLength];
        in.readBytes(data);
        MessageBodyProto.MessageBody messageBody = MessageBodyProto.MessageBody.parseFrom(data);
        out.add(messageBody);
    }
}