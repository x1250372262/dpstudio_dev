package com.dpstudio.dev.doc.core.format;

import com.dpstudio.dev.doc.core.model.ApiDoc;

/**
  * @author 徐建鹏
  * @Date 2020.01.02.
  * @Time: 14:30.
  * @Description: 文档输出格式
  */
public interface Format {

    String format(ApiDoc apiDoc);
}
