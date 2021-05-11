package com.mx.dev.server.undertow;

import com.mx.dev.server.IServer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @Author: mengxiang.
 * @Date: 2020/1/8.
 * @Time: 5:38 下午.
 * @Description:
 */
public class UndertowServer implements IServer {

    private static final Log log = LogFactory.getLog(UndertowServer.class);

    @Override
    public void startUp() {

        System.out.println(" ymp begin run by undertow ... ");
        try {
            UndertowApplication.get().startUp();
        } catch (Exception e) {
            e.printStackTrace();
            log.error(" ymp run fail  ");
        }
    }
}
