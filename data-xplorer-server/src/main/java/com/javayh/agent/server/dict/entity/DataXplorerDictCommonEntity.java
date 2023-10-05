package com.javayh.agent.server.dict.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * <p>
 * 全局数据字典
 * </p>
 *
 * @author hai ji
 * @version 1.0.0
 * @since 2023-09-25
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "data_xplorer_dict_common")
public class DataXplorerDictCommonEntity {

    private Integer id;

    /**
     * 编码
     */
    private String dictCode;

    /**
     * 名称
     */
    private String dictDesc;

    /**
     * 分类编码
     */
    private String categoryCode;

    /**
     * 分类描述
     */
    private String categoryDesc;

    /**
     * 排序编号
     */
    private Integer sortNo;

    /**
     * 数据类型
     */
    private String dataType;

    /**
     * 附加说明
     */
    private String remark;

    /**
     * 检索标识
     */
    private String locateCode;

    /**
     * 乐观锁版本号
     */
    private Integer version;

    private String createBy;

    private Date createTime;

    private String updateBy;

    private Date updateTime;
}
