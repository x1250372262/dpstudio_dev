package com.dpstudio.dev.model;


import com.dpstudio.dev.core.R;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: 徐建鹏.
 * @Date: 2020/10/20.
 * @Time: 12:01 下午.
 * @Description:
 */
public interface IModelHandler {

    /**
     * 返回结果
     * @param currentUrl
     * @param filterUrl
     * @return
     * @throws Exception
     */
    R result(String currentUrl, String filterUrl, HttpServletRequest request) throws Exception;
}
