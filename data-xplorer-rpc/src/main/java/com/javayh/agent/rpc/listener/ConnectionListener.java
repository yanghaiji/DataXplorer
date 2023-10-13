package com.javayh.agent.rpc.listener;

import com.javayh.agent.rpc.network.DataXplorerClient;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @author haiji
 */
@Slf4j
public class ConnectionListener implements ChannelFutureListener {

    private final DataXplorerClient loggerAgentClient;

    public ConnectionListener(DataXplorerClient loggerAgentClient) {
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
            }, 15, TimeUnit.SECONDS);
        }
    }

}