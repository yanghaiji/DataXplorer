package com.javayh.agent.rpc.listener;

import com.javayh.agent.rpc.network.LoggerAgentClient;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @author haiji
 */
@Slf4j
public class ConnectionListener implements ChannelFutureListener {

    private final LoggerAgentClient loggerAgentClient;

    public ConnectionListener(LoggerAgentClient loggerAgentClient) {
        this.loggerAgentClient = loggerAgentClient;
    }

    @Override
    public void operationComplete(ChannelFuture future) throws Exception {
        if (!future.isSuccess()) {
            //重连交给后端线程执行
            future.channel().eventLoop().schedule(() -> {
                log.error("reconnect client...");
                try {
                    loggerAgentClient.scheduleReconnect("exception caught");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }, 3000, TimeUnit.MILLISECONDS);
        }
    }

}