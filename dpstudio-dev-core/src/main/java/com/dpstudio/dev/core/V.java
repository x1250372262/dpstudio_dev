package com.dpstudio.dev.core;

import net.ymate.platform.webmvc.util.WebResult;
import net.ymate.platform.webmvc.view.IView;
import org.apache.commons.lang.NullArgumentException;


/**
 * @Author: 徐建鹏.
 * @Date: 2019-04-23.
 * @Time: 17:49.¬
 * @Description: 通用返回结果
 */
public class V {

    /**
     * 成功view
     */
    public static IView ok() {
        return view(R.ok());
    }

    /**
     * 失败view
     */
    public static IView failJson() {
        return view(R.fail());
    }


    public static IView view(R r) {
        if (r == null) {
            throw new NullArgumentException("r");
        }
        return WebResult.create(r.code()).msg(r.msg()).attrs(r.attrs()).keepNullValue().toJsonView();
    }
}
