package com.javayh.agent.rpc.encode;

import com.alibaba.fastjson.JSON;
import com.javayh.agent.common.bean.LoggerCollector;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * <p>
 * json解码器
 * </p>
 *
 * @author hai ji
 * @version 1.0.0
 * @since 2023-09-24
 */
public class JsonAgentDecoder extends MessageToMessageDecoder<ByteBuf> {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if (in.readableBytes() < 4) {
            // 数据不足4字节，等待更多数据
            return;
        }
        in.markReaderIndex();
        int length = in.readInt();

        if (in.readableBytes() < length) {
            // 数据不足消息长度，等待更多数据
            in.resetReaderIndex();
            return;
        }
        byte[] bytes = new byte[length];
        in.readBytes(bytes);
        LoggerCollector loggerCollector = JSON.parseObject(new String(bytes, StandardCharsets.UTF_8), LoggerCollector.class);
        // 将解码后的对象添加到out列表
        out.add(loggerCollector);
    }
}
