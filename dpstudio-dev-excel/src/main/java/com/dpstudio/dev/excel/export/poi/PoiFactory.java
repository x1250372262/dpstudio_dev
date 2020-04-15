package com.dpstudio.dev.excel.export.poi;


import com.dpstudio.dev.excel.export.poi.impl.BeanExportImpl;
import com.dpstudio.dev.excel.export.poi.impl.MapExportImpl;

/**
 * @Author: 徐建鹏.
 * @Date: 2019/8/31.
 * @Time: 5:19 下午.
 * @Description:
 */
public class PoiFactory {

    /**
     * 导入枚举
     */
    public enum TYPE {
        /**
         * 实体类
         */
        BEAN,
        /**
         * map
         */
        MAP
    }

    /**
     * 获取导入实现类
     *
     * @param type
     * @return
     * @throws Exception
     */
    public static IPoiExport getPoi(TYPE type) throws Exception {
        switch (type) {
            case BEAN:
                return new BeanExportImpl();
            case MAP:
                return new MapExportImpl();
            default:
                throw new Exception("请选择正确的类型");
        }
    }
}
