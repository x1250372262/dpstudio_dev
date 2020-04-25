package com.dpstudio.dev.mimiprogram.impl;

import com.alibaba.fastjson.JSONObject;
import com.dpstudio.dev.core.CommonResult;
import com.dpstudio.dev.core.code.CommonCode;
import com.dpstudio.dev.mimiprogram.IWxMimiProgramHandler;
import com.dpstudio.dev.mimiprogram.WxMimiProgram;
import com.dpstudio.dev.mimiprogram.bean.WxPhoneInfo;
import com.dpstudio.dev.mimiprogram.bean.WxUserInfo;
import com.dpstudio.dev.mimiprogram.model.MimiprogramUser;
import net.ymate.platform.core.util.DateTimeUtils;
import net.ymate.platform.core.util.UUIDUtils;
import net.ymate.platform.log.Logs;
import net.ymate.platform.persistence.Fields;
import org.apache.commons.lang.StringUtils;

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
                        .lastModifyTime(DateTimeUtils.currentTimeMillis())
                        .createTime(DateTimeUtils.currentTimeMillis())
                        .gender(wxUserInfo.getGender())
                        .nickName(wxUserInfo.getNickName())
                        .openId(wxUserInfo.getOpenId())
                        .province(wxUserInfo.getProvince())
                        .unionId(wxUserInfo.getUnionId())
                        .build().save();
            } else {
                mimiprogramUser.setAvatarUrl(wxUserInfo.getAvatarUrl());
                mimiprogramUser.setCity(wxUserInfo.getCity());
                mimiprogramUser.setCountry(wxUserInfo.getCountry());
                mimiprogramUser.setLastModifyTime(DateTimeUtils.currentTimeMillis());
                mimiprogramUser.setGender(wxUserInfo.getGender());
                mimiprogramUser.setNickName(wxUserInfo.getNickName());
                mimiprogramUser.setProvince(wxUserInfo.getProvince());
                mimiprogramUser.setUnionId(wxUserInfo.getUnionId());
                mimiprogramUser.update(Fields.create(MimiprogramUser.FIELDS.AVATAR_URL, MimiprogramUser.FIELDS.CITY, MimiprogramUser.FIELDS.COUNTRY,
                        MimiprogramUser.FIELDS.LAST_MODIFY_TIME, MimiprogramUser.FIELDS.GENDER, MimiprogramUser.FIELDS.NICK_NAME, MimiprogramUser.FIELDS.PROVINCE,
                        MimiprogramUser.FIELDS.UNION_ID));
            }
        } else {
            Logs.get().getLogger().debug("默认数据处理实现输出微信用户信息:" + JSONObject.toJSONString(wxUserInfo));
        }

        return CommonResult.create(CommonCode.COMMON_OPTION_SUCCESS.getCode())
                .attr("token", wxUserInfo.getOpenId());
    }

    @Override
    public CommonResult handlerMobileData(String token, WxPhoneInfo wxPhoneInfo) throws Exception {
        boolean defaultHandlerByDatabase = WxMimiProgram.get().getModuleCfg().defaultHandlerByDatabase();
        if (defaultHandlerByDatabase) {
            if (StringUtils.isNotBlank(token)) {
                MimiprogramUser mimiprogramUser = MimiprogramUser.builder().openId(token).build().findFirst();
                if (mimiprogramUser != null) {
                    mimiprogramUser.setMobile(wxPhoneInfo.getPhoneNumber());
                    mimiprogramUser.update(Fields.create(MimiprogramUser.FIELDS.MOBILE));
                } else {
                    Logs.get().getLogger().debug("用户信息不存在");
                }
            }
        } else {
            Logs.get().getLogger().debug("默认数据处理实现输出微信手机号信息:" + JSONObject.toJSONString(wxPhoneInfo));
        }

        return CommonResult.create(CommonCode.COMMON_OPTION_SUCCESS.getCode());
    }
}
