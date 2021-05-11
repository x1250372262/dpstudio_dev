package com.mx.dev.support.spi;

import com.mx.dev.support.spi.annotation.SpiInject;
import com.mx.dev.support.spi.exception.SpiException;
import net.ymate.platform.core.beans.IBeanFactory;
import net.ymate.platform.core.beans.IBeanInjector;
import net.ymate.platform.core.beans.annotation.Injector;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * @author mengxiang
 * @Date .
 * @Time: .
 * @Description: 动态配置spi
 */
@Injector(SpiInject.class)
public class SpiInjector implements IBeanInjector {

    @Override
    public Object inject(IBeanFactory beanFactory, Annotation annotation, Class<?> targetClass, Field field, Object originInject) {
        //返回的值
        Object obj = null;
        SpiInject spiInject = field.getAnnotation(SpiInject.class);
        if (spiInject != null) {
            try {
                obj = SpiLoader.load(field.getType(), spiInject.className());
            } catch (Exception e) {
                throw new SpiException("spi注入失败", e);
            }
        }
        if (obj == null) {
            obj = originInject;
        }
        return obj;
    }
}
