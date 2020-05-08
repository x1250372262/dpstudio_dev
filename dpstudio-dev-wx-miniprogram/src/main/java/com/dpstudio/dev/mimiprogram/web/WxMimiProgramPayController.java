package com.dpstudio.dev.mimiprogram.web;

import com.dpstudio.dev.core.CommonResult;
import com.dpstudio.dev.core.code.CommonCode;
import com.dpstudio.dev.mimiprogram.IWxMimiProgramPayHandler;
import com.dpstudio.dev.mimiprogram.WxMimiProgram;
import net.ymate.payment.wxpay.IWxPay;
import net.ymate.payment.wxpay.WxPay;
import net.ymate.payment.wxpay.WxPayException;
import net.ymate.payment.wxpay.base.WxPayAccountMeta;
import net.ymate.payment.wxpay.base.WxPayBaseData;
import net.ymate.payment.wxpay.base.WxPayNotifyResponse;
import net.ymate.payment.wxpay.request.WxPayOrderQuery;
import net.ymate.payment.wxpay.request.WxPayUnifiedOrder;
import net.ymate.payment.wxpay.support.WxPayRequestProcessor;
import net.ymate.platform.core.util.DateTimeUtils;
import net.ymate.platform.core.util.RuntimeUtils;
import net.ymate.platform.log.Logs;
import net.ymate.platform.validation.validate.VRequired;
import net.ymate.platform.webmvc.annotation.*;
import net.ymate.platform.webmvc.base.Type;
import net.ymate.platform.webmvc.util.WebResult;
import net.ymate.platform.webmvc.view.IView;
import net.ymate.platform.webmvc.view.impl.HttpStatusView;
import net.ymate.platform.webmvc.view.impl.TextView;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: 徐建鹏.
 * @Date: 2018/4/16.
 * @Time: 21:40.
 * @Description:
 */
@Controller
@RequestMapping("/dpstudio/wx/mimiprogram/pay")
public class WxMimiProgramPayController {

    @RequestMapping(value = "/", method = {Type.HttpMethod.GET, Type.HttpMethod.POST})
    public IView pay(@RequestParam("app_id") String appId,
                     @VRequired @RequestParam String state,
                     @RequestParam String attach) throws Exception {
        IWxMimiProgramPayHandler eventHandler = WxMimiProgram.get().getPayHandler();
        if (eventHandler != null) {
            WxPayAccountMeta meta = WxPay.get().getModuleCfg().getAccountProvider().getAccount(appId);
            if (meta != null) {
                CommonResult commonResult = eventHandler.checkPay(attach);
                if (commonResult != null && commonResult.code() != CommonCode.COMMON_OPTION_SUCCESS.getCode()) {
                    return commonResult.toJsonView();
                }
                WxPayUnifiedOrder _request = eventHandler.buildUnifiedOrderRequest(meta, IWxPay.TradeType.JSAPI, state, attach);
                WxPayUnifiedOrder.Response _response = _request.execute();
                //
                if (_response.checkReturnCode() && _response.checkResultCode() && (WxPay.get().getModuleCfg().isSignCheckDisabled() || _response.checkSignature(meta.getMchKey()))) {
                    String _timestamp = DateTimeUtils.currentTimeUTC() + "";
                    String _nonceStr = WxPayBaseData.__doCreateNonceStr();
                    // 封装基于JSAPI的支付调用相关参数
                    Map<String, Object> _paramMap = new HashMap<String, Object>();
                    _paramMap.put("appId", meta.getAppId());
                    _paramMap.put("timeStamp", _timestamp);
                    _paramMap.put("nonceStr", _nonceStr);
                    _paramMap.put("package", "prepay_id=" + _response.prepayId());
                    _paramMap.put("signType", IWxPay.Const.SIGN_TYPE_MD5);
                    _paramMap.put("paySign", WxPayBaseData.__doCreateSignature(_paramMap, meta.getMchKey()));
                    //
                    return WebResult.succeed().attr("data", _paramMap)
                            .attr("trade_no", state).toJSON();

                }
            }
        }
        return CommonResult.create(CommonCode.COMMON_OPTION_ERROR.getCode()).msg("支付调起失败").toJsonView();
    }

