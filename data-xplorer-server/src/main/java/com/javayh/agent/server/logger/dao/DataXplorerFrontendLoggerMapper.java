package com.javayh.agent.server.logger.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.javayh.agent.server.logger.entity.DataXplorerFrontendLoggerEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 数据持久化
 * </p>
 *
 * @author hai ji
 * @version 1.0.0
 * @since 2023-09-24
 */
@Mapper
public interface DataXplorerFrontendLoggerMapper extends BaseMapper<DataXplorerFrontendLoggerEntity> {

}
