package com.javayh.agent.server.logger.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author haiji
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "data_xplorer_frontend_event_logger")
public class DataXplorerFrontendLoggerEntity implements Serializable {

    private static final long serialVersionUID = 3575438370068966717L;

    private Long id;

    /**
     * 请求的trace id
     */
    private String traceId;
    /**
     * 事件名称，例如 "Button Click"
     */
    private String eventName;

    /**
     * 元素ID，例如 "myButton"
     */
    private String elementId;

    /**
     * 元素类型，例如 "Button", "Link", "Form"
     */
    private String elementType;

    /**
     * 页面URL，用户发生事件的页面
     */
    private String pageUrl;

    /**
     * 用户ID，标识事件来源用户
     */
    private String userId;

    /**
     * 事件发生的时间戳
     */
    private Date eventTime;

    /**
     * web / qpp / 小程序
     */
    private String sourceType;

    /**
     * 创建的时间
     */
    private Date createTime;

    /**
     * 其他的任何参数
     */
    private String othersBody;

}
