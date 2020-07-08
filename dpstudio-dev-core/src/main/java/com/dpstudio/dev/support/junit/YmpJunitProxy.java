package com.dpstudio.dev.support.junit;


import net.ymate.platform.core.YMP;
import org.junit.After;
import org.junit.Before;

/**
 * @author mengxiang
 * @Date .
 * @Time: .
 * @Description: ymp单元测试类
 */
public class YmpJunitProxy {

    @Before
    public void init() throws Throwable {
        YMP.run();
    }

    @After
    public void destroy() throws Throwable {
        YMP.destroy();
    }

}
