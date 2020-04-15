package com.dpstudio.dev.mimiprogram.impl;

import com.alibaba.fastjson.JSONObject;
import com.dpstudio.dev.mimiprogram.IWxMimiProgramHandler;
import com.dpstudio.dev.mimiprogram.WxMimiProgram;
import com.dpstudio.dev.mimiprogram.bean.WxPhoneInfo;
import com.dpstudio.dev.mimiprogram.bean.WxUserInfo;
import com.dpstudio.dev.core.CommonResult;
import com.dpstudio.dev.core.code.CommonCode;
import com.dpstudio.dev.mimiprogram.model.MimiprogramUser;
import net.ymate.platform.core.util.DateTimeUtils;
import net.ymate.platform.core.util.UUIDUtils;
import net.ymate.platform.log.Logs;

/**
 * @Author: 徐建鹏.
 * @Date: 2020/4/2.
 * @Time: 5:41 下午.
 * @Description: 默认数据处理实现类
 */
public class DefaultWxMimiProgramHandler implements IWxMimiProgramHandler {
    @Override
    public CommonResult handlerUserData(WxUserInfo wxUserInfo) throws Exception {
        boolean defaultHandlerByDatabase = WxMimiProgram.get().getModuleCfg().defaultHandlerByDatabase();
        if (defaultHandlerByDatabase) {
            MimiprogramUser mimiprogramUser = MimiprogramUser.builder().openId(wxUserInfo.getOpenId()).build().findFirst();
            if (mimiprogramUser == null) {
                //保存微信用户信息
                MimiprogramUser.builder().id(UUIDUtils.UUID())
                        .avatarUrl(wxUserInfo.getAvatarUrl())
                        .city(wxUserInfo.getCity())
                        .country(wxUserInfo.getCountry())
                        .createTime(DateTimeUtils.currentTimeMillis())
                        .gender(wxUserInfo.getGender())
                        .nickName(wxUserInfo.getNickName())
                        .openId(wxUserInfo.getOpenId())
                        .province(wxUserInfo.getProvince())
                        .unionId(wxUserInfo.getUnionId())
                        .build().save();
            }
        } else {
            Logs.get().getLogger().debug("默认数据处理实现输出微信用户信息:" + JSONObject.toJSONString(wxUserInfo));
        }

        return CommonResult.create(CommonCode.COMMON_OPTION_SUCCESS.getCode()).attr("login_key", wxUserInfo.getOpenId());
    }

    @Override
    public CommonResult handlerUserData(WxUserInfo wxUserInfo, WxPhoneInfo wxPhoneInfo) throws Exception {
        boolean defaultHandlerByDatabase = WxMimiProgram.get().getModuleCfg().defaultHandlerByDatabase();
        if (defaultHandlerByDatabase) {
            MimiprogramUser mimiprogramUser = MimiprogramUser.builder().openId(wxUserInfo.getOpenId()).build().findFirst();
            if (mimiprogramUser == null) {
                //保存微信用户信息
                MimiprogramUser.builder().id(UUIDUtils.UUID())
                        .avatarUrl(wxUserInfo.getAvatarUrl())
                        .city(wxUserInfo.getCity())
                        .country(wxUserInfo.getCountry())
                        .createTime(DateTimeUtils.currentTimeMillis())
                        .gender(wxUserInfo.getGender())
                        .nickName(wxUserInfo.getNickName())
                        .openId(wxUserInfo.getOpenId())
                        .province(wxUserInfo.getProvince())
                        .unionId(wxUserInfo.getUnionId())
                        .photo(wxPhoneInfo.getPhoneNumber())
                        .build().save();
            }
        } else {
            Logs.get().getLogger().debug("默认数据处理实现输出微信用户信息:" + JSONObject.toJSONString(wxUserInfo));
            Logs.get().getLogger().debug("默认数据处理实现输出微信手机号信息:" + JSONObject.toJSONString(wxPhoneInfo));
        }

        return CommonResult.create(CommonCode.COMMON_OPTION_SUCCESS.getCode()).attr("login_key", wxUserInfo.getOpenId());
    }
}
