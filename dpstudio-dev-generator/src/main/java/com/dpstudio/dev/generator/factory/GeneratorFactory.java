package com.dpstudio.dev.generator.factory;


import com.dpstudio.dev.generator.bean.ConfigInfo;
import com.dpstudio.dev.generator.generate.ICurdGenerate;
import com.dpstudio.dev.generator.generate.IModelGenerate;
import com.dpstudio.dev.generator.generate.impl.CurdGenerateImpl;
import com.dpstudio.dev.generator.generate.impl.CurdVoGenerateImpl;
import com.dpstudio.dev.generator.generate.impl.ModelGenerateImpl;

/**
 * @Author: 徐建鹏.
 * @Date: 2019/11/5.
 * @Time: 11:13 上午.
 * @Description:
 */
public class GeneratorFactory {

    private final ConfigInfo configInfo;

    private GeneratorFactory(ConfigInfo configInfo) {
        this.configInfo = configInfo;
    }

    public static GeneratorFactory create(ConfigInfo configInfo) {
        return new GeneratorFactory(configInfo);
    }

    public IModelGenerate modelGenerate() throws Exception {
        return new ModelGenerateImpl(configInfo);
    }

    public ICurdGenerate curdGenerate() throws Exception {
        if(configInfo.isCreateVo()){
            return new CurdVoGenerateImpl(configInfo);
        }
        return new CurdGenerateImpl(configInfo);
    }
}
