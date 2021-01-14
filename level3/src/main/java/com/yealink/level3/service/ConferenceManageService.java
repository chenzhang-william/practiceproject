package com.yealink.level3.service;

import com.yealink.level3.bean.Conference;
import com.yealink.level3.bean.ConferenceRule;
import com.yealink.level3.bean.result.Schedule;

import java.util.HashMap;
import java.util.List;

/**
 * @author zhangchen
 * @description ConferenceManageService
 * @date 2021/1/6 9:54
 */
public interface ConferenceManageService {


    boolean isConferenceExist(String conferenceNo);

    void addConference(Conference conference, ConferenceRule conferenceRule);

    Conference findConferenceByNo(Conference conference);

    boolean hasPermission(String conferenceNo, String mobile);

    void deleteRule(String ruleId);

    void deleteConference(String conferenceNo);

    void updateConference(Conference conference);

    ConferenceRule findRuleByNo(Conference conference);

    void updateRule(ConferenceRule conferenceRule);

    Conference findConferenceById(String id);

    String findRuleIdByNo(String conferenceNo);

    String findIdByNo(String conferenceNo);

    List<Schedule> findSchedule(List<String> conferenceIdList);

    List<Schedule> getScheduleByCycleRule(HashMap<ConferenceRule, Conference> ruleMap);

    List<Schedule> singleConference(Conference conference, ConferenceRule conferenceRule);

    List<Schedule> cycleByDay(Conference conference, ConferenceRule conferenceRule);

    List<Schedule> cycleByWeek(Conference conference, ConferenceRule conferenceRule);

    List<Schedule> cycleByMonthDay(Conference conference, ConferenceRule conferenceRule);

    List<Schedule> cycleByMonthWeek(Conference conference, ConferenceRule conferenceRule);

    List<Schedule> cycleByYearDay(Conference conference, ConferenceRule conferenceRule);

    List<Schedule> cycleByYearMonth(Conference conference, ConferenceRule conferenceRule);

    List<Schedule> scheduleSort(List<Schedule> scheduleList);

    boolean conferenceRoomDetection(Conference conference, ConferenceRule conferenceRule);
}
