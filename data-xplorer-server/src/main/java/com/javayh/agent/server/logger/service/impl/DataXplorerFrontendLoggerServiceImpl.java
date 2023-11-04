package com.javayh.agent.server.logger.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.javayh.agent.common.bean.FrontendEventRequest;
import com.javayh.agent.common.executor.DataXplorerExecutor;
import com.javayh.agent.common.listener.OutboundCacheQueue;
import com.javayh.agent.server.logger.dao.DataXplorerFrontendLoggerMapper;
import com.javayh.agent.server.logger.entity.DataXplorerFrontendLoggerEntity;
import com.javayh.agent.server.logger.service.DataXplorerFrontendLoggerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.CompletableFuture;

/**
 * <p>
 *
 * </p>
 *
 * @author hai ji
 * @version 1.0.0
 * @since 2023-09-25
 */
@Slf4j
@Service
public class DataXplorerFrontendLoggerServiceImpl extends ServiceImpl<DataXplorerFrontendLoggerMapper, DataXplorerFrontendLoggerEntity> implements DataXplorerFrontendLoggerService {

    /**
     * 存储前端时间
     *
     * @param request 数据源
     */
    @Override
    public void saveEvent(FrontendEventRequest request) {
        CompletableFuture.runAsync(() -> {
            final DataXplorerFrontendLoggerEntity entity = new DataXplorerFrontendLoggerEntity();
            BeanUtils.copyProperties(request, entity);
            entity.setCreateTime(new Date());
            entity.setOthersBody(JSON.toJSONString(request.getOthers()));
            OutboundCacheQueue.DB_CACHE.offer(entity);
        }, DataXplorerExecutor.executor);

    }
}
