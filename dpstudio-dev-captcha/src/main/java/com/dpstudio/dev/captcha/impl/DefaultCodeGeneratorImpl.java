package com.dpstudio.dev.captcha.impl;

import com.dpstudio.dev.captcha.ICodeGenerator;
import net.ymate.platform.core.util.UUIDUtils;

/**
 * @Author: 徐建鹏.
 * @Date: 2020/4/14.
 * @Time: 11:27 上午.
 * @Description:
 */
public class DefaultCodeGeneratorImpl implements ICodeGenerator {
    @Override
    public String generate() {
        return UUIDUtils.randomStr(4, true);
    }
}
