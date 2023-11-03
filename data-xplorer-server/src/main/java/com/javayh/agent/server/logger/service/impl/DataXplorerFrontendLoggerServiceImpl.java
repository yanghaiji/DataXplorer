package com.javayh.agent.server.logger.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.javayh.agent.common.bean.FrontendEventRequest;
import com.javayh.agent.server.logger.dao.DataXplorerFrontendLoggerMapper;
import com.javayh.agent.server.logger.entity.DataXplorerFrontendLoggerEntity;
import com.javayh.agent.server.logger.service.DataXplorerFrontendLoggerService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Date;

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
public class DataXplorerFrontendLoggerServiceImpl extends ServiceImpl<DataXplorerFrontendLoggerMapper, DataXplorerFrontendLoggerEntity> implements DataXplorerFrontendLoggerService {


    /**
     * 存储前端时间
     *
     * @param request 数据源
     */
    @Override
    public void saveEvent(FrontendEventRequest request) {
        // 目前先简单的入库
        DataXplorerFrontendLoggerEntity dataXplorerFrontendLoggerEntity = new DataXplorerFrontendLoggerEntity();
        BeanUtils.copyProperties(request, dataXplorerFrontendLoggerEntity);
        dataXplorerFrontendLoggerEntity.setCreateTime(new Date());
        dataXplorerFrontendLoggerEntity.setOthersBody(JSON.toJSONString(request.getOthers()));
        this.save(dataXplorerFrontendLoggerEntity);

    }
}
