package com.javayh.agent.common.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>
 * 默认实现，只做数据的控制台打印
 * </p>
 *
 * @author hai ji
 * @version 1.0.0
 * @since 2023-09-23
 */
@Slf4j
@Component
public class DefaultDataStreamSink<T> implements DataStreamSink<T> {
    /**
     * 单条数据存储
     *
     * @param data 原始数据
     */
    @Override
    public void sink(T data) {
        log.info("DefaultDataStreamSink data{}", data);
    }


}
