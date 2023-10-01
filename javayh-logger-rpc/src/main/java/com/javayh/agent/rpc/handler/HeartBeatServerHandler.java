package com.javayh.agent.rpc.handler;

import com.javayh.agent.common.bean.MessageBody;
import com.javayh.agent.common.exception.HealthMonitorException;
import com.javayh.agent.rpc.RpcSocketHolder;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.Date;

/**
 * <p>
 * 服务端
 * </p>
 *
 * @author Dylan-haiji
 * @version 1.0.0
 * @since 2021/4/13
 */
@Slf4j
public class HeartBeatServerHandler extends SimpleChannelInboundHandler<MessageBody> {

    private static final ByteBuf HEART_BEAT = Unpooled.unreleasableBuffer(
            Unpooled.copiedBuffer(
                    MessageBody.builder()
                            .msgId(20200202L)
                            .msg("pong")
                            .appName("Health-Monitor-Server")
                            .createDate(new Date()).build().toString(),
                    CharsetUtil.UTF_8));

    private static final String YMS = "yyyy-MM-dd HH:mm:ss";

    /**
     * <p>
     * 链接验证
     * </p>
     *
     * @param context
     * @param messageBody
     * @return void
     * @version 1.0.0
     * @author Dylan-haiji
     * @since 2021/4/13
     */
    @Override
    protected void channelRead0(ChannelHandlerContext context, MessageBody messageBody)
            throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append("服务端消息接收成功==>").append(messageBody);
        log.info(sb.toString());
        // 保存客户端与 Channel 之间的关系
        RpcSocketHolder.put(messageBody.getAppName(), (NioSocketChannel) context.channel());
    }

    /**
     * <p>
     * 取消绑定
     * </p>
     *
     * @param ctx
     * @return void
     * @version 1.0.0
     * @author Dylan-haiji
     * @since 2021/4/13
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        RpcSocketHolder.remove((NioSocketChannel) ctx.channel());
    }

    /**
     * <p>
     * 尝试建立链接
     * </p>
     *
     * @param ctx
     * @param evt
     * @return void
     * @version 1.0.0
     * @author Dylan-haiji
     * @since 2021/4/13
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt)
            throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent idleStateEvent = (IdleStateEvent) evt;
            if (idleStateEvent.state() == IdleState.READER_IDLE) {
                log.info("已经5秒没有收到信息！");
                // 向客户端发送消息
                ctx.writeAndFlush(HEART_BEAT)
                        .addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
            }
        }
        super.userEventTriggered(ctx, evt);
    }

    /**
     * <p>
     * 有服务上线通知
     * </p>
     *
     * @param ctx
     * @return void
     * @version 1.0.0
     * @since 2021/4/14
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("服务上线时间为: {}, IP 为:{}", DateFormatUtils.format(new Date(), YMS), ctx.channel().remoteAddress());
    }

    /**
     * <p>
     * 异常的处理机制
     * </p>
     *
     * @param ctx
     * @param cause
     * @return void
     * @version 1.0.0
     * @since 2021/4/14
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.channel().closeFuture().sync();
        throw new HealthMonitorException(ctx.name(), cause);
    }

}