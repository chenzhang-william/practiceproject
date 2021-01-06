package com.yealink.level1.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhangchen
 * @description Schedule
 * @date 2021/1/6 17:45
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Schedule {


    private String title;

    private String conferenceNo;

    private long startTime;

    private long endTime;

    private long conferenceDay;
}
