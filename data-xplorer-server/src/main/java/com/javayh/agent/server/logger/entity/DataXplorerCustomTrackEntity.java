package com.javayh.agent.server.logger.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.google.protobuf.Timestamp;
import com.javayh.agent.common.bean.proto.CustomTrackLoggerProto;
import com.javayh.agent.common.bean.proto.LoggerCollectorProto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * @author HaiJiYang
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "data_xplorer_custom_track")
public class DataXplorerCustomTrackEntity implements Serializable {

    private static final long serialVersionUID = 3575438370068966717L;

    private Long id;

    /**
     * 请求的trace id
     */
    private String traceId;

    /**
     * 实际业务的操作，比如 saveOrder ， updateOrder
     */
    private Integer operationType;

    /**
     * 请求的参数
     */
    private String requestParameter;

    /**
     * 服务的名字
     */
    private String appName;

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
     * 数据落库的时间
     */
    private Date insertTime;

    public void copy(CustomTrackLoggerProto.CustomTrackLogger source) {
        BeanUtils.copyProperties(source, this);
        Timestamp date = source.getCreateTime();
        this.setCreateTime(new Date(date.getSeconds() * 1000L + date.getNanos() / 1000000));
        this.setInsertTime(new Date());

    }
}