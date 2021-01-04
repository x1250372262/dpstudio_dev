package com.dpstudio.dev.support.junit;

import net.ymate.platform.core.IApplication;
import net.ymate.platform.core.IApplicationInitializer;
import net.ymate.platform.core.YMP;
import net.ymate.platform.core.beans.BeanMeta;
import net.ymate.platform.core.beans.IBeanFactory;
import net.ymate.platform.core.beans.IBeanLoader;
import net.ymate.platform.core.event.Events;
import net.ymate.platform.core.module.ModuleManager;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;


/**
 * @Author: mengxinag.
 * @create: 2021-01-04 17:58
 * @Description:
 */
public class YmpJUnit4ClassRunner extends BlockJUnit4ClassRunner {

    private final Class<?> targetClass;

    private Object target;

    /**
     * Creates a BlockJUnit4ClassRunner to run {@code klass}
     *
     * @param klass
     * @throws InitializationError if the test class is malformed.
     */
    public YmpJUnit4ClassRunner(Class<?> klass) throws InitializationError {
        super(klass);
        this.targetClass = klass;

        try {
            YMP.run(new YmpJunitApplicationInitializer());
            this.target = YMP.get().getBeanFactory().getBean(targetClass);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Object createTest() throws Exception {
        return this.target;
    }

    private class YmpJunitApplicationInitializer implements IApplicationInitializer {
        @Override
        public void afterEventInit(IApplication application, Events events) {

        }

        @Override
        public void beforeBeanLoad(IApplication application, IBeanLoader beanLoader) {

        }

        @Override
        public void beforeModuleManagerInit(IApplication application, ModuleManager moduleManager) {

        }

        @Override
        public void beforeBeanFactoryInit(IApplication application, IBeanFactory beanFactory) {
            BeanMeta beanMeta = BeanMeta.create(targetClass, true);
            beanFactory.registerBean(beanMeta);
        }
    }

}
