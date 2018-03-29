package cn.bluesking.spider.commons.util;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期时间操作工具类
 * 
 * @author 随心
 *
 */
public final class DateUtil {

    /**
     * 获取累加时间的毫秒
     * 
     * @param start [Date]起始日期
     * @param year  [int]累加年数
     * @param month [int]累加月数
     * @param day   [int]累加天数
     * @return
     */
    public static long getAddDateTime(Date start, int year, int month, int day) {
        Date added = addDate(start, year, month, day);
        return added.getTime() - start.getTime();
    }
    
    /**
     * 获取累加时间的毫秒
     * 
     * @param start       [Timestamp]起始时间戳
     * @param year        [int]累加年数
     * @param month       [int]累加月数
     * @param day         [int]累加天数
     * @param hour        [int]延后小时数
     * @param minute      [int]延后分钟数
     * @param second      [int]延后秒数
     * @return
     */
    public static long getAddDateTime(Timestamp start, int year, int month, 
            int day, int hour, int minute, int second) {
        long startTime = start.getTime();
        long addedTime = addDateTime(start.getTime(), year, month, day, hour, minute, second);
        return addedTime - startTime;
    }
    
    /**
     * 日期时间延后
     * 
     * @param start       [long]起始日期时间毫秒
     * @param year        [int]延后年数
     * @param month       [int]延后月数
     * @param day         [int]延后天数
     * @param hour        [int]延后小时数
     * @param minute      [int]延后分钟数
     * @param second      [int]延后秒数
     * @return
     */
    public static long addDateTime(long start, int year, int month, 
            int day, int hour, int minute, int second) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(start);
        calendar.add(Calendar.SECOND, second);
        calendar.add(Calendar.MINUTE, minute);
        calendar.add(Calendar.HOUR, hour);
        calendar.add(Calendar.DAY_OF_MONTH, day);
        calendar.add(Calendar.MONTH, month);
        calendar.add(Calendar.YEAR, year);
        return calendar.getTimeInMillis();
    }
    
    /**
     * 日期延后
     * 
     * @param start [Date]起始日期
     * @param year  [int]延后年数
     * @param month [int]延后月数
     * @param day   [int]延后天数
     * @return
     */
    public static Date addDate(Date start, int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(start);
        calendar.add(Calendar.DAY_OF_MONTH, day);
        calendar.add(Calendar.MONTH, month);
        calendar.add(Calendar.YEAR, year);
        return calendar.getTime();
    }
    
    /**
     * 日期提前
     * 
     * @param start [Date]起始日期
     * @param year  [int]提前年数
     * @param month [int]提前月数
     * @param day   [int]提前天数
     * @return
     */
    public static Date minusDate(Date start, int year, int month, int day) {
        return addDate(start, -year, -month, -day);
    }
    
}
