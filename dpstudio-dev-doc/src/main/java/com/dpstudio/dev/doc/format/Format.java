package com.dpstudio.dev.doc.format;


import com.dpstudio.dev.doc.bean.ApiResult;

/**
 * @author mengxiang
 * @Date 2020.01.02.
 * @Time: 14:30.
 * @Description: 文档输出格式
 */
public interface Format {

    /**
     * 文档格式化生成
     * @param apiResult
     * @return
     */
    String format(ApiResult apiResult);
}
