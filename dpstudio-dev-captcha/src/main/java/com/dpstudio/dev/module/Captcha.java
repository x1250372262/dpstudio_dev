package com.dpstudio.dev.module;

import com.dpstudio.dev.module.impl.DefaultCaptchaModuleCfg;
import net.ymate.module.captcha.CaptchaTokenBean;
import net.ymate.module.captcha.ICaptchaScopeProcessor;
import net.ymate.platform.core.Version;
import net.ymate.platform.core.YMP;
import net.ymate.platform.core.module.IModule;
import net.ymate.platform.core.module.annotation.Module;
import net.ymate.platform.core.util.UUIDUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Captcha
 * <br>
 * Code generation by yMateScaffold on 2020/04/14 上午 11:14
 *
 * @author ymatescaffold
 * @version 1.0
 */
@Module
public class Captcha implements IModule, ICaptcha {

    private static final Log _LOG = LogFactory.getLog(Captcha.class);

    public static final Version VERSION = new Version(1, 0, 0, Captcha.class.getPackage().getImplementationVersion(), Version.VersionType.Alphal);

    private static volatile ICaptcha __instance;

    private YMP __owner;

    private ICaptchaModuleCfg __moduleCfg;

    private  net.ymate.module.captcha.ICaptchaModuleCfg iCaptchaModuleCfg;

    private boolean __inited;

    public static ICaptcha get() {
        if (__instance == null) {
            synchronized (VERSION) {
                if (__instance == null) {
                    __instance = YMP.get().getModule(Captcha.class);
                }
            }
        }
        return __instance;
    }

    public static ICaptcha get(YMP owner) {
        return owner.getModule(Captcha.class);
    }

    @Override
    public String getName() {
        return ICaptcha.MODULE_NAME;
    }

    @Override
    public void init(YMP owner) throws Exception {
        if (!__inited) {
            //
            _LOG.info("Initializing dpstudio-dev-captcha-" + VERSION);
            //
            __owner = owner;
            __moduleCfg = new DefaultCaptchaModuleCfg(owner);
            iCaptchaModuleCfg = net.ymate.module.captcha.Captcha.get().getModuleCfg();
            //
            // Here to write your code
            //
            __inited = true;
        }
    }

    @Override
    public boolean isInited() {
        return __inited;
    }

    @Override
    public ICodeHandler getCodeHandler() {
        return __moduleCfg.codeHandler();
    }

    @Override
    public CaptchaTokenBean getCaptchaToken(net.ymate.module.captcha.ICaptcha.Type type, String scope, String target) throws Exception {

        CaptchaTokenBean tokenBean = iCaptchaModuleCfg.getStorageAdapter().load(scope);
        if (tokenBean == null || !StringUtils.equalsIgnoreCase(tokenBean.getTarget(), target)
                || (iCaptchaModuleCfg.getTokenTimeout() != null && System.currentTimeMillis() - tokenBean.getCreateTime() >= iCaptchaModuleCfg.getTokenTimeout())) {
            tokenBean = generate(scope, target);
            //
            if (tokenBean != null) {
                if (iCaptchaModuleCfg.isDevelopMode()) {
                    _LOG.debug("Generate captcha['" + scope + "']: " + tokenBean.getToken());
                }
            }
        }
        return tokenBean;
    }

    private String generateToken() {
        if (__moduleCfg.codeGenerator() != null) {
            return __moduleCfg.codeGenerator().generate();
        }
        return null;
    }

    private CaptchaTokenBean generate(String scope, String target) throws Exception{
        if (!iCaptchaModuleCfg.isDisabled()) {
            String _token = generateToken();
            if (StringUtils.isBlank(_token)) {
                _token = UUIDUtils.randomStr(iCaptchaModuleCfg.getTokenLengthMin(), true);
            }
            return iCaptchaModuleCfg.getStorageAdapter().saveOrUpdate(scope, target, _token);
        } else if (_LOG.isWarnEnabled()) {
            _LOG.warn("Captcha module has been disabled.");
        }
        return null;
    }

    @Override
    public void destroy() throws Exception {
        if (__inited) {
            __inited = false;
            //
            // Here to write your code
            //
            __moduleCfg = null;
            __owner = null;
        }
    }
    @Override
    public YMP getOwner() {
        return __owner;
    }

    @Override
    public ICaptchaModuleCfg getModuleCfg() {
        return __moduleCfg;
    }
}
