package com.yealink.practiceproject.bean.result;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhangchen
 * @description Schedule
 * @date 2021/1/6 17:45
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Schedule {


    private String title;

    private String conferenceNo;

    private String startTime;

    private String endTime;


    public static int compareByTime(Schedule a, Schedule b) {

        int num = a.getStartTime().compareTo(b.getStartTime());
        num = num == 0 ? a.getEndTime().compareTo(b.getEndTime()) : num;

        return num;
    }
}
