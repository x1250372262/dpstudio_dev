package com.dpstudio.dev.mimiprogram.impl;

import com.dpstudio.dev.mimiprogram.WxMimiProgram;
import net.ymate.module.wechat.AbstractWechatTokenCacheAdapter;
import net.ymate.module.wechat.base.WechatAccessToken;
import net.ymate.module.wechat.base.WechatAccountMeta;
import net.ymate.module.wechat.base.WechatTicket;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author: 徐建鹏.
 * @Date: 2020/4/29.
 * @Time: 1:17 下午.
 * @Description:
 */
public class WxMimiProgramTokenCacheAdapter extends AbstractWechatTokenCacheAdapter {
    private static final Log LOG = LogFactory.getLog(WxMimiProgramTokenCacheAdapter.class);

    private final Map<String, WechatAccessToken> TOKEN_CACHES = new ConcurrentHashMap<String, WechatAccessToken>();

    private static final ReentrantLock LOCKS = new ReentrantLock();

    public WechatAccessToken getAccessToken(WechatAccountMeta accountMeta) {
        String appId = WxMimiProgram.get().getModuleCfg().appId();
        String appSecret = WxMimiProgram.get().getModuleCfg().appSecret();
        WechatAccessToken token = TOKEN_CACHES.get(appId);
        if (token == null || token.isExpired()) {
            LOCKS.lock();
            try {
                token = __doGetAccessToken(appId, appSecret);
                if (token != null) {
                    TOKEN_CACHES.put(appId, token);
                }
            } catch (Exception e) {
                try {
                    token = __doGetAccessToken(appId, appSecret);
                    if (token != null) {
                        TOKEN_CACHES.put(appId, token);
                    }
                } catch (Exception ex) {
                    LOG.warn("", ex);
                }
            } finally {
                LOCKS.unlock();
            }
        }
        return token;
    }

    public WechatTicket getJsTicket(WechatAccountMeta accountMeta) {
        return null;
    }
}
