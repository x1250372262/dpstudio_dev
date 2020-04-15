package com.dpstudio.dev.utils;

import net.ymate.platform.core.util.DateTimeUtils;

import java.util.Calendar;

/**
 * Author: 徐建鹏.
 * User: Administrator.
 * Date: 2016/08/24.
 * Time: 11:05.
 * Reamrk 时间工具类
 */
public class TimeHelper {

    public long DateTimeHelper(long date, int days) {
        Calendar __calendar = Calendar.getInstance();
        if (String.valueOf(date).length() <= 10) {
            date *= 1000;
        }
        __calendar.setTimeInMillis(date);
        __calendar.add(Calendar.DATE, days);
        return __calendar.getTimeInMillis();
    }

    /**
     * 本月第一天
     * @return
     * @throws Exception
     */
    public Long getThisMonthFrist() throws Exception{
        return DateTimeUtils.parseDateTime(DateTimeUtils.formatTime(DateTimeHelper(System.currentTimeMillis(), -1), "yyyy-MM"), "yyyy-MM").getTime();
    }

    /**
     * 下个月第一天
     * @return
     * @throws Exception
     */
    public Long getNextMonthFrist() throws Exception{
        Long beginTime = DateTimeUtils.parseDateTime(DateTimeUtils.formatTime(DateTimeHelper(System.currentTimeMillis(), -1), "yyyy-MM"), "yyyy-MM").getTime();
        Calendar _calendar = Calendar.getInstance();
        _calendar.setTimeInMillis(beginTime);
        _calendar.add(Calendar.MONTH, 1);
       return _calendar.getTimeInMillis();
    }


    /**
     * 本年第一天
     * @return
     * @throws Exception
     */
    public Long getThisYearFrist() throws Exception{
        return DateTimeUtils.parseDateTime(DateTimeUtils.formatTime(DateTimeHelper(System.currentTimeMillis(), -1), "yyyy"), "yyyy").getTime();
    }

    /**
     * 下年月第一天
     * @return
     * @throws Exception
     */
    public Long getNextYearFrist() throws Exception{
        Long beginTime = DateTimeUtils.parseDateTime(DateTimeUtils.formatTime(DateTimeHelper(System.currentTimeMillis(), -1), "yyyy"), "yyyy").getTime();
        Calendar _calendar = Calendar.getInstance();
        _calendar.setTimeInMillis(beginTime);
        _calendar.add(Calendar.YEAR, 1);
        return _calendar.getTimeInMillis();
    }

    /**
     * 得到本周周一零点零分零秒
     *
     * @return yyyy-MM-dd
     */
    public long getMondayOfThisWeek() throws Exception {
        Calendar c = Calendar.getInstance();
        int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
        if (day_of_week == 0){
            day_of_week = 7;
        }
        c.add(Calendar.DATE, -day_of_week + 1);
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
        int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
        if (day_of_week == 0){
            day_of_week = 7;
        }
        c.add(Calendar.DATE, -day_of_week + 7);
        String date = DateTimeUtils.formatTime(c.getTime().getTime(), "yyyy-MM-dd" + " 23:59:59");
        return DateTimeUtils.parseDateTime(date, "yyyy-MM-dd HH:mm:ss").getTime();
    }

    /**
     * 计算当前时间加减N天零点
     *
     * @param day
     * @return
     * @throws Exception
     */
    public long getDay(int day) throws Exception {
        Long beginTime = DateTimeUtils.currentTimeMillis();//开始时间
        String bTime = DateTimeUtils.formatTime(beginTime, "yyyy-MM-dd") + " 00:00:00";//开始时间的零分零秒
        Long endTime = DateTimeUtils.parseDateTime(bTime, "yyyy-MM-dd HH:mm:ss").getTime() + DateTimeUtils.DAY * day;//N天后的零分零秒
        return endTime;
    }

    /**
     * 计算当前时间加减N天23点
     *
     * @param day
     * @return
     * @throws Exception
     */
    public long getDays(int day) throws Exception {
        Long beginTime = DateTimeUtils.currentTimeMillis();//开始时间
        String bTime = DateTimeUtils.formatTime(beginTime, "yyyy-MM-dd") + " 23:59:59";//开始时间的23：23：59
        Long endTime = DateTimeUtils.parseDateTime(bTime, "yyyy-MM-dd HH:mm:ss").getTime() + DateTimeUtils.DAY * day;//N天后的零分零秒
        return endTime;
    }


}
