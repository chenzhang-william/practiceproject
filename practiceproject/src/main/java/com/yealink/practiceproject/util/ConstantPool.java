package com.yealink.practiceproject.util;

/**
 * @author zhangchen
 * @description ConstantPool
 * @date 2021/1/15 14:38
 */
public interface ConstantPool {


    interface Role {
        String ENTERPRISE_CREATOR_ROLE = "创建者";
        String ENTERPRISE_CREATOR_POSITION = "Boss";

        int CONFERENCE_CREATOR_ROLE = 1;
        int CONFERENCE_ADMIN_ROLE = 2;
        int CONFERENCE_PARTICIPANT_STATUS_OFF = 0;
    }

    interface CycleType {
        int CYCLE_TYPE_SINGLE = 0;
        int CYCLE_TYPE_BY_DAY = 1;
        int CYCLE_TYPE_BY_WEEK = 2;
        int CYCLE_TYPE_BY_MONTH_DAY = 30;
        int CYCLE_TYPE_BY_MONTH_WEEK = 31;
        int CYCLE_TYPE_BY_YEAR_DAY = 40;
        int CYCLE_TYPE_BY_YEAR_WEEK = 41;
    }

    interface DateFormat {
        String YMD_FORMAT = "yyyy-MM-dd";
        String YMDHM_FORMAT = "yyyy-MM-dd HH:mm";
        String SPACE = " ";
    }


}
