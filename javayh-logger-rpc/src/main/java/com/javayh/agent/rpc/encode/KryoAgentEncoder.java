package com.javayh.agent.rpc.encode;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.KryoException;
import com.esotericsoftware.kryo.io.Output;
import com.javayh.agent.common.bean.LoggerCollector;
import com.javayh.agent.common.bean.MessageBody;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.apache.commons.io.IOUtils;

import java.io.ByteArrayOutputStream;

/**
 * <p>
 * 客户端编码器
 * https://github.com/yanghaiji/health-monitor/blob/main/javayh-health-monitor-client/src/main/java/com/javayh/health/monitor/client/encode/HeartbeatEncode.java
 * </p>
 *
 * @author Dylan-haiji
 * @version 1.0.0
 * @since 2020/3/10
 */
public class KryoAgentEncoder extends MessageToByteEncoder<MessageBody> {
    private static Kryo kryo = new Kryo();

    @Override
    protected void encode(ChannelHandlerContext handlerContext,
                          MessageBody messageBody, ByteBuf byteBuf) throws Exception {
        //将对象转换为byte
        byte[] body = convertToBytes(messageBody);
        //读取消息的长度
        int dataLength = body.length;
        //先将消息长度写入，也就是消息头
        byteBuf.writeInt(dataLength);
        //消息体中包含我们要发送的数据
        byteBuf.writeBytes(body);
    }

    private byte[] convertToBytes(MessageBody messageBody) {

        ByteArrayOutputStream bos = null;
        Output output = null;
        try {
            bos = new ByteArrayOutputStream();
            output = new Output(bos);
            kryo.writeObject(output, messageBody);
            output.flush();

            return bos.toByteArray();
        } catch (KryoException e) {
            e.printStackTrace();
        }finally{
            IOUtils.closeQuietly(output);
            IOUtils.closeQuietly(bos);
        }
        return null;
    }
}