package com.mx.dev.server.tomcat;

import cn.hutool.core.util.ClassUtil;
import net.ymate.platform.commons.util.ClassUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @Author: mengxiang.
 * @create: 2021-04-10 20:52
 * @Description:
 */
public class TomcatClassLoader extends ClassLoader{

    private static final String CLASS_FILE_SUFFIX = ".class";

    //AppClassLoader的父类加载器
    private ClassLoader extClassLoader;

    public TomcatClassLoader(){
        ClassLoader j = String.class.getClassLoader();
        if (j == null) {
            j = getSystemClassLoader();
            while (j.getParent() != null) {
                j = j.getParent();
            }
        }
        this.extClassLoader = j ;
    }

    @Override
    protected Class<?> loadClass(String name, boolean resolve){

        Class cls;
        cls = findLoadedClass(name);
        if (cls != null){
            return cls;
        }
        //获取ExtClassLoader
        ClassLoader extClassLoader = getExtClassLoader() ;
        //确保自定义的类不会覆盖Java的核心类
        cls = ClassUtil.loadClass(name);
        if (cls != null){
            return cls;
        }
        cls = findClass(name);
        return cls;
    }

    @Override
    public Class<?> findClass(String name) {
        byte[] bt = loadClassData(name);
        if(bt == null){
            return null;
        }
        return defineClass(name, bt, 0, bt.length);
    }

    private byte[] loadClassData(String className) {
        // 读取Class文件呢
        ByteArrayOutputStream byteSt = new ByteArrayOutputStream();
        InputStream is = getClass().getClassLoader().getResourceAsStream(className.replace(".", "/"));
        if(is==null){
            return null;
        }
        // 写入byteStream
        int len =0;
        try {
            while((len=is.read())!=-1){
                byteSt.write(len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 转换为数组
        return byteSt.toByteArray();
    }

    public ClassLoader getExtClassLoader(){
        return extClassLoader;
    }
}
