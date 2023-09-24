package com.javayh.agent.server.logger;

import com.javayh.agent.common.bean.LoggerCollector;
import com.javayh.agent.common.repository.LoggerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

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
public class LoggerRepositoryImpl implements LoggerRepository<LoggerCollector> {
    /**
     * 单条数据存储
     *
     * @param data 原始数据
     */
    @Override
    public void save(LoggerCollector data) {
        log.info("------");
        log.info("data {}", data);
    }

    /**
     * 单条数据存储
     *
     * @param data 原始数据集
     */
    @Override
    public void batchSave(List<LoggerCollector> data) {

    }
}
