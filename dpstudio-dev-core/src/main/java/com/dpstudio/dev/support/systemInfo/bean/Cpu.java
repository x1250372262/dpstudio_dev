package com.dpstudio.dev.support.systemInfo.bean;

/**
 * @Author: mengxiang.
 * @Date: 2020/11/21.
 * @Time: 3:31 下午.
 * @Description:
 */
public class Cpu {

    private String name;
    private int packageNum;
    private int coreNum;
    private int logic;
    private double used;
    private double usageRate;
    private double idle;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPackageNum() {
        return packageNum;
    }

    public void setPackageNum(int packageNum) {
        this.packageNum = packageNum;
    }

    public int getCoreNum() {
        return coreNum;
    }

    public void setCoreNum(int coreNum) {
        this.coreNum = coreNum;
    }

    public int getLogic() {
        return logic;
    }

    public void setLogic(int logic) {
        this.logic = logic;
    }

    public double getUsed() {
        return used;
    }

    public void setUsed(double used) {
        this.used = used;
    }

    public double getIdle() {
        return idle;
    }

    public void setIdle(double idle) {
        this.idle = idle;
    }

    public double getUsageRate() {
        return usageRate;
    }

    public void setUsageRate(double usageRate) {
        this.usageRate = usageRate;
    }
}
