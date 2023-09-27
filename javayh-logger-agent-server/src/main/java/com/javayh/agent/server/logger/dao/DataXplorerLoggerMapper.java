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

    /**
     * 所有常用服务的百分比
     */
    @Select("SELECT app_name as appName, count( app_name ) / (SELECT count(a.id)  FROM data_xplorer_logger a) * 100 as total  FROM data_xplorer_logger  GROUP BY app_name ;")
    List<MicroservicesDataDTO> coreBusinessMicroservices();

    @Select(
            "SELECT aa.app_name ,aa.total,bb.total/aa.total * 100 rate FROM  (SELECT a.app_name ,count(a.app_name) total  FROM data_xplorer_logger a GROUP BY a.app_name ) aa " +
                    "JOIN   (SELECT b.app_name ,count(b.app_name) total FROM data_xplorer_logger b WHERE b.error_msg is not null GROUP BY b.app_name )  bb ON bb.app_name= aa.app_name")
    List<MicroservicesDataDTO> serviceErrorRate();

    @Select("SELECT a.app_name ,count(a.app_name) total ,date_format(a.create_time,'%Y-%m-%d') date  FROM data_xplorer_logger a GROUP BY a.app_name,date  HAVING date <= date_format(now(),'%Y-%m-%d')")
    List<MicroservicesDataDTO> dataGrowth();
}
