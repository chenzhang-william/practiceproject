package com.yealink.level2.util;

import com.yealink.level2.bean.result.Schedule;

import java.util.Comparator;

/**
 * @author zhangchen
 * @description ScheduleComparator
 * @date 2021/1/8 10:59
 */
public class ScheduleComparator implements Comparator<Schedule> {
    @Override
    public int compare(Schedule o1, Schedule o2) {
        int num = o1.getStartTime().compareTo(o2.getStartTime());
        num = num == 0?o1.getEndTime().compareTo(o2.getEndTime()):num;

        return num;
    }
}
