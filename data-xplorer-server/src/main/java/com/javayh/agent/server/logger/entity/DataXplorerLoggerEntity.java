package com.javayh.agent.server.logger.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.google.protobuf.Timestamp;
import com.javayh.agent.common.bean.proto.LoggerCollectorProto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "data_xplorer_logger")
public class DataXplorerLoggerEntity implements Serializable {

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
     * 请求访问的类型，{@link com.javayh.agent.common.constant.LoggerType}
     */
    private Integer requestType;

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
     * 数据落库的时间
     */
    private Date insertTime;

    public void copy(LoggerCollectorProto.LoggerCollector source) {
        BeanUtils.copyProperties(source, this);
        Timestamp date = source.getCreateTime();
        this.setCreateTime(new Date(date.getSeconds() * 1000L + date.getNanos() / 1000000));
        this.setInsertTime(new Date());
        this.setRequestType(source.getType());
    }
}