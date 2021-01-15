package com.yealink.practiceproject.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author zhangchen
 * @description DateFormatUtil
 * @date 2021/1/7 9:31
 */
public class DateUtil {

    interface DateFormat{
        String YMD_FORMAT = "yyyy-MM-dd";
        String YMDHM_FORMAT = "yyyy-MM-dd HH:mm";
        String SPACE = " ";
    }
    /*
    将”yy-mm-dd“格式的date转化成long型数据
     */
    public static long getYMDTimeStamp(String date){
        return getTimeStamp(date,DateFormat.YMD_FORMAT);
    }

    /*
    合并日期和时间获取long型数据
     */
    public static long mergeYMDHMTimeStamp(String date,String time){
        return getTimeStamp(date+DateFormat.SPACE+time,DateFormat.YMDHM_FORMAT);
    }


    public static long getYMDHMTimeStamp(String date){
        return getTimeStamp(date,DateFormat.YMDHM_FORMAT);
    }



    /*
    将formatStr格式的date转换成long型数据
     */
    public static long getTimeStamp(String date,String formatStr){
        final SimpleDateFormat format = new SimpleDateFormat(formatStr);

        long result;

        try {
            result = format.parse(date).getTime();
        } catch (ParseException e) {
            result = new Date().getTime();
        }
        return result;
    }

    /*
    将date转换成“yy-MM-dd”格式的String数据
     */
    public static String getYMDDate(long timestamp){
        return getDate(timestamp,DateFormat.YMD_FORMAT);
    }

    public static String getDate(long timestamp,String formatStr){
        final SimpleDateFormat format = new SimpleDateFormat(formatStr);

        return format.format(new Date(timestamp));
    }
    /*
    将long类型日期加天数
     */
    public static long addDay(long day,int gap){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(day);
        calendar.add(Calendar.DATE,gap);
        return calendar.getTimeInMillis();
    }
    public static long addWeek(long day,int gap){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(day);
        calendar.add(Calendar.WEEK_OF_MONTH,gap);
        return calendar.getTimeInMillis();

    }

    public static int getDayOfWeek(long date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date);
        return calendar.get(Calendar.DAY_OF_WEEK);
    }


    public static int getDayOfMonth(long date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    public static int getWeekOfMonth(long date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date);
        return calendar.get(Calendar.WEEK_OF_MONTH);
    }

    public static long addMonth(long day, int gap){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(day);
        calendar.add(Calendar.MONTH,gap);
        return calendar.getTimeInMillis();
    }

    public static int getMonth(long date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date);
        return calendar.get(Calendar.MONTH)+1;
    }

    public static long addYear(long day, int gap){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(day);
        calendar.add(Calendar.YEAR,gap);
        return calendar.getTimeInMillis();
    }

}
