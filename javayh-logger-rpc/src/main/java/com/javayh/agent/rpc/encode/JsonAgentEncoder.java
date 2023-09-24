package com.javayh.agent.rpc.encode;

import com.alibaba.fastjson.JSON;
import com.javayh.agent.common.bean.LoggerCollector;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.nio.charset.StandardCharsets;

/**
 * <p>
 * json编码器
 * </p>
 *
 * @author hai ji
 * @version 1.0.0
 * @since 2023-09-24
 */
public class JsonAgentEncoder extends MessageToByteEncoder<LoggerCollector> {

    @Override
    protected void encode(ChannelHandlerContext ctx, LoggerCollector loggerCollector, ByteBuf out) throws Exception {
        // 将LoggerCollector对象转换为JSON字符串
        String json = JSON.toJSONString(loggerCollector);
        byte[] jsonBytes = json.getBytes(StandardCharsets.UTF_8);
        // 写入消息长度
        out.writeInt(jsonBytes.length);
        // 写入JSON数据
        out.writeBytes(jsonBytes);
    }


}