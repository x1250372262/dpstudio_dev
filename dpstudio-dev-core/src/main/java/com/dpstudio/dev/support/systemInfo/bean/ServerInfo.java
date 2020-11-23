package com.dpstudio.dev.support.systemInfo.bean;

/**
 * @Author: 徐建鹏.
 * @Date: 2020/11/21.
 * @Time: 3:48 下午.
 * @Description:
 */
public class ServerInfo {

    private Cpu cpu;

    private Info memory;

    private Info swap;

    private Info disk;

    private SysInfo system;

    private String time;


    public Cpu getCpu() {
        return cpu;
    }

    public void setCpu(Cpu cpu) {
        this.cpu = cpu;
    }

    public Info getMemory() {
        return memory;
    }

    public void setMemory(Info memory) {
        this.memory = memory;
    }

    public Info getSwap() {
        return swap;
    }

    public void setSwap(Info swap) {
        this.swap = swap;
    }

    public Info getDisk() {
        return disk;
    }

    public void setDisk(Info disk) {
        this.disk = disk;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public SysInfo getSystem() {
        return system;
    }

    public void setSystem(SysInfo system) {
        this.system = system;
    }
}
