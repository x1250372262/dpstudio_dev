package com.dpstudio.dev.doc.core.utils;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.Objects;

/**
 * @Author: xujianpeng.
 * @Date: 2020/7/2.
 * @Time: 下午1:30.
 * @Description: mock工具类
 */
public class MockUtils {

    /**
     * 生成模拟数据
     *
     * @param type
     * @return
     */
    public static Object createData(String type) {
        if (Objects.equals(int.class.getName(), type) || Objects.equals(Integer.class.getSimpleName(), type)) {
            return (int) ((Math.random() * 9 + 1) * 100000);
        } else if (Objects.equals(long.class.getName(), type) || Objects.equals(Long.class.getSimpleName(), type)) {
            return (long) ((Math.random() * 9 + 1) * 100000);
        } else if (Objects.equals(boolean.class.getName(), type) || Objects.equals(Boolean.class.getSimpleName(), type)) {
            return false;
        } else if (Objects.equals(double.class.getName(), type) || Objects.equals(Double.class.getSimpleName(), type)) {
            return (Math.random() * 9 + 1) * 100000;
        } else if (Objects.equals(float.class.getName(), type) || Objects.equals(Float.class.getSimpleName(), type)) {
            return (float) ((Math.random() * 9 + 1) * 100000);
        } else {
            return RandomStringUtils.randomAlphanumeric(10);
        }
    }

    public static int createDataInt() {
        return (int) ((Math.random() * 9 + 1) * 100000);
    }

}
