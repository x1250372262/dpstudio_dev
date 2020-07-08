package com.dpstudio.dev.doc.analysis;


import com.dpstudio.dev.doc.bean.ApiModule;

import java.util.List;

/**
 * @author mengxiang
 * @Date 2020.01.02.
 * @Time: 14:30.
 * @Description: 数据解析
 */
public interface IDocAnalysis {

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
