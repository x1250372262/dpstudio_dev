package com.dpstudio.dev.excel.analysis;

import com.dpstudio.dev.excel.analysis.bean.ResultBean;
import org.apache.poi.ss.usermodel.Sheet;

/**
 * @Author: 徐建鹏.
 * @Date: 2019-07-10.
 * @Time: 16:32.
 * @Description: excle导入处理接口
 */
public interface ISheetHandler {

    /**
     * @param sheet Sheet页接口对象
     * @return 处理Sheet页并返回数据
     * @throws Exception 可能产生的任何异常
     */
    ResultBean handle(Sheet sheet) throws Exception;


}
