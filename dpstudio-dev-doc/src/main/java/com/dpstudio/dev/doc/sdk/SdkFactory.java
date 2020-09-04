package com.dpstudio.dev.doc.sdk;

import com.dpstudio.dev.doc.sdk.wxmini.WxMiniSdkImpl;

/**
 * @Author: xujianpeng.
 * @Date: 2020/7/9.
 * @Time: 下午3:42.
 * @Description:
 */
public class SdkFactory {

    /**
     * sdk类型
     */
    public enum SDK_TYPE {

        /**
         * 微信小程序
         */
        WX_MINI("wxmini"),

        /**
         * 所有的sdk
         */
        ALL("all");

        private String value;

        SDK_TYPE(String value) {
            this.value = value;
        }

        public static SDK_TYPE valueTo(String value) {
            switch (value) {
                case "wxmini":
                    return WX_MINI;
                default:
                    return ALL;
            }
        }

        public String value() {
            return this.value;
        }
    }


    public static ISdk get(SDK_TYPE sdkType) {
        switch (sdkType) {
            case WX_MINI:
                return new WxMiniSdkImpl();
            default:
                return new AllSdkImpl();
        }
    }
}
