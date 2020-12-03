package com.dpstudio.dev.excel.export.converter;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.alibaba.excel.util.IoUtils;

import java.io.InputStream;
import java.net.URL;

/**
 * @author 徐建鹏
 * @Date 2020/5/20.
 * @Time: 16:57.
 * @Description: excel转换类
 */
public class PicConverter implements Converter<String> {
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
        throw new UnsupportedOperationException("Cannot convert images to string");
    }

    @Override
    public CellData convertToExcelData(String value, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        URL url = new URL(value);
        InputStream inputStream = null;
        try {
            inputStream = url.openStream();
            byte[] bytes = IoUtils.toByteArray(inputStream);
            return new CellData(bytes);
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }
}
