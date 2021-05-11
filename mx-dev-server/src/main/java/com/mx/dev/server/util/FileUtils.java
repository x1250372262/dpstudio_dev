package com.mx.dev.server.util;

import java.io.File;

/**
 * @Author: mengxiang.
 * @Date: 2020/4/26.
 * @Time: 5:08 下午.
 * @Description:
 */
public class FileUtils {


    public static File createTempDir(String folderName) {
        File tmpdir = new File(System.getProperty("java.io.tmpdir"));
        tmpdir = new File(tmpdir, folderName);
        if (!tmpdir.exists()) {
            tmpdir.mkdir();
        }
        return tmpdir;
    }
}
