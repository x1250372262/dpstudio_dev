package com.dpstudio.dev.generator.generate;

import net.ymate.platform.core.util.RuntimeUtils;
import org.fusesource.jansi.Ansi;

import java.io.File;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.fusesource.jansi.Ansi.ansi;

/**
 * @Author: 徐建鹏.
 * @Date: 2019/11/5.
 * @Time: 12:05 下午.
 * @Description: 通用生成方法
 */
public class BaseGenerate {

    /**
     * 临时文件路径
     */
    public static String tempFilePath = RuntimeUtils.getRootPath() + File.separator + "generate" + File.separator;

    /**
     * 压缩包文件路径
     */
    public static String zipFilePath = RuntimeUtils.getRootPath() + File.separator + "generateZip" + File.separator;

    /**
     * 生成输出信息
     *
     * @param color
     * @param msg
     */
    public static void out(Ansi.Color color, String msg) {
        System.out.println(ansi().eraseScreen().fg(color).a(msg).reset());
    }

    public static URL getProjectPath() {
        return BaseGenerate.class.getProtectionDomain().getCodeSource().getLocation();
    }


    /**
     * 创建ftl参数集合
     *
     * @return
     */
    protected Map<String, Object> buildPropMap() {
        Map<String, Object> propMap = new HashMap<String, Object>();
        propMap.put("lastUpdateTime", new Date());
        return propMap;
    }
}
