package com.javayh.agent.common.constant;

/**
 * <p>
 * 请求的类型
 * </p>
 *
 * @author hai ji
 * @version 1.0.0
 * @since 2022-06-29
 */
public enum LoggerSourceType {

    AUTOMATIC(0),
    MANUAL (1),


    ;

    private int type;

    LoggerSourceType(int type){
        this.type = type;
    }

    public int value(){
        return type;
    }

}
