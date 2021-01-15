package com.yealink.practiceproject.util;

/**
 * @author zhangchen
 * @description DataConversion
 * @date 2021/1/15 17:21
 */
public class DataConversion {

    public static int[] weekTransferToInt(String week){
        String[] weekdays = week.split(",");
        int[] weekRule = new int[weekdays.length];
        for(int i =0;i<weekdays.length;i++){
            weekRule[i] =weekStringToInt(weekdays[i]);
        }
        return weekRule;
    }

    public static int weekStringToInt(String weekday){
        int result =0;
        switch (weekday) {
            case "sunday":
                result = 1;
                break;
            case "monday":
                result = 2;
                break;
            case "tuesday":
                result = 3;
                break;
            case "wednesday":
                result = 4;
                break;
            case "thursday":
                result = 5;
                break;
            case "friday":
                result = 6;
                break;
            case "saturday":
                result = 7;
                break;
        }
        return result;
    }

    public static int genderTransfer(String gender) {
        switch (gender){
            case "男":
                return 1;
            case "女":
                return 2;
            default:
                return 0;
        }
    }



}
