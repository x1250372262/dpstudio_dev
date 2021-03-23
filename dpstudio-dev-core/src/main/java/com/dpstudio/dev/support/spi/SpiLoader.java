package com.dpstudio.dev.support.spi;


import com.dpstudio.dev.support.spi.annotation.SpiBean;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;
import java.util.ServiceLoader;

/**
 * @author mengxiang
 * @Date 2020.06.30
 * @Time: 17:30
 * @Description: spi加载器
 */
public class SpiLoader {


    /**
     * 加载
     *
     * @param classes
     * @param className
     * @param <T>
     * @return
     */
    public static <T> T load(Class<T> classes, String className) throws Exception {
        return serviceLoad(classes, className);
    }

    /**
     * 加载
     *
     * @param classes
     * @param <T>
     * @return
     */
    public static <T> T load(Class<T> classes) throws Exception {
        return load(classes,null);
    }

    /**
     * ServiceLoader加载
     *
     * @param classes
     * @param className
     * @param <T>
     * @return
     */
    public static <T> T serviceLoad(Class<T> classes, String className) {
        ServiceLoader<T> serviceLoader = ServiceLoader.load(classes);
        T returnValue = null;
        for (T t : serviceLoader) {
            SpiBean spi = t.getClass().getAnnotation(SpiBean.class);
            if (spi == null) {
                continue;
            }
            if (StringUtils.isBlank(className)) {
                returnValue = t;
                break;
            }
            String spiClassName = StringUtils.defaultIfBlank(spi.className(), t.getClass().getName());
            if (Objects.equals(spiClassName, className)) {
                returnValue = t;
            }
        }
        return returnValue;
    }
}
