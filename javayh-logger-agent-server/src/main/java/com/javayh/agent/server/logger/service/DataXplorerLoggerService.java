package com.javayh.agent.server.logger.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.javayh.agent.server.api.entity.MicroservicesDataDTO;
import com.javayh.agent.server.api.entity.UrlDataDTO;
import com.javayh.agent.server.logger.entity.DataXplorerLoggerEntity;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author hai ji
 * @version 1.0.0
 * @since 2023-09-25
 */
public interface DataXplorerLoggerService extends IService<DataXplorerLoggerEntity> {

    /**
     * 查询top 时的url
     */
    List<UrlDataDTO> getTop10Urls();

    /**
     * 常用的微服务
     */
    List<MicroservicesDataDTO> coreBusinessMicroservices();
}
