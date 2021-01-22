package com.dpstudio.dev.core;

import com.dpstudio.dev.support.UserSession;

import java.util.Optional;

/**
 * @Author: mengxiang.
 * @Date: 2020/11/6.
 * @Time: 2:36 下午.
 * @Description:
 */
public class M {

    /**
     * 获取登陆用户id
     *
     * @return
     */
    public static String userId() {
        return Optional.ofNullable(UserSession.current())
                .map(UserSession::getUid).orElse("1");
    }
}
