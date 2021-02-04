package com.aib906.freyr.common.exception;

/**
 * @Author: zjs
 * @Date: 2021/2/1 7:43 下午
 */
public class FreyrPluginException extends RuntimeException{

    public FreyrPluginException(String message) {
        super(message);
    }

    public FreyrPluginException(String message, Throwable cause) {
        super(message, cause);
    }

    public FreyrPluginException(Throwable cause) {
        super(cause);
    }
}
