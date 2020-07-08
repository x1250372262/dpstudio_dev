package com.dpstudio.dev.doc.format;


import com.dpstudio.dev.doc.bean.ApiResult;

/**
 * @author 徐建鹏
 * @Date 2020.01.02.
 * @Time: 14:30.
 * @Description: 文档输出格式
 */
public interface Format {

    String format(ApiResult apiResult);
}
