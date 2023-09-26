package com.javayh.agent.server.logger.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.javayh.agent.server.api.entity.MicroservicesDataDTO;
import com.javayh.agent.server.api.entity.UrlDataDTO;
import com.javayh.agent.server.logger.dao.DataXplorerLoggerMapper;
import com.javayh.agent.server.logger.entity.DataXplorerLoggerEntity;
import com.javayh.agent.server.logger.service.DataXplorerLoggerService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
public class DataXplorerLoggerServiceImpl extends ServiceImpl<DataXplorerLoggerMapper, DataXplorerLoggerEntity> implements DataXplorerLoggerService {

    @Resource
    private DataXplorerLoggerMapper dataXplorerLoggerMapper;

    /**
     * 查询top 时的url
     */
    @Override
    public List<UrlDataDTO> getTop10Urls() {
        return dataXplorerLoggerMapper.getTop10Urls();
    }

    /**
     * 常用的微服务
     */
    @Override
    public List<MicroservicesDataDTO> coreBusinessMicroservices() {
        return dataXplorerLoggerMapper.coreBusinessMicroservices();
    }
}
