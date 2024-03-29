package com.javayh.agent.server.logger;

import com.javayh.agent.common.bean.proto.CustomTrackLoggerProto;
import com.javayh.agent.common.bean.proto.LoggerCollectorProto;
import com.javayh.agent.common.configuration.DataXplorerProperties;
import com.javayh.agent.common.executor.OutboundExecutor;
import com.javayh.agent.common.listener.OutboundCacheQueue;
import com.javayh.agent.common.repository.DataStreamSink;
import com.javayh.agent.server.listener.OutboundQueueListener;
import com.javayh.agent.server.logger.dao.DataXploreCustomTrackMapper;
import com.javayh.agent.server.logger.entity.DataXplorerCustomTrackEntity;
import com.javayh.agent.server.logger.entity.DataXplorerFrontendLoggerEntity;
import com.javayh.agent.server.logger.entity.DataXplorerLoggerEntity;
import com.javayh.agent.server.logger.service.DataXplorerFrontendLoggerService;
import com.javayh.agent.server.logger.service.DataXplorerLoggerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 *
 * </p>
 *
 * @author hai ji
 * @version 1.0.0
 * @since 2023-09-23
 */
@Slf4j
@Primary
@Service
public class DataStreamSinkImpl<T> implements DataStreamSink<T> {

    @Resource
    private DataXploreCustomTrackMapper customTrackMapper;

    public DataStreamSinkImpl(DataXplorerLoggerService dataXplorerLoggerService, DataXplorerProperties dataXplorerProperties,
                              DataXplorerFrontendLoggerService frontendLoggerService) {

        DataXplorerProperties.OutboundTransferRate outboundTransferRate = dataXplorerProperties.getOutboundTransferRate();
        Integer initialDelay = outboundTransferRate.getInitialDelay();
        Integer period = outboundTransferRate.getPeriod();
        Integer dataThroughput = outboundTransferRate.getDataThroughput();
        // 设置定时任务，每隔一定时间执行批量插入
        ScheduledExecutorService singe = OutboundExecutor.singe();
        singe.scheduleAtFixedRate(new OutboundQueueListener<DataXplorerLoggerEntity>(
                        OutboundCacheQueue.OUTBOUND_CACHE, dataThroughput, dataXplorerLoggerService),
                initialDelay, period, TimeUnit.SECONDS);
        singe.scheduleAtFixedRate(new OutboundQueueListener<DataXplorerFrontendLoggerEntity>(
                        OutboundCacheQueue.DB_CACHE, dataThroughput, frontendLoggerService),
                initialDelay, period, TimeUnit.SECONDS);
    }

    /**
     * 单条数据存储
     *
     * @param data 原始数据
     */
    @Override
    public void sink(T data) {
        if (data instanceof LoggerCollectorProto.LoggerCollector) {
            boolean ignore = ((LoggerCollectorProto.LoggerCollector) data).getIgnore();
            if (!ignore) {
                DataXplorerLoggerEntity dataXplorerLoggerEntity = new DataXplorerLoggerEntity();
                dataXplorerLoggerEntity.copy((LoggerCollectorProto.LoggerCollector)data);
                OutboundCacheQueue.OUTBOUND_CACHE.offer(dataXplorerLoggerEntity);
            }
        }else if(data instanceof CustomTrackLoggerProto.CustomTrackLogger){
            DataXplorerCustomTrackEntity dataXplorerLoggerEntity = new DataXplorerCustomTrackEntity();
            dataXplorerLoggerEntity.copy((CustomTrackLoggerProto.CustomTrackLogger)data);
            customTrackMapper.insert(dataXplorerLoggerEntity);
        }
    }

}
