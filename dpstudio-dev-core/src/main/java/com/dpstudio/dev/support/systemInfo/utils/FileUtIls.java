package com.dpstudio.dev.support.systemInfo.utils;

import net.ymate.platform.commons.MathCalcHelper;

/**
 * @Author: mengxiang.
 * @Date: 2020/11/21.
 * @Time: 3:30 下午.
 * @Description:
 */
public class FileUtIls {

    private static final int GB = 1024 * 1024 * 1024;
    private static final int MB = 1024 * 1024;
    private static final int KB = 1024;

    public static String sizeStr(long size) {
        String resultSize;
        if (size / GB >= 1) {
            resultSize = MathCalcHelper.bind(size).divide(GB).scale(2).round().toBlurObject().toStringValue() + "GB ";
        } else if (size / MB >= 1) {
            //如果当前Byte的值大于等于1MB
            resultSize = MathCalcHelper.bind(size).divide(MB).scale(2).round().toBlurObject().toStringValue() + "MB ";
        } else if (size / KB >= 1) {
            //如果当前Byte的值大于等于1KB
            resultSize = MathCalcHelper.bind(size).divide(KB).scale(2).round().toBlurObject().toStringValue() + "KB ";
        } else {
            resultSize = size + "B ";
        }
        return resultSize;
    }
}
