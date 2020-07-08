package com.dpstudio.dev.doc.converter.impl;


import com.dpstudio.dev.doc.Constants;
import com.dpstudio.dev.doc.converter.ITagConverter;
import com.dpstudio.dev.doc.tag.DocTag;
import com.dpstudio.dev.doc.tag.ParamTag;

/**
 * @author 徐建鹏
 * @Date 2020.01.02.
 * @Time: 14:30.
 * @Description: @param的转换器
 */
public class ParamTagConverterImpl implements ITagConverter {

    @Override
    public ParamTag converter(String comment) {
        DocTag docTag = new DefaultTagConverterImpl().converter(comment);
        String val = docTag.getValues();
        String[] array = val.split("[ \t]+");
        String paramName = null;
        String paramDesc = "";
        String paramType = "String";
        boolean require = false;
        String demoValue = "";
        //"object :demo 例子|Y"
        //"demo 例子|Y"
        //"demo 例子|String|N"
        if (array.length > 0) {
            //先将第一个认为是参数名称
            paramName = array[0];
            if (array.length > 1) {
                int start = 1;
                if (array[1].startsWith(":") && array[1].length() > 1) {
                    //获取 :demo
                    paramName = array[1].substring(1);
                    start = 2;
                }
                StringBuilder sb = new StringBuilder();
                for (int i = start; i < array.length; i++) {
                    sb.append(array[i]).append(' ');
                }
                paramDesc = sb.toString();
            }
        }

        String[] descs = paramDesc.split("\\|");
        if (descs.length > 0) {
            paramDesc = descs[0];
            paramType = descs[1];
            String requireString = descs[2];
            require = Constants.YES_EN.equals(requireString);
            if (descs.length > 3) {
                demoValue = descs[3];
            }

        }
        return new ParamTag(docTag.getTagName(), paramName, paramDesc, paramType, require, demoValue);
    }
}
