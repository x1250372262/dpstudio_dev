package com.dpstudio.dev.utils;

import java.io.File;

/**
 * @Author: 徐建鹏.
 * @Date: 2018/3/26.
 * @Time: 15:04.
 * @Description:
 */
public class FileUtils {

    /**
     * 修复并创建目标文件目录
     *
     * @param dir 待处理的文件夹路径
     * @return 修改并创建的目标文件夹路径
     */
    public static String fixAndMkDir(String dir) {
        File _file = new File(dir);
        if (!_file.exists()) {
            _file.mkdirs();
        }
        return fixSeparator(_file.getPath());
    }

    /**
     * 修复文件夹路径（必须以'\'结束）
     *
     * @param dir 待修复的文件夹路径
     * @return 修复后的文件夹路径
     */
    public static String fixSeparator(String dir) {
        if (!dir.endsWith(File.separator)) {
            dir += File.separator;
        }
        return dir;
    }
}
