package com.javayh.agent.common.repository;

import java.util.List;

/**
 * <p>
 * 持久化的统一接口
 * </p>
 *
 * @author hai ji
 * @version 1.0.0
 * @since 2023-09-23
 */
public interface LoggerRepository<T> {

    /**
     * 单条数据存储
     *
     * @param data 原始数据
     */
    void save(T data);

    /**
     * 单条数据存储
     *
     * @param data 原始数据集
     */
    void batchSave(List<T> data);
}
