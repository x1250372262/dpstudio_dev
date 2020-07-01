package com.dpstudio.dev.doc.core.resolver.javaparser.converter;


import com.dpstudio.dev.doc.core.tag.DocTag;
import com.dpstudio.dev.doc.core.tag.ParamTagImpl;
import com.dpstudio.dev.doc.core.utils.Constant;

/**
 * @author 徐建鹏
 * @Date 2020.01.02.
 * @Time: 14:30.
 * @Description: @param的转换器
 */
public class ParamTagConverter extends DefaultJavaParserTagConverterImpl {

    @Override
    public DocTag converter(String comment) {
        DocTag docTag = super.converter(comment);
        String val = (String) docTag.getValues();
        String[] array = val.split("[ \t]+");
        String paramName = null;
        String paramDesc = "";
        String paramType = "String";
        boolean require = false;
        String demoValue = "";
        //解析 "user :username 用户名|必填" 这种注释内容
        //或者 "username 用户名|必填" 这种注释内容
        //或者 "username 用户名|String|必填" 这种注释内容
        //上面的"必填"两个字也可以换成英文的"Y"

        if (array.length > 0) {
            //先将第一个认为是参数名称
            paramName = array[0];
            if (array.length > 1) {

                int start = 1;
                if (array[1].startsWith(":") && array[1].length() > 1) {
                    //获取 :username这种类型的参数名称
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
            require = Constant.YES_ZH.equals(requireString) || Constant.YES_EN.equalsIgnoreCase(requireString);
            if(descs.length>3){
                demoValue = descs[3];
            }

        }

        return new ParamTagImpl(docTag.getTagName(), paramName, paramDesc, paramType, require,demoValue);
    }
}
