package com.dpstudio.dev.mimiprogram;


import com.dpstudio.dev.mimiprogram.bean.WxCodeSession;
import com.dpstudio.dev.mimiprogram.bean.WxPhoneInfo;
import com.dpstudio.dev.mimiprogram.bean.WxUserInfo;
import net.ymate.platform.core.YMP;

/**
 * @author 徐建鹏
 * @Date 2020.04.02.
 * @Time: .
 * @Description: 微信小程序模块
 */
public interface IWxMimiProgram {

    String MODULE_NAME = "dpstudio.wx_mimiprogram";

    /**
     * @return 返回所属YMP框架管理器实例
     */
    YMP getOwner();

    /**
     * @return 返回模块配置对象
     */
    IWxMimiProgramModuleCfg getModuleCfg();

    /**
     * @return 返回模块是否已初始化
     */
    boolean isInited();

    /**
     * 获取session信息
     *
     * @param jsCode
     * @return
     * @throws Exception
     */
    WxCodeSession getSessionInfo(String jsCode) throws Exception;

    /**
     * 获取用户信息
     *
     * @param sessionKey
     * @param encryptedData 用户敏感信息
     * @param ivStr         解密算法的向量
     * @return
     */
    WxUserInfo getUserInfo(String sessionKey, String encryptedData, String ivStr) throws Exception;


    /**
     * 获取access_token
     *
     * @return
     */
    String getAccessToken();

    /**
     * 获取小程序码 业务数量多 永久有效 参数限制
     *
     * @return
     * @throws Exception
     */
    String createACodeLimit(String scene) throws Exception;

    /**
     * 获取小程序码 业务数量多 永久有效 参数限制
     *
     * @return
     * @throws Exception
     */
    String createACodeLimit(String scene, String page, int width, boolean autoColor, String lineColor, boolean isHyaline) throws Exception;


    /**
     * 检查用户信息
     *
     * @param sessionKey
     * @param rawData    用户非敏感信息
     * @param signature  签名
     * @return
     */
    boolean checkUserInfo(String sessionKey, String rawData, String signature) throws Exception;

    /**
     * 获取手机信息
     *
     * @param sessionKey
     * @param encryptedData 用户敏感信息
     * @param ivStr         解密算法的向量
     * @return
     */
    WxPhoneInfo getPhoneNoInfo(String sessionKey, String encryptedData, String ivStr) throws Exception;

    /**
     * 获取数据处理信息
     *
     * @return
     * @throws Exception
     */
    IWxMimiProgramHandler getHandler() throws Exception;

    /**
     * 获取支付处理
     *
     * @return
     * @throws Exception
     */
    IWxMimiProgramPayHandler getPayHandler() throws Exception;

    /**
     * api接口
     */
    interface WX_API {
        /**
         * code获取session信息
         */
        String JSCODE_TO_SESSION_URL = "https://api.weixin.qq.com/sns/jscode2session";
    }
}
