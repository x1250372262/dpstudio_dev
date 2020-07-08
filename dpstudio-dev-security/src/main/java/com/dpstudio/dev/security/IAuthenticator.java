package com.dpstudio.dev.security;

import java.util.List;

/**
 * @Author: mengxiang.
 * @Date: 2019-05-06.
 * @Time: 09:00.
 * @Description: 认证接口
 */
public interface IAuthenticator {

    /**
     * 返回当前用户是不是总管理员
     * 
     * @return 返回当前用户是不是总管理员
     * @throws Exception
     */
    boolean isFounder() throws Exception;

    /**
     * 返回用户拥有的权限码
     * 
     * @return 返回用户拥有的权限码
     * @throws Exception
     */
    List<String> userPermissions() throws Exception;

    class DefaultAuthenticator implements IAuthenticator {

        @Override
        public boolean isFounder() throws Exception {
            return false;
        }

        @Override
        public List<String> userPermissions() throws Exception {
            return null;
        }
    }
}
