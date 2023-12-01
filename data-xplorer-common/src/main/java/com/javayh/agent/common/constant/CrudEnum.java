package com.javayh.agent.common.constant;


import com.javayh.agent.common.handler.OperationHandler;

/**
 * @author HaiJiYang
 */
public enum CrudEnum implements OperationHandler {

    SAVE(1, "SAVE"),
    UPDATE(2, "UPDATE"),
    INSERT(3, "INSERT"),
    SELECT(4, "SELECT");

    private final Integer operation;
    private final String value;

    CrudEnum(Integer operation, String value) {
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