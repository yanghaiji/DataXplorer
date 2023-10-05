package com.javayh.agent.common.configuration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.lang.NonNull;

/**
 * <p>
 * 配置类
 * </p>
 *
 * @author hai ji
 * @version 1.0.0
 * @since 2023-09-22
 */
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


}
