//package com.dpstudio.dev.mimiprogram.utils;
//
//import com.dpstudio.dev.mimiprogram.WxMimiProgram;
//import com.dpstudio.dev.mimiprogram.result.QRCodeResult;
//import net.ymate.platform.core.util.UUIDUtils;
//
//import java.io.*;
//import java.net.HttpURLConnection;
//import java.net.MalformedURLException;
//import java.net.URL;
//
///**
// * @Author: 徐建鹏.
// * @Date: 2020/4/29.
// * @Time: 8:37 下午.
// * @Description:
// */
//public class QRCodeHelper {
//
//    private void saveBit(InputStream inputStream,String path) throws Exception {
//        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
//        byte[] buffer = new byte[1024];
//        int len = 0;
//        while ((len = inputStream.read(buffer)) != -1) {
//            outStream.write(buffer, 0, len);
//        }
//        inputStream.close();
//        byte[] data = outStream.toByteArray();
//        File imageFile = new File(path.concat("/").concat(UUIDUtils.UUID()).concat(""));
//        FileOutputStream fileOutStream = new FileOutputStream(imageFile);
//        fileOutStream.write(data);
//    }
//
//    /**
//     * 生成小程序码
//     *
//     * @param params
//     * @return
//     */
//    public static QRCodeResult createACodeLimit(String params) throws Exception {
//        String QR_CODE_URL = "https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token=".concat(WxMimiProgram.get().getAccessToken());
//        URL url = new URL(QR_CODE_URL);
//        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
//        httpURLConnection.setRequestMethod("POST");// 提交模式
//        httpURLConnection.setDoOutput(true);
//        httpURLConnection.setDoInput(true);
//        // 获取URLConnection对象对应的输出流
//        PrintWriter printWriter = new PrintWriter(httpURLConnection.getOutputStream());
//        // 发送请求参数
//        printWriter.write(params);
//        // flush输出流的缓冲
//        printWriter.flush();
//        //获取二维码文件流
//        InputStream inputStream = httpURLConnection.getInputStream();
//        File file = saveBit(inputStream);
//    }
//
//}
