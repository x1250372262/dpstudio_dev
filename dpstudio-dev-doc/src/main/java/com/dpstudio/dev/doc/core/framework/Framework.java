package com.dpstudio.dev.doc.core.framework;


import com.dpstudio.dev.doc.core.model.ApiModule;

import java.util.List;

/**
 * @author 徐建鹏
 * @Date 2020.01.02.
 * @Time: 14:30.
 * @Description:
 */
public interface Framework {

    /**
     * 扩展API数据
     *
     * @param apiModules 原始基本的Api数据
     * @return 扩展后的api数据
     */
    List<ApiModule> extend(List<ApiModule> apiModules);

    /**
     * 判断该类是否适合该框架
     *
     * @param classz 扫描到的类
     * @return 是支持
     */
    boolean support(Class<?> classz);
}
