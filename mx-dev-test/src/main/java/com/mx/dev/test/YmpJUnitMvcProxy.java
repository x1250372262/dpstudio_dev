package com.dpstudio.dev.test;

import com.dpstudio.dev.test.server.UndertowServer;
import org.junit.Before;

/**
 * @Author: mengxiang.
 * @create: 2021-01-06 15:51
 * @Description:
 */
public abstract class YmpJUnitMvcProxy {

    @Before
    public void start(){
        try {
            UndertowServer.get().startUp();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
