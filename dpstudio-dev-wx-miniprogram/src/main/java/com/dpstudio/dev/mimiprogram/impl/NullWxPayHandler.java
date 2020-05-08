package com.dpstudio.dev.mimiprogram.impl;

import net.ymate.payment.wxpay.IWxPay;
import net.ymate.payment.wxpay.IWxPayEventHandler;
import net.ymate.payment.wxpay.base.WxPayAccountMeta;
import net.ymate.payment.wxpay.base.WxPayNotifyResponse;
import net.ymate.payment.wxpay.request.WxPayUnifiedOrder;

/**
 * @Author: 徐建鹏.
 * @Date: 2020/5/6.
 * @Time: 3:02 下午.
 * @Description:
 */
public class NullWxPayHandler implements IWxPayEventHandler {
    @Override
    public WxPayUnifiedOrder buildUnifiedOrderRequest(WxPayAccountMeta accountMeta, IWxPay.TradeType tradeType, String orderId, String attach) throws Exception {
        return null;
    }

    @Override
    public void onNotifyReceived(WxPayNotifyResponse notifyData) throws Exception {

    }

    @Override
    public boolean onReturnCallback(String orderId) throws Exception {
        return false;
    }

    @Override
    public void onExceptionCaught(Throwable cause) throws Exception {

    }

    @Override
    public String getJsApiTicket(String appId) throws Exception {
        return null;
    }
}
