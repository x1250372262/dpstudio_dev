package com.dpstudio.dev.utils;

import net.ymate.platform.core.lang.BlurObject;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;

/**
 * @Author: 徐建鹏.
 * @Date: 2019-07-20.
 * @Time: 14:57.
 * @Description: object工具类
 */
public class ObjectUtils {

    public static boolean isNull(Object obj) {
        return obj == null;
    }

    public static boolean isNotNull(Object obj) {
        return !isNull(obj);
    }

    public static boolean isEmpty(Object obj) {
        if (obj == null){
            return true;
        }
        else if (obj instanceof CharSequence) {
            return ((CharSequence) obj).length() == 0;
        }
        else if (obj instanceof Collection) {
            return ((Collection) obj).isEmpty();
        }
        else if (obj instanceof Map) {
            return ((Map) obj).isEmpty();
        }
        else if (obj.getClass().isArray()) {
            return Array.getLength(obj) == 0;
        }
        return false;
    }

    public static boolean isNotEmpty(Object obj) {
        return !isEmpty(obj);
    }


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

    public static String toStr(Object value) {
        return Objects.isNull(value) ? null : BlurObject.bind(value).toStringValue();
    }

    public static int toInt(Object value) {
        return Objects.isNull(value) ? 0 : BlurObject.bind(value).toIntValue();
    }

    public static long toLong(Object value) {
        return Objects.isNull(value) ? 0L : BlurObject.bind(value).toLongValue();
    }

}
