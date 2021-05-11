package com.mx.dev.excel.export.converter;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.mx.dev.excel.export.annotation.DConverter;
import net.ymate.platform.commons.lang.BlurObject;

import java.lang.reflect.Field;

/**
 * @author mengxiang
 * @Date 2020/5/20.
 * @Time: 16:57.
 * @Description: excel转换类
 */
public class DpStudioConverter implements Converter<String> {
    @Override
    public Class supportJavaTypeKey() {
        return String.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    @Override
    public String convertToJavaData(CellData cellData, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        Field field = contentProperty.getField();
        if (field == null) {
            return cellData.getStringValue();
        }
        DConverter dConverter = field.getAnnotation(DConverter.class);
        if (dConverter == null) {
            return cellData.getStringValue();
        }
        String returnValue = null;
        HandlerBean handlerBean = HandlerBean.create(dConverter);
        if (handlerBean.getDataHandle() != null) {
            returnValue = BlurObject.bind(handlerBean.getMethod().invoke(handlerBean.getDataHandle(), cellData.getStringValue())).toStringValue();
        } else {
            returnValue = cellData.getStringValue();
        }
        return returnValue;
    }

    @Override
    public CellData convertToExcelData(String value, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        Field field = contentProperty.getField();
        if (field == null) {
            return new CellData(value);
        }
        DConverter dConverter = field.getAnnotation(DConverter.class);
        if (dConverter == null) {
            return new CellData(value);
        }
        String returnValue = null;
        HandlerBean handlerBean = HandlerBean.create(dConverter);
        if (handlerBean.getDataHandle() != null) {
            returnValue = BlurObject.bind(handlerBean.getMethod().invoke(handlerBean.getDataHandle(), value)).toStringValue();
        } else {
            returnValue = BlurObject.bind(value).toStringValue();
        }
        return new CellData(returnValue);
    }
}
