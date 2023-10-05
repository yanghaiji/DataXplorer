package com.javayh.agent.common.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 收集类
 * </p>
 *
 * @author hai ji
 * @version 1.0.0
 * @since 2022-06-29
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoggerCollector implements Serializable {

    private static final long serialVersionUID = 3575438370068966717L;

    private Long id;

    /**
     * 请求的trace id
     */
    private String traceId;

    /**
     * 操作的方法类型 GET | POST
     */
    private String method;

    /**
     * 请求路径
     */
    private String url;

    /**
     * 请求的参数
     */
    private String query;

    /**
     * 请求的参数
     */
    private String body;

    /**
     * 操作耗时
     */
    private Long actionTime;

    /**
     * 操作人的ip
     */
    private String ip;

    /**
     * 服务的名字
     */
    private String appName;

    /**
     * 请求访问的类型，{@link com.javayh.agent.common.constant.LoggerType;}
     */
    private Integer type;

    /**
     * 创建的时间
     */
    private Date createTime;

    /**
     * 创建人
     * <p>
     * 这里需要结合自己的开发脚手架进行创建人
     */
    private String createBy;

    /**
     * 异常信息
     */
    private String errorMsg;

    /**
     * 数据来源，自动拦截或者自定义埋点
     * {@link com.javayh.agent.common.constant.LoggerSourceType}
     */
    private Integer sourceType;

    /**
     * 是否忽略此消息，消息传递时判断是否需要继续向下传递
     */
    private boolean ignore = false;

}
