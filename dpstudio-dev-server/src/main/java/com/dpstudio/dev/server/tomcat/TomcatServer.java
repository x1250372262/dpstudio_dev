package com.dpstudio.dev.server.tomcat;

import com.dpstudio.dev.server.IServer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @Author: mengxiang.
 * @Date: 2020/1/8.
 * @Time: 5:38 下午.
 * @Description:
 */
public class TomcatServer implements IServer {

    private static final Log log = LogFactory.getLog(TomcatServer.class);

    @Override
    public void startUp() {

        System.out.println(" ymp begin run by tomcat ... ");
        try {
            TomcatApplication.get().startUp();
        } catch (Exception e) {
            e.printStackTrace();
            log.error(" ymp run fail  ");
        }
    }
}
