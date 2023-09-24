package com.javayh.agent.rpc.listener;

import com.javayh.agent.common.bean.LoggerCollector;
import com.javayh.agent.common.cache.AgentCacheQueue;
import com.javayh.agent.common.exception.ChannelListenerException;
import com.javayh.agent.common.executor.AgentExecutor;
import com.javayh.agent.common.listener.QueueListener;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 *
 * </p>
 *
 * @author hai ji
 * @version 1.0.0
 * @since 2023-09-21
 */
@Slf4j
public class ChannelListener {

    /**
     * channel的监听器
     *
     * @param ctx channel 上下文
     */
    public void listener(ChannelHandlerContext ctx) {
        // initialDelay（初始延迟）和 period（间隔时间）来控制定期执行的频率
        // 初始延迟30秒，之后每次执行后等待30秒再继续
        try {
            AgentExecutor.singe().scheduleAtFixedRate(new QueueListener<>(
                    AgentCacheQueue.MSG_CACHE_DE, data -> sendData(ctx, data)), 30, 10, TimeUnit.SECONDS);
        } catch (Exception e) {
            log.error("listener {}", ExceptionUtils.getStackTrace(e));
        }
    }


    /**
     * 发送数据
     *
     * @param ctx  channel 上下文
     * @param data 数据
     */
    private void sendData(ChannelHandlerContext ctx, LoggerCollector data) {
        try {
            ctx.channel().eventLoop().execute(() -> {
                try {
                    if (Objects.nonNull(data)) {
                        ctx.writeAndFlush(data);
                        //ctx.writeAndFlush(Unpooled.copiedBuffer(JSONObject.toJSONString(data), CharsetUtil.UTF_8));
                    }
                } catch (Exception ex) {
                    log.error("数据发送异常 {}", ExceptionUtils.getStackTrace(ex));
                    throw new ChannelListenerException(ex);
                }
            });
        } catch (Exception e) {
            log.error("sendData {}", ExceptionUtils.getStackTrace(e));
        }
    }
}
