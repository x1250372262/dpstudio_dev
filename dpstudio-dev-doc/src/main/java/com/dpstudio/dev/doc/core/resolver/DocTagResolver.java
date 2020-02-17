package com.dpstudio.dev.doc.core.resolver;


import com.dpstudio.dev.doc.core.framework.Framework;
import com.dpstudio.dev.doc.core.model.ApiModule;

import java.util.List;

/**
 * @author 徐建鹏
 * @Date 2020.01.02.
 * @Time: 14:30.
 * @Description:
 */
public interface DocTagResolver {

    /**
     * 执行解析
     *
     * @param files     要解析的所有java源代码文件的绝对路径
     * @param framework api文档所属框架
     */
    List<ApiModule> resolve(List<String> files, Framework framework);
}