    /**
     * @param notify 微信支付异步通知对象
     * @return 接收微信异步通知
     * @throws Exception 可能产生的任何异常
     */
    @RequestMapping(value = "/notify", method = {Type.HttpMethod.POST, Type.HttpMethod.GET})
    @RequestProcessor(WxPayRequestProcessor.class)
    public IView notify(@RequestParam WxPayNotifyResponse notify) throws Exception {
        Logs.get().getLogger().debug("微信支付回调方法");
        IWxMimiProgramPayHandler eventHandler = WxMimiProgram.get().getPayHandler();
        if (eventHandler != null) {
            Logs.get().getLogger().debug("11");
            Map<String, Object> returnValues = new HashMap<String, Object>();
            WxPayAccountMeta meta = WxPay.get().getModuleCfg().getAccountProvider().getAccount(notify.appId());
            if (meta != null) {
                Logs.get().getLogger().debug("22");
                try {
                    if (!WxPay.get().getModuleCfg().isSignCheckDisabled() || notify.checkSignature(meta.getMchKey())) {
                        Logs.get().getLogger().debug("33");
                        if (StringUtils.isNotBlank(notify.productId())) {
                            WxPayUnifiedOrder _request = eventHandler.buildUnifiedOrderRequest(meta, IWxPay.TradeType.NATIVE, notify.productId(), notify.attach());
                            WxPayUnifiedOrder.Response _response = _request.execute();
                            if (_response.checkReturnCode() && _response.checkResultCode()) {
                                if (WxPay.get().getModuleCfg().isSignCheckDisabled() || _response.checkSignature(meta.getMchKey())) {
                                    returnValues.put(IWxPay.Const.RETURN_CODE, IWxPay.ReturnCode.SUCCESS.name());
                                    returnValues.put(IWxPay.Const.RESULT_CODE, IWxPay.ResultCode.SUCCESS.name());
                                    returnValues.put(IWxPay.Const.APP_ID, meta.getAppId());
                                    returnValues.put(IWxPay.Const.MCH_ID, meta.getMchId());
                                    returnValues.put(IWxPay.Const.NONCE_STR, WxPayBaseData.__doCreateNonceStr());
                                    returnValues.put("prepay_id", _response.prepayId());
                                    returnValues.put("trade_type", IWxPay.TradeType.NATIVE.name());
                                    returnValues.put(IWxPay.Const.SIGN, WxPayBaseData.__doCreateSignature(returnValues, meta.getMchKey()));
                                } else {
                                    returnValues.put(IWxPay.Const.RETURN_CODE, IWxPay.ReturnCode.FAIL.name());
                                    returnValues.put(IWxPay.Const.RETURN_MSG, IWxPay.ErrCode.SIGNERROR.desc());
                                }
                            }
                        } else {
                            Logs.get().getLogger().debug("55");
                            eventHandler.onNotifyReceived(notify);
                            //
                            returnValues.put(IWxPay.Const.RETURN_CODE, IWxPay.ReturnCode.SUCCESS.name());
                        }
                    } else {
                        Logs.get().getLogger().debug("44");
                        returnValues.put(IWxPay.Const.RETURN_CODE, IWxPay.ReturnCode.FAIL.name());
                        returnValues.put(IWxPay.Const.RETURN_MSG, IWxPay.ErrCode.SIGNERROR.desc());
                    }
                } catch (Exception e) {
                    returnValues.put(IWxPay.Const.RETURN_CODE, IWxPay.ReturnCode.FAIL.name());
                    if (e instanceof WxPayException) {
                        returnValues.put(IWxPay.Const.RETURN_MSG, ((WxPayException) e).getErrMsg());
                    } else {
                        eventHandler.onExceptionCaught(RuntimeUtils.unwrapThrow(e));
                        returnValues.put(IWxPay.Const.RETURN_MSG, IWxPay.ErrCode.SYSTEMERROR);
                    }
                }
                String _returnStr = WxPayBaseData.__doBuildXML(returnValues);
                Logs.get().getLogger().debug("WxPay Notify Response: [" + _returnStr + "]");
                return new TextView(_returnStr, "text/xml");
            }
        }
        return HttpStatusView.bind(HttpServletResponse.SC_BAD_REQUEST);
    }

    /**
     * @param appId 微信公众号应用ID
     * @param state 商品或订单ID
     * @return 同步回调(订单状态主动检查)
     * @throws Exception 可能产生的任何异常
     */
    @RequestMapping(value = "{app_id}/callback", method = {Type.HttpMethod.GET, Type.HttpMethod.POST})
    public IView callback(@PathVariable("app_id") String appId, @VRequired @RequestParam String state) throws Exception {
        IWxMimiProgramPayHandler eventHandler = WxMimiProgram.get().getPayHandler();
        if (eventHandler != null) {
            if (eventHandler.onReturnCallback(state)) {
                WxPayOrderQuery query = WxPay.get().orderQuery(appId, null, state);
                WxPayOrderQuery.Response response = query.execute();
                eventHandler.onNotifyReceived(response);
                return net.ymate.framework.webmvc.WebResult.SUCCESS().dataAttr("trade_state", response.tradeState()).dataAttr("trade_state_desc", response.tradeStateDesc()).toJSON();
            }
        }
        return HttpStatusView.bind(HttpServletResponse.SC_BAD_REQUEST);
    }
}
