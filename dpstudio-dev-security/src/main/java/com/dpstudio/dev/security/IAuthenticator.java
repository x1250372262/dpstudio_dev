package com.dpstudio.dev.security;

import java.util.List;

/**
 * @Author: 徐建鹏.
 * @Date: 2019-05-06.
 * @Time: 09:00.
 * @Description: 认证接口
 */
public interface IAuthenticator {

    /**
     * @return 返回当前用户是不是总管理员
     * @throws Exception
     */
    boolean isFounder() throws Exception;

    /**
     * @return 返回用户拥有的权限码
     * @throws Exception
     */
    List<String> userPermission() throws Exception;
}
