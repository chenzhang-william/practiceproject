package com.yealink.level1.service;

import com.yealink.level1.bean.Conference;
import com.yealink.level1.bean.ConferenceRule;
import com.yealink.level1.bean.result.Schedule;

import java.util.List;

/**
 * @author zhangchen
 * @description ConferenceManageService
 * @date 2021/1/6 9:54
 */
public interface ConferenceManageService {


    boolean isConferenceExist(Conference conference);

    void addConference(Conference conference, ConferenceRule conferenceRule);

    Conference findConferenceByNo(Conference conference);

    boolean hasPermission(String conferenceNo,String mobile);

    void deleteRule(String conferenceNo);

    void deleteConference(String conferenceNo);

    void updateConference(Conference conference);

    ConferenceRule findRuleByNo(Conference conference);

    void updateRule(ConferenceRule conferenceRule);

    Conference findConferenceById(String id);

    String findRuleIdById(String id);

    String findIdByNo(String conferenceNo);

    List<Schedule> findScheduleOfStaff(List<String> conferenceIdList);

    List<Schedule> singleConference(Conference conference,ConferenceRule conferenceRule);

    List<Schedule> cycleByDay(Conference conference,ConferenceRule conferenceRule);

    List<Schedule> cycleByWeek(Conference conference,ConferenceRule conferenceRule);

    List<Schedule> cycleByMonthDay(Conference conference,ConferenceRule conferenceRule);

    List<Schedule> cycleByMonthWeek(Conference conference,ConferenceRule conferenceRule);

    List<Schedule> cycleByYearDay(Conference conference,ConferenceRule conferenceRule);

    List<Schedule> cycleByYearMonth(Conference conference,ConferenceRule conferenceRule);

    List<Schedule> scheduleSort(List<Schedule> scheduleList);
}
