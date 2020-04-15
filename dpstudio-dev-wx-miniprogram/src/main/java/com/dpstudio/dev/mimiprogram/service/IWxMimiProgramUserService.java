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
     * 授权
     *
     * @param code
     * @param rawData
     * @param signature
     * @param encrypteData
     * @param iv
     * @return
     * @throws Exception
     */
    CommonResult oauth(String scope, String code, String rawData, String signature, String encrypteData, String iv) throws Exception;
}
