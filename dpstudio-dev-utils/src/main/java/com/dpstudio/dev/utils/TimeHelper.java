package com.dpstudio.dev.utils;



import net.ymate.platform.commons.util.DateTimeUtils;

import java.util.Calendar;

 /**
  * @author mengxiang
  * @Date 2020.07.05
  * @Time:
  * @Description: 时间工具类
  */
public class TimeHelper {

    public static TimeHelper create() {
        return new TimeHelper();
    }

    final int timeUnit = 10;
    private TimeHelper() {

    }

    public long dateTimeHelper(long date, int type) {
        Calendar calendar = Calendar.getInstance();
        if (String.valueOf(date).length() <= timeUnit) {
            date *= 1000;
        }
        calendar.setTimeInMillis(date);

        if (type == 1) {
            calendar.add(Calendar.YEAR, 0);
            calendar.set(Calendar.DAY_OF_YEAR, 1);
        } else if (type == 0) {
            calendar.add(Calendar.MONTH, 0);
            calendar.set(Calendar.DAY_OF_MONTH, 1);
        }

        return calendar.getTimeInMillis();
    }

    /**
     * 本月第一天
     */
    public Long getThisMonthFrist() throws Exception {
        return DateTimeUtils.parseDateTime(DateTimeUtils.formatTime(dateTimeHelper(System.currentTimeMillis(), 0), "yyyy-MM"), "yyyy-MM").getTime();
    }

    /**
     * 下个月第一天
     */
    public Long getNextMonthFrist() throws Exception {
        Long beginTime = DateTimeUtils.parseDateTime(DateTimeUtils.formatTime(dateTimeHelper(System.currentTimeMillis(), 0), "yyyy-MM"), "yyyy-MM").getTime();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(beginTime);
        calendar.add(Calendar.MONTH, 1);
        return calendar.getTimeInMillis();
    }


    /**
     * 本年第一天
     */
    public Long getThisYearFrist() throws Exception {
        return DateTimeUtils.parseDateTime(DateTimeUtils.formatTime(dateTimeHelper(System.currentTimeMillis(), 1), "yyyy"), "yyyy").getTime();
    }

    /**
     * 下年第一天
     */
    public Long getNextYearFrist() throws Exception {
        Long beginTime = DateTimeUtils.parseDateTime(DateTimeUtils.formatTime(dateTimeHelper(System.currentTimeMillis(), 1), "yyyy"), "yyyy").getTime();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(beginTime);
        calendar.add(Calendar.YEAR, 1);
        return calendar.getTimeInMillis();
    }

    /**
     * 得到本周周一零点零分零秒
     *
     * @return yyyy-MM-dd
     */
    public long getMondayOfThisWeek() throws Exception {
        Calendar c = Calendar.getInstance();
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
        if (dayOfWeek == 0) {
            dayOfWeek = 7;
        }
        c.add(Calendar.DATE, -dayOfWeek + 1);
        String date = DateTimeUtils.formatTime(c.getTime().getTime(), "yyyy-MM-dd" + " 00:00:00");
        return DateTimeUtils.parseDateTime(date, "yyyy-MM-dd HH:mm:ss").getTime();
    }


    /**
     * 得到本周周日23:23:59
     *
     * @return yyyy-MM-dd
     */
    public long getSundayOfThisWeek() throws Exception {
        Calendar c = Calendar.getInstance();
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
        if (dayOfWeek == 0) {
            dayOfWeek = 7;
        }
        c.add(Calendar.DATE, -dayOfWeek + 7);
        String date = DateTimeUtils.formatTime(c.getTime().getTime(), "yyyy-MM-dd" + " 23:59:59");
        return DateTimeUtils.parseDateTime(date, "yyyy-MM-dd HH:mm:ss").getTime();
    }

    /**
     * 计算当前时间加减N天零点
     *
     * @param day
     */
    public long getDay(int day) throws Exception {
        long beginTime = DateTimeUtils.currentTimeMillis();
        String bTime = DateTimeUtils.formatTime(beginTime, "yyyy-MM-dd") + " 00:00:00";
        return DateTimeUtils.parseDateTime(bTime, "yyyy-MM-dd HH:mm:ss").getTime() + DateTimeUtils.DAY * day;
    }

    /**
     * 计算当前时间加减N天23点
     *
     * @param day
     */
    public long getDays(int day) throws Exception {
        long beginTime = DateTimeUtils.currentTimeMillis();
        String bTime = DateTimeUtils.formatTime(beginTime, "yyyy-MM-dd") + " 23:59:59";
        return DateTimeUtils.parseDateTime(bTime, "yyyy-MM-dd HH:mm:ss").getTime() + DateTimeUtils.DAY * day;
    }


}
