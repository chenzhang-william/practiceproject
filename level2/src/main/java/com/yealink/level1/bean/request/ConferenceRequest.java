package com.yealink.level1.bean.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * @author zhangchen
 * @description ConferenceRequest
 * @date 2021/1/5 17:37
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConferenceRequest {
    private String staffId;

    private String title;

    private String conferenceNo;

    private long startTime;

    private long endTime;

    private byte type;

    private byte gap;

    private byte day;

    private byte week;

    private byte ordinalWeek;

    private byte ordinalMonth;

    private long startDay;

    private long endDay;
}
