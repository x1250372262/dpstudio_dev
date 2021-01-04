package com.dpstudio.dev.support.junit;

import net.ymate.platform.core.YMP;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;


/**
 * @Author: 徐建鹏.
 * @create: 2021-01-04 17:58
 * @Description:
 */
public class YmpJUnit4ClassRunner extends BlockJUnit4ClassRunner {

    /**
     * Creates a BlockJUnit4ClassRunner to run {@code klass}
     *
     * @param klass
     * @throws InitializationError if the test class is malformed.
     */
    public YmpJUnit4ClassRunner(Class<?> klass) throws InitializationError {
        super(klass);
        try {
            YMP.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
        YMP.get().getBeanFactory().registerBean(klass);
    }
}
