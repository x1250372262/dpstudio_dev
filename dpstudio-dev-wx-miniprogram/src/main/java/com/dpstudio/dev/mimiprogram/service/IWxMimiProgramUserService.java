package com.dpstudio.dev.mimiprogram.service;

import com.dpstudio.dev.core.CommonResult;

/**
 * @Author: 徐建鹏.
 * @Date: 2020/4/2.
 * @Time: 4:51 下午.
 * @Description:
 */
public interface IWxMimiProgramUserService {

    /**
     * 获取session
     * @param code
     * @return
     * @throws Exception
     */
    CommonResult getSession(String code) throws Exception;

    /**
     * 用户信息
     *
     * @param sessionKey
     * @param rawData
     * @param signature
     * @param encrypteData
     * @param iv
     * @return
     * @throws Exception
     */
    CommonResult userInfo(String sessionKey, String rawData, String signature, String encrypteData, String iv) throws Exception;

    /**
     * 手机号信息
     * @param encryptedData
     * @param ivStr
     * @return
     * @throws Exception
     */
    CommonResult mobileInfo(String token,String sessionKey,String encryptedData, String ivStr) throws Exception;
}
