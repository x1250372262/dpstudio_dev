package com.dpstudio.dev.junit;


import net.ymate.platform.core.YMP;
import org.junit.After;
import org.junit.Before;

/**
 * @author 徐建鹏
 * @Date .
 * @Time: .
 * @Description: ymp单元测试类
 */
public class YmpJunitProxy {

    @Before
    public void init() throws Throwable {
        YMP.get().init();
    }

    @After
    public void destroy() throws Throwable {
        YMP.get().destroy();
    }

}
