package com.javayh.agent.server.logger.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.javayh.agent.common.bean.FrontendEventRequest;
import com.javayh.agent.server.logger.entity.DataXplorerFrontendLoggerEntity;

/**
 * <p>
 *
 * </p>
 *
 * @author hai ji
 * @version 1.0.0
 * @since 2023-09-25
 */
public interface DataXplorerFrontendLoggerService extends IService<DataXplorerFrontendLoggerEntity> {

    /**
     * 存储前端时间
     *
     * @param request 数据源
     */
    void saveEvent(FrontendEventRequest request);

}
