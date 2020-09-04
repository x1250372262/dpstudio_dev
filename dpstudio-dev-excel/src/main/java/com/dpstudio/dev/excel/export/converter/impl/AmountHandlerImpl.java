package com.dpstudio.dev.excel.export.converter.impl;

import com.dpstudio.dev.excel.export.converter.IDpstudioDataHandler;
import com.dpstudio.dev.utils.MathExtend;
import net.ymate.platform.core.lang.BlurObject;
import org.apache.commons.lang.StringUtils;

/**
 * @Author: 徐建鹏.
 * @Date: 2020/8/28.
 * @Time: 上午10:19.
 * @Description:
 */
public class AmountHandlerImpl implements IDpstudioDataHandler {

    public String amount(String amount) throws Exception {
        if(amount==null){
            return "0.00";
        }
        String moneyStr = BlurObject.bind(amount).toStringValue();
        if(StringUtils.isBlank(moneyStr)){
            return "0.00";
        }
        return MathExtend.divide(moneyStr, "100", 2);
    }
}
