package com.yealink.practiceproject.util;

/**
 * @author zhangchen
 * @description Constants
 * @date 2021/1/15 14:38
 */
public interface Constants {
    String ENTERPRISE_CREATOR_ROLE = "创建者";
    String ENTERPRISE_CREATOR_POSITION = "Boss";

    int CYCLE_TYPE_SINGLE = 0;
    int CYCLE_TYPE_BY_DAY = 1;
    int CYCLE_TYPE_BY_WEEK = 2;
    int CYCLE_TYPE_BY_MONTH_DAY = 30;
    int CYCLE_TYPE_BY_MONTH_WEEK = 31;
    int CYCLE_TYPE_BY_YEAR_DAY = 40;
    int CYCLE_TYPE_BY_YEAR_WEEK = 41;

    int CONFERENCE_CREATOR_ROLE = 1;
    int CONFERENCE_ADMIN_ROLE = 2;
    int CONFERENCE_PARTICIPANT_STATUS_OFF = 0;
}
