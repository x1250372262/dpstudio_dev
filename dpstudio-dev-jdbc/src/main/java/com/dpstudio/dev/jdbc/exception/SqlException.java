package com.dpstudio.dev.jdbc.exception;


/**
 * @Author: 徐建鹏.
 * @Date: 2019-07-11.
 * @Time: 09:34.
 * @Description:
 */
public class SqlException extends Exception {

    public SqlException() {
        super();
    }
    public SqlException(String msg) {
        super(msg);
    }
}
