package com.dpstudio.dev.utils;

import org.apache.commons.lang.NullArgumentException;

import java.io.*;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

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

    public static List<String> getFileList(String strPath) {
        List<String> fileList = new ArrayList<>();
        File fileDir = new File(strPath);
        if (null != fileDir && fileDir.isDirectory()) {
            File[] files = fileDir.listFiles();
            if (null != files) {
                for (int i = 0; i < files.length; i++) {
                    // 如果是文件夹 继续读取
                    if (files[i].isFile() && !files[i].getName().startsWith(".")) {
                        fileList.add(files[i].getName());
                    }
                }

            }
        }
        return fileList;
    }

    public static void copyDir(String oldPath, String newPath) throws IOException {
        File file = new File(oldPath);
        String[] filePath = file.list();
        if(filePath!=null&&filePath.length>0){
            delAllFile(newPath);
            (new File(newPath)).mkdir();

            for (int i = 0; i < filePath.length; i++) {
                if ((new File(oldPath + File.separator + filePath[i])).isDirectory()) {
                    copyDir(oldPath + File.separator + filePath[i], newPath + File.separator + filePath[i]);
                }

                if (new File(oldPath + File.separator + filePath[i]).isFile()) {
                    copyFile(new File(oldPath + File.separator + filePath[i]), new File(newPath + File.separator + filePath[i]));
                }

            }
        }else{
            throw new NullArgumentException("filePath");
        }
    }


    private static void copyFile(File oldPath, File newPath) throws IOException {
        FileChannel inputChannel = null;
        FileChannel outputChannel = null;
        try {
            inputChannel = new FileInputStream(oldPath).getChannel();
            outputChannel = new FileOutputStream(newPath).getChannel();
            outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
        } finally {
            inputChannel.close();
            outputChannel.close();
        }

    }

    /**
     * 删除指定文件夹下所有文件
     *
     * @param path 文件夹完整绝对路径
     * @return
     */
    public static void deleteFile(String path) {
        File file = new File(path);
        if (file.exists()&&file.isDirectory()) {
            String[] tempList = file.list();
            File temp = null;
            for (int i = 0; i < tempList.length; i++) {
                if (path.endsWith(File.separator)) {
                    temp = new File(path + tempList[i]);
                } else {
                    temp = new File(path + File.separator + tempList[i]);
                }
                if (temp.isFile()) {
                    temp.delete();
                }
            }
        }
    }

    /**
     * 删除文件夹
     *
     * @param folderPath 文件夹完整绝对路径
     */
    public static void delFolder(String folderPath) {
        try {
            delAllFile(folderPath); //删除完里面所有内容
            String filePath = folderPath;
            filePath = filePath.toString();
            File myFilePath = new File(filePath);
            myFilePath.delete(); //删除空文件夹
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除指定文件夹下所有文件
     *
     * @param path 文件夹完整绝对路径
     * @return
     */
    public static boolean delAllFile(String path) {
        boolean flag = false;
        File file = new File(path);
        if (!file.exists()) {
            return flag;
        }
        if (!file.isDirectory()) {
            return flag;
        }
        String[] tempList = file.list();
        File temp = null;
        for (int i = 0; i < tempList.length; i++) {
            if (path.endsWith(File.separator)) {
                temp = new File(path + tempList[i]);
            } else {
                temp = new File(path + File.separator + tempList[i]);
            }
            if (temp.isFile()) {
                temp.delete();
            }
            if (temp.isDirectory()) {
                delAllFile(path + "/" + tempList[i]);//先删除文件夹里面的文件
                delFolder(path + "/" + tempList[i]);//再删除空文件夹
                flag = true;
            }
        }
        return flag;
    }

    /**
     * 获取模板文件内容
     *
     * @param file
     * @return
     * @throws Exception
     */
    public static String readTemplateFileContent(File file) throws Exception {
        InputStream inputStream = new FileInputStream(file);
        byte[] buffer = new byte[1024];
        int len;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while ((len = inputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        bos.close();
        return new String(bos.toByteArray(), "utf-8");
    }

    /**
     * 写入模板文件
     *
     * @param file
     * @param content
     * @throws Exception
     */
    public static void writeTemplateFileContent(File file, String content) throws Exception {
        OutputStream outputStream = new FileOutputStream(file);
        OutputStreamWriter os = new OutputStreamWriter(outputStream, "utf-8");
        os.write(content);
        os.flush();
        os.close();
    }
}
