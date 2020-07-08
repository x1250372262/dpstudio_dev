package com.dpstudio.dev.doc.converter;

import com.dpstudio.dev.doc.Constants;
import com.dpstudio.dev.doc.converter.impl.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: xujianpeng.
 * @Date: 2020/7/8.
 * @Time: 上午8:05.
 * @Description: 标签转换器注册
 */
public class TagConverterRegister {

    private static final TagConverterRegister TAG_REGISTER = new TagConverterRegister();

    public static TagConverterRegister me() {
        return TAG_REGISTER;
    }

    /**
     * 转换器
     */
    private static final Map<String, ITagConverter> REGISTER_MAP = new HashMap<>();

    /**
     * 注册转换器
     */
    public void register() {
        registerConverter(Constants.PARAM, new ParamTagConverterImpl());
        registerConverter(Constants.RESP, new RespTagConverterImpl());
        registerConverter(Constants.PARAM_OBJ, new ParamObjTagConverterImpl());
        registerConverter(Constants.RESP_OBJ, new RespObjTagConverterImpl());
    }

    /**
     * 注册转换器
     *
     * @param tagName      要解析转换的标签
     * @param tagConverter 转换器
     */
    public void registerConverter(String tagName, ITagConverter tagConverter) {
        REGISTER_MAP.put(tagName, tagConverter);
    }

    /**
     * 获取标签转换器,没有匹配的 返回默认的转换器
     *
     * @param tagName 要转换的标签名称
     * @return 标签转换器
     */
    public ITagConverter findConverter(String tagName) {
        if (REGISTER_MAP.containsKey(tagName)) {
            return REGISTER_MAP.get(tagName);
        }
        return new DefaultTagConverterImpl();
    }
}
