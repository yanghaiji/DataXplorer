package com.javayh.agent.example;

import com.javayh.agent.common.handler.OperationHandler;

/**
 * <p>
 *
 * </p>
 *
 * @author hai ji
 * @version 1.0.0
 * @since 2023-12-01
 */
public enum CustomEnum implements OperationHandler {
    EXAMPLE(7, "Example");


    private final Integer operation;
    private final String value;

    CustomEnum(Integer operation, String value) {
        this.operation = operation;
        this.value = value;
    }

    @Override
    public String value() {
        return value;
    }

    @Override
    public Integer operation() {
        return operation;
    }
}
