package com.javayh.agent.server.logger.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.javayh.agent.server.api.entity.MicroservicesDataDTO;
import com.javayh.agent.server.api.entity.UrlDataDTO;
import com.javayh.agent.server.logger.entity.DataXplorerCustomTrackEntity;
import com.javayh.agent.server.logger.entity.DataXplorerLoggerEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

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
public interface DataXploreCustomTrackMapper extends BaseMapper<DataXplorerCustomTrackEntity> {

}
