package com.javayh.agent.core.bean;

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

    private String traceId;

    private String method;

    private String url;

    private String query;

    private String body;

    private Long actionTime;

    private String ip;

    private String webName;

    /**
     * 请求放生的类型
     */
    private Integer type;

    private Date createTime;


}
