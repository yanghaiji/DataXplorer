package com.javayh.agent.common.bean;

import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 用于表示前端埋点事件的类
 *
 * @author haiji
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FrontendEventRequest {

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
     * 其他的任何参数
     */
    private JSONObject others;

}