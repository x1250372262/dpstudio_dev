package com.dpstudio.dev.spi;

import org.apache.commons.lang.StringUtils;

import java.util.Objects;
import java.util.ServiceLoader;

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
        T returnObject = serviceLoad(classes, className);
        if (returnObject != null) {
            return returnObject;
        }
        return null;
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
            Spi spi = t.getClass().getAnnotation(Spi.class);
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
