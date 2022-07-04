package com.javayh.agent.core.constant;

/**
 * <p>
 * 请求的类型
 * </p>
 *
 * @author hai ji
 * @version 1.0.0
 * @since 2022-06-29
 */
public enum LoggerType {

    FEIGN(0),
    INTERCEPTOR(1),


    ;

    private int type;

    LoggerType(int type){
        this.type = type;
    }

    public int value(){
        return type;
    }

}
