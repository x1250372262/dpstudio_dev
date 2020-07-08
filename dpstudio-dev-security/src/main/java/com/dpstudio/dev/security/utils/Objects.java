package com.dpstudio.dev.security.utils;


import net.ymate.platform.commons.lang.BlurObject;
import net.ymate.platform.core.configuration.IConfigFileParser;

/**
 * @Author: mengxiang.
 * @Date: 2020/6/17.
 * @Time: 6:08 下午.
 * @Description:
 */
public class Objects {

    public static String get(IConfigFileParser.Attribute value, String defaultValue) {
        return java.util.Objects.isNull(value) ? defaultValue : BlurObject.bind(value.getValue()).toStringValue();
    }

    public static int get(IConfigFileParser.Attribute value, int defaultValue) {
        return java.util.Objects.isNull(value) ? defaultValue : BlurObject.bind(value.getValue()).toIntValue();
    }

    public static long get(IConfigFileParser.Attribute value, long defaultValue) {
        return java.util.Objects.isNull(value) ? defaultValue : BlurObject.bind(value.getValue()).toLongValue();
    }

}
