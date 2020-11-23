package com.dpstudio.dev.support.systemInfo;

import cn.hutool.core.date.BetweenFormater;
import cn.hutool.core.date.DateUtil;
import com.dpstudio.dev.support.systemInfo.bean.Cpu;
import com.dpstudio.dev.support.systemInfo.bean.Info;
import com.dpstudio.dev.support.systemInfo.bean.ServerInfo;
import com.dpstudio.dev.support.systemInfo.bean.SysInfo;
import com.dpstudio.dev.support.systemInfo.utils.FileUtIls;
import net.ymate.platform.commons.MathCalcHelper;
import net.ymate.platform.commons.util.DateTimeUtils;
import net.ymate.platform.webmvc.context.WebContext;
import net.ymate.platform.webmvc.util.WebUtils;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.hardware.VirtualMemory;
import oshi.software.os.FileSystem;
import oshi.software.os.OSFileStore;
import oshi.software.os.OperatingSystem;
import oshi.util.FormatUtil;
import oshi.util.Util;

import java.lang.management.ManagementFactory;
import java.util.Date;
import java.util.List;

/**
 * @Author: 徐建鹏.
 * @Date: 2020/11/21.
 * @Time: 2:46 下午.
 * @Description:
 */
public class SystemInfoUtils {


    public static ServerInfo serverInfo() {
        ServerInfo serverInfo = new ServerInfo();
        try {
            SystemInfo si = new SystemInfo();
            OperatingSystem os = si.getOperatingSystem();
            HardwareAbstractionLayer hal = si.getHardware();
            // 系统信息
            serverInfo.setSystem(getSystemInfo(os));
            // cpu 信息
            serverInfo.setCpu(getCpuInfo(hal.getProcessor()));
            // 内存信息
            serverInfo.setMemory(getMemoryInfo(hal.getMemory()));
            // 交换区信息
            serverInfo.setSwap(getSwapInfo(hal.getMemory()));
            // 磁盘
            serverInfo.setDisk(getDiskInfo(os));
            serverInfo.setTime(DateTimeUtils.formatTime(DateTimeUtils.currentTimeMillis(), DateTimeUtils.YYYY_MM_DD_HH_MM_SS));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return serverInfo;
    }

    /**
     * 获取磁盘信息
     *
     * @return /
     */
    private static Info getDiskInfo(OperatingSystem os) {
        Info diskInfo = new Info();
        FileSystem fileSystem = os.getFileSystem();
        List<OSFileStore> fsArray = fileSystem.getFileStores();
        for (OSFileStore fs : fsArray) {
            long available = fs.getUsableSpace();
            long total = fs.getTotalSpace();
            long used = total - available;
            diskInfo.setTotal(total > 0 ? FileUtIls.sizeStr(total) : "?");
            diskInfo.setAvailable(FileUtIls.sizeStr(available));
            diskInfo.setUsed(FileUtIls.sizeStr(used));
            diskInfo.setUsageRate(MathCalcHelper.bind(used / (double) fs.getTotalSpace()).multiply(100).scale(2).round().toBlurObject().toDoubleValue());
        }
        return diskInfo;
    }

    /**
     * 获取交换区信息
     *
     * @param memory /
     * @return /
     */
    private static Info getSwapInfo(GlobalMemory memory) {
        Info swapInfo = new Info();
        VirtualMemory virtualMemory = memory.getVirtualMemory();
        long total = virtualMemory.getSwapTotal();
        long used = virtualMemory.getSwapUsed();
        swapInfo.setTotal(FormatUtil.formatBytes(total));
        swapInfo.setUsed(FormatUtil.formatBytes(used));
        swapInfo.setAvailable(FormatUtil.formatBytes(total - used));
        if (total == 0) {
            swapInfo.setUsageRate(0d);
        } else {
            swapInfo.setUsageRate(MathCalcHelper.bind(used / (double) total).multiply(100).scale(2).round().toBlurObject().toDoubleValue());
        }
        return swapInfo;
    }

    /**
     * 获取内存信息
     *
     * @param memory /
     * @return /
     */
    private static Info getMemoryInfo(GlobalMemory memory) {
        Info memoryInfo = new Info();
        memoryInfo.setTotal(FormatUtil.formatBytes(memory.getTotal()));
        memoryInfo.setAvailable(FormatUtil.formatBytes(memory.getAvailable()));
        memoryInfo.setUsed(FormatUtil.formatBytes(memory.getTotal() - memory.getAvailable()));
        memoryInfo.setUsageRate(MathCalcHelper.bind((memory.getTotal() - memory.getAvailable()) / (double) memory.getTotal()).multiply(100).scale(2).round().toBlurObject().toDoubleValue());
        return memoryInfo;
    }

    /**
     * 获取Cpu相关信息
     *
     * @param processor /
     * @return /
     */
    private static Cpu getCpuInfo(CentralProcessor processor) {
        Cpu cpu = new Cpu();
        cpu.setName(processor.getProcessorIdentifier().getName());
        cpu.setPackageNum(processor.getPhysicalPackageCount());
        cpu.setCoreNum(processor.getPhysicalProcessorCount());
        cpu.setLogic(processor.getLogicalProcessorCount());
        // CPU信息
        long[] prevTicks = processor.getSystemCpuLoadTicks();
        // 等待1秒...
        Util.sleep(1000);
        long[] ticks = processor.getSystemCpuLoadTicks();
        long user = ticks[CentralProcessor.TickType.USER.getIndex()] - prevTicks[CentralProcessor.TickType.USER.getIndex()];
        long nice = ticks[CentralProcessor.TickType.NICE.getIndex()] - prevTicks[CentralProcessor.TickType.NICE.getIndex()];
        long sys = ticks[CentralProcessor.TickType.SYSTEM.getIndex()] - prevTicks[CentralProcessor.TickType.SYSTEM.getIndex()];
        long idle = ticks[CentralProcessor.TickType.IDLE.getIndex()] - prevTicks[CentralProcessor.TickType.IDLE.getIndex()];
        long iowait = ticks[CentralProcessor.TickType.IOWAIT.getIndex()] - prevTicks[CentralProcessor.TickType.IOWAIT.getIndex()];
        long irq = ticks[CentralProcessor.TickType.IRQ.getIndex()] - prevTicks[CentralProcessor.TickType.IRQ.getIndex()];
        long softirq = ticks[CentralProcessor.TickType.SOFTIRQ.getIndex()] - prevTicks[CentralProcessor.TickType.SOFTIRQ.getIndex()];
        long steal = ticks[CentralProcessor.TickType.STEAL.getIndex()] - prevTicks[CentralProcessor.TickType.STEAL.getIndex()];
        long totalCpu = user + nice + sys + idle + iowait + irq + softirq + steal;
        if (totalCpu == 0) {
            cpu.setUsed(0d);
            cpu.setIdle(0d);
            cpu.setUsageRate(0d);
        } else {
            cpu.setUsed(MathCalcHelper.bind(100d * user / totalCpu).add(100d * sys / totalCpu).scale(2).round().toBlurObject().toDoubleValue());
            cpu.setIdle(MathCalcHelper.bind(100d * idle).add(totalCpu).scale(2).round().toBlurObject().toDoubleValue());
            cpu.setUsageRate(MathCalcHelper.bind(100d * (user + sys) /  totalCpu).multiply(100).scale(2).round().toBlurObject().toDoubleValue());

        }
        return cpu;
    }

    /**
     * 获取系统相关信息,系统、运行天数、系统IP
     *
     * @param os /
     * @return /
     */
    private static SysInfo getSystemInfo(OperatingSystem os) {
        SysInfo sysInfo = new SysInfo();
        // 运行时间
        long time = ManagementFactory.getRuntimeMXBean().getStartTime();
        Date date = new Date(time);
        // 计算项目运行时间
        String formatBetween = DateUtil.formatBetween(date, new Date(), BetweenFormater.Level.HOUR);
        // 系统信息
        sysInfo.setInfo(os.toString());
        sysInfo.setTime(formatBetween);
        sysInfo.setIp(WebUtils.getRemoteAddress(WebContext.getRequest()));
        return sysInfo;
    }
}
