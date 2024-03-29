package com.javayh.agent.common.configuration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.annotation.Order;
import org.springframework.lang.NonNull;

import java.util.List;

/**
 * <p>
 * 配置类
 * </p>
 *
 * @author hai ji
 * @version 1.0.0
 * @since 2023-09-22
 */
@Order(value = 10)
@Data
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties(prefix = "data.xplorer")
public class DataXplorerProperties {

    /**
     * 服务端地址
     */
    private String host = "127.0.0.1";

    /**
     * 端口号
     */
    private Integer port = 9090;

    /**
     * 服务名
     */
    @NonNull
    private String appName;

    /**
     * 是否开启log,默认开启
     */
    private Boolean showLog = true;

    /**
     * 需要排除的url
     */
    private List<String> excludeUrl;

    /**
     * 服务的配速
     * <p>
     * 这里的配置需要注意配置，需要根据实际的网速和资源进行合理的配比
     * <p>
     * 否则将会出现负载过高，导致无法分批执行，而是持续执行
     */
    @NonNull
    private InboundTransferRate inboundTransferRate;

    /**
     * 数据消费的速率，这里需要根据项目的实际内存进行分析，避免消费过慢导致的内存剧增
     */
    @NonNull
    private OutboundTransferRate outboundTransferRate;


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class InboundTransferRate {

        /**
         * 初始化起始执行时间 {@link java.util.concurrent.TimeUnit#SECONDS}
         */
        private Integer initialDelay = 30;

        /**
         * 每次执行的时间间隔，这个值需要根据实际情况而调整，避免数据的堆积{@link java.util.concurrent.TimeUnit#SECONDS}
         */
        private Integer period = 30;

        /**
         * 每次间隔内需要发送数据的总和
         */
        private Integer dataThroughput = 100;

    }


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OutboundTransferRate {

        /**
         * 初始化起始执行时间 {@link java.util.concurrent.TimeUnit#SECONDS}
         */
        private Integer initialDelay = 60;

        /**
         * 每次执行的时间间隔，这个值需要根据实际情况而调整，避免数据的堆积{@link java.util.concurrent.TimeUnit#SECONDS}
         */
        private Integer period = 30;

        /**
         * 每次保存的数据量
         */
        private Integer dataThroughput = 1000;

    }


}
