package com.aib906.freyr.common.bean;

import lombok.Data;

/**
 * @Author: zjs
 * @Date: 2021/1/29 11:41 上午
 */
@Data
public class RestResult<T> {

    private T data;

    private Integer code;

    private String msg;

    public RestResult(T data, Integer code, String msg) {
        this.data = data;
        this.code = code;
        this.msg = msg;
    }

    public RestResult(T data, Integer code) {
        this.data = data;
        this.code = code;
    }


    public RestResult(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static RestResult success() {
        return new RestResult(null, 200, "succeed");
    }

    public static <T> RestResult<T> success(T data) {
        return new RestResult(data, 200);
    }

    public static RestResult error(String msg) {
        return new RestResult(-1, msg);
    }

    public static RestResult error(Integer code, String msg) {
        return new RestResult(code, msg);
    }

}
