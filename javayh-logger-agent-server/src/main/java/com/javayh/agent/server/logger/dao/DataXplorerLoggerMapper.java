package com.javayh.agent.server.logger.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.javayh.agent.server.api.entity.MicroservicesDataDTO;
import com.javayh.agent.server.api.entity.UrlDataDTO;
import com.javayh.agent.server.logger.entity.DataXplorerLoggerEntity;
import org.apache.ibatis.annotations.Mapper;
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
public interface DataXplorerLoggerMapper extends BaseMapper<DataXplorerLoggerEntity> {

    /**
     * 查询 top 10 url
     */
    @Select("SELECT url, count( url ) as requests  FROM data_xplorer_logger  GROUP BY url limit 10 ")
    List<UrlDataDTO> getTop10Urls();

    @Select("SELECT app_name as appName, count( app_name ) /100  as total  FROM data_xplorer_logger  GROUP BY app_name ")
    List<MicroservicesDataDTO> coreBusinessMicroservices();

}
