package com.dpstudio.dev.security;

/**
 * ISecurity
 * <br>
 * Code generation by yMateScaffold on 2019/05/05 下午 16:04
 *
 * @author ymatescaffold
 * @version 1.0
 */
public interface ISecurityModuleCfg {

    /**
     * @return 权限包名
     */
    String getPackageName();

    /**
     * @return 权限认证接口
     */
    IAuthenticator getAuthenticator();


    boolean isDisabled();
}