package com.dpstudio.dev.support.spi.exception;

/**
 * @Author: mengxiang.
 * @Date: 2020/5/26.
 * @Time: 8:44 上午.
 * @Description:
 */
public class SpiException extends RuntimeException{

    public SpiException() {
        super();
    }
    public SpiException(String msg) {
        super(msg);
    }
    public SpiException(String msg,Throwable e) {
        super(msg,e);
    }
}
