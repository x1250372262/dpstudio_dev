package com.dpstudio.dev.support.systemInfo.bean;

/**
 * @Author: 徐建鹏.
 * @Date: 2020/11/21.
 * @Time: 3:42 下午.
 * @Description:
 */
public class Info {

    private String total;

    private String available;

    private String used;

    private double usageRate;

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
        this.available = available;
    }

    public String getUsed() {
        return used;
    }

    public void setUsed(String used) {
        this.used = used;
    }

    public double getUsageRate() {
        return usageRate;
    }

    public void setUsageRate(double usageRate) {
        this.usageRate = usageRate;
    }
}
