package com.javayh.agent.server.api.service;

import com.javayh.agent.server.api.entity.DataGrowthDTO;
import com.javayh.agent.server.api.entity.MicroservicesDataDTO;
import com.javayh.agent.server.api.entity.UrlDataDTO;

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
public interface DataXplorerService {

    /**
     * 获取top 10 url
     */
    List<UrlDataDTO> getTop10Urls();

    /**
     * 常用微服务
     */
    List<MicroservicesDataDTO> coreBusinessMicroservices();

    /**
     * 微服务报错率
     */
    List<MicroservicesDataDTO> serviceErrorRate();

    /**
     * 最近的数据
     *
     */
    DataGrowthDTO dataGrowth();
}
