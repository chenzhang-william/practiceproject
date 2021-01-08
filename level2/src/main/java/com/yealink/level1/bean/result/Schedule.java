package com.yealink.level1.bean.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Comparator;

import static com.yealink.level1.util.DateUtil.*;

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

    private String startTime;

    private String endTime;


}
