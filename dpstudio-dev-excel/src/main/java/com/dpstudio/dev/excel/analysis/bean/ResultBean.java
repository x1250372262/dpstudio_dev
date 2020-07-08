package com.dpstudio.dev.excel.analysis.bean;

import java.util.List;

/**
 * @Author: mengxiang.
 * @Date: 2019-07-18.
 * @Time: 13:39.
 * @Description: 导入结果
 */
public class ResultBean<T> {

    /**
     * 结果
     */
    private List<T> resultData;

    /**
     * 错误信息
     */
    private List<ErrorInfo> errorInfoList;


    public List<T> getResultData() {
        return resultData;
    }

    public void setResultData(List<T> resultData) {
        this.resultData = resultData;
    }

    public List<ErrorInfo> getErrorInfoList() {
        return errorInfoList;
    }

    public void setErrorInfoList(List<ErrorInfo> errorInfoList) {
        this.errorInfoList = errorInfoList;
    }
}
