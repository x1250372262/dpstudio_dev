package com.dpstudio.dev.mimiprogram.result;

import com.alibaba.fastjson.JSONObject;

import java.nio.Buffer;

public class QRCodeResult {

    private Buffer url;

    private int errCode;

    private String errMsg;

    public QRCodeResult(JSONObject result) {
        this.errCode = result.getIntValue("errcode");
        this.errMsg = result.getString("errmsg");
        if (this.getErrCode() == 0) {
            this.url = (Buffer) result.get("buffer");
        }
    }

    public Buffer getUrl() {
        return url;
    }

    public void setUrl(Buffer url) {
        this.url = url;
    }

    public int getErrCode() {
        return errCode;
    }

    public void setErrCode(int errCode) {
        this.errCode = errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }
}
