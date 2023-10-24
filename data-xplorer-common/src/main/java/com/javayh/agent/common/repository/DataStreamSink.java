package com.javayh.agent.common.repository;

/**
 * <p>
 * 持久化的统一接口
 * </p>
 *
 * @author hai ji
 * @version 1.0.0
 * @since 2023-09-23
 */
public interface DataStreamSink<T> {

    /**
     * 单条数据存储
     *
     * @param data 原始数据
     */
    void sink(T data);

}
