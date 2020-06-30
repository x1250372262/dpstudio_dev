package com.dpstudio.dev.support.qrcode.bean;

import java.io.File;

/**
 * @Author: 徐建鹏.
 * @Date: 2020/4/26.
 * @Time: 9:23 上午.
 * @Description:
 */
public class QrCodeResult {

   private File qrCodeFile;

   private String qrCodeUrl;

    public File getQrCodeFile() {
        return qrCodeFile;
    }

    public void setQrCodeFile(File qrCodeFile) {
        this.qrCodeFile = qrCodeFile;
    }

    public String getQrCodeUrl() {
        return qrCodeUrl;
    }

    public void setQrCodeUrl(String qrCodeUrl) {
        this.qrCodeUrl = qrCodeUrl;
    }
}
