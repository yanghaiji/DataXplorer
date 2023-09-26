package com.javayh.agent.common.utils;

import lombok.Data;

/**
 * <p>
 *
 * </p>
 *
 * @author hai ji
 * @version 1.0.0
 * @since 2023-09-25
 */
@Data
public class Result {

    private Integer code;

    private String msg;

    public Result() {
    }

    private Object data;


    public Result(String msg) {
        this.msg = msg;
    }


    public Result(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Result(Integer code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }


    public static Result ok() {
        return new Result(200, "successful");
    }

    public static Result ok(String msg) {
        return new Result(200, msg);
    }


    public static Result ok(Object data) {
        return new Result(200, "successful", data);
    }


    public static Result fail() {
        return new Result(500, "Operation failure");
    }


    public static Result fail(Integer code) {
        return new Result(code, "Operation failure");
    }

    public static Result fail(String msg) {
        return new Result(500, msg);
    }


}
