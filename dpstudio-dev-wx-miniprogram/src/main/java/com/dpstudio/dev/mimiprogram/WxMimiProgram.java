package com.dpstudio.dev.mimiprogram;

import com.alibaba.fastjson.JSONObject;
import com.dpstudio.dev.mimiprogram.bean.WxCodeSession;
import com.dpstudio.dev.mimiprogram.bean.WxPhoneInfo;
import com.dpstudio.dev.mimiprogram.bean.WxUserInfo;
import com.dpstudio.dev.mimiprogram.impl.DefaultWxMimiProgramModuleCfg;
import com.dpstudio.dev.mimiprogram.utils.PKCS7Encoder;
import com.dpstudio.dev.mimiprogram.utils.QRCodeHelper;
import net.ymate.framework.commons.HttpClientHelper;
import net.ymate.framework.commons.IHttpResponse;
import net.ymate.module.wechat.Wechat;
import net.ymate.module.wechat.base.WechatAccessToken;
import net.ymate.platform.core.Version;
import net.ymate.platform.core.YMP;
import net.ymate.platform.core.lang.BlurObject;
import net.ymate.platform.core.module.IModule;
import net.ymate.platform.core.module.annotation.Module;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.AlgorithmParameters;
import java.util.HashMap;
import java.util.Map;

/**
 * DemoModule
 */
@Module
public class WxMimiProgram implements IModule, IWxMimiProgram {

    private static final Log _LOG = LogFactory.getLog(WxMimiProgram.class);

    public static final Version VERSION = new Version(1, 0, 0, WxMimiProgram.class.getPackage().getImplementationVersion(), Version.VersionType.Alphal);

    private static volatile IWxMimiProgram __instance;

    private YMP __owner;

    private IWxMimiProgramModuleCfg __moduleCfg;

    private boolean __inited;

    public static IWxMimiProgram get() {
        if (__instance == null) {
            synchronized (VERSION) {
                if (__instance == null) {
                    __instance = YMP.get().getModule(WxMimiProgram.class);
                }
            }
        }
        return __instance;
    }

    public static IWxMimiProgram get(YMP owner) {
        return owner.getModule(WxMimiProgram.class);
    }

    @Override
    public String getName() {
        return IWxMimiProgram.MODULE_NAME;
    }

    @Override
    public void init(YMP owner) throws Exception {
        if (!__inited) {
            //
            _LOG.info("Initializing dpstudio-dev-wx-miniprogram-" + VERSION);
            //
            __owner = owner;
            __moduleCfg = new DefaultWxMimiProgramModuleCfg(owner);
            __inited = true;
        }
    }

    @Override
    public boolean isInited() {
        return __inited;
    }

    @Override
    public void destroy() throws Exception {
        if (__inited) {
            __inited = false;
            __moduleCfg = null;
            __owner = null;
        }
    }

    @Override
    public YMP getOwner() {
        return __owner;
    }

    @Override
    public IWxMimiProgramModuleCfg getModuleCfg() {
        return __moduleCfg;
    }

    /**
     * AES解密
     *
     * @param encryptedData 消息密文
     * @param ivStr         iv字符串
     */
    private String decrypt(String sessionKey, String encryptedData, String ivStr) {
        try {
            AlgorithmParameters params = AlgorithmParameters.getInstance("AES");
            params.init(new IvParameterSpec(Base64.decodeBase64(ivStr)));

            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(Base64.decodeBase64(sessionKey), "AES"), params);

            return new String(PKCS7Encoder.decode(cipher.doFinal(Base64.decodeBase64(encryptedData))), StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException("AES解密失败", e);
        }
    }

    /**
     * 获取session信息
     *
     * @param jsCode
     * @return
     * @throws Exception
     */
    public WxCodeSession getSessionInfo(String jsCode) throws Exception {
        Map<String, String> params = new HashMap<>(8);
        params.put("appid", __moduleCfg.appId());
        params.put("secret", __moduleCfg.appSecret());
        params.put("js_code", jsCode);
        params.put("grant_type", "authorization_code");
        IHttpResponse response = HttpClientHelper.create().get(WX_API.JSCODE_TO_SESSION_URL, params);
        return WxCodeSession.byJson(response.getContent());
    }

    /**
     * 获取用户信息
     *
     * @param sessionKey
     * @param encryptedData 用户敏感信息
     * @param ivStr         解密算法的向量
     * @return
     */
    public WxUserInfo getUserInfo(String sessionKey, String encryptedData, String ivStr) throws Exception {
        return WxUserInfo.byJson(decrypt(sessionKey, encryptedData, ivStr));
    }

    /**
     * 获取access_token
     *
     * @return
     */
    public String getAccessToken() {
        WechatAccessToken token = Wechat.get().getModuleCfg().getTokenCacheAdapter().getAccessToken(null);
        return token == null ? null : token.getToken();
    }

    public String createACodeLimit(String scene) throws Exception {
        JSONObject params = new JSONObject();
        params.put("scene", scene);
        return QRCodeHelper.createACodeLimit(params.toJSONString());
    }

    public String createACodeLimit(String scene, String page, int width, boolean autoColor, String lineColor, boolean isHyaline) throws Exception {
        JSONObject params = new JSONObject();
        params.put("scene", scene);
        params.put("page", page);
        params.put("width", BlurObject.bind(width).toStringValue());
        params.put("auto_color", BlurObject.bind(autoColor).toStringValue());
        params.put("line_color", lineColor);
        params.put("is_hyaline", BlurObject.bind(isHyaline).toStringValue());
        return QRCodeHelper.createACodeLimit(params.toJSONString());
    }

    /**
     * 检查用户信息
     *
     * @param sessionKey
     * @param rawData    用户非敏感信息
     * @param signature  签名
     * @return
     */
    public boolean checkUserInfo(String sessionKey, String rawData, String signature) {
        final String generatedSignature = DigestUtils.sha1Hex(rawData + sessionKey);
        return generatedSignature.equals(signature);
    }

    /**
     * 获取手机信息
     *
     * @param sessionKey
     * @param encryptedData 用户敏感信息
     * @param ivStr         解密算法的向量
     * @return
     */
    public WxPhoneInfo getPhoneNoInfo(String sessionKey, String encryptedData, String ivStr) {
        return WxPhoneInfo.byJson(decrypt(sessionKey, encryptedData, ivStr));
    }

    @Override
    public IWxMimiProgramHandler getHandler() throws Exception {
        return __moduleCfg.handler();
    }
}
