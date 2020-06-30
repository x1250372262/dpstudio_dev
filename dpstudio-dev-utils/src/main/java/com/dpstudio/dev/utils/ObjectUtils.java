package com.dpstudio.dev.utils;


import net.ymate.platform.commons.lang.BlurObject;

import java.util.Objects;

/**
 * @Author: 徐建鹏.
 * @Date: 2019-07-20.
 * @Time: 14:57.
 * @Description: object工具类
 */
public class ObjectUtils {

    public static String get(Object value, String defaultValue) {
        return Objects.isNull(value) ? defaultValue : BlurObject.bind(value).toStringValue();
    }

    public static int get(Object value, int defaultValue) {
        return Objects.isNull(value) ? defaultValue : BlurObject.bind(value).toIntValue();
    }

    public static long get(Object value, long defaultValue) {
        return Objects.isNull(value) ? defaultValue : BlurObject.bind(value).toLongValue();
    }

    public static boolean get(Object value, boolean defaultValue) {
        return Objects.isNull(value) ? defaultValue : BlurObject.bind(value).toBooleanValue();
    }
}
