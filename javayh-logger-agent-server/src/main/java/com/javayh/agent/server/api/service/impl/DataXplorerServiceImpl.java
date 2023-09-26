package com.javayh.agent.server.api.service.impl;

import com.javayh.agent.server.api.entity.MicroservicesDataDTO;
import com.javayh.agent.server.api.entity.UrlDataDTO;
import com.javayh.agent.server.api.service.DataXplorerService;
import com.javayh.agent.server.logger.service.DataXplorerLoggerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
@Service
public class DataXplorerServiceImpl implements DataXplorerService {

    @Autowired
    private DataXplorerLoggerService dataXplorerLoggerService;


    /**
     * 获取top 10 url
     *
     * @return
     */
    @Override
    public List<UrlDataDTO> getTop10Urls() {
        return dataXplorerLoggerService.getTop10Urls();
    }

    /**
     * 常用微服务
     * @return
     */
    @Override
    public List<MicroservicesDataDTO> coreBusinessMicroservices() {
        return dataXplorerLoggerService.coreBusinessMicroservices();
    }
}
