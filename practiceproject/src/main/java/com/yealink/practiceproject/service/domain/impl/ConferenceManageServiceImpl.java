package com.yealink.practiceproject.service.domain.impl;

import com.yealink.practiceproject.bean.Conference;
import com.yealink.practiceproject.bean.ConferenceParticipant;
import com.yealink.practiceproject.bean.ConferenceRule;
import com.yealink.practiceproject.bean.result.Schedule;
import com.yealink.practiceproject.dao.ConferenceMapper;
import com.yealink.practiceproject.dao.ConferenceRuleMapper;
import com.yealink.practiceproject.service.domain.ConferenceManageService;
import com.yealink.practiceproject.service.domain.ConferenceParticipantService;
import com.yealink.practiceproject.util.DataConversion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.*;

import static com.yealink.practiceproject.util.ConstantPool.CycleType.*;
import static com.yealink.practiceproject.util.ConstantPool.Role.CONFERENCE_ADMIN_ROLE;
import static com.yealink.practiceproject.util.ConstantPool.Role.CONFERENCE_CREATOR_ROLE;
import static com.yealink.practiceproject.util.DateUtil.*;

/**
 * @author zhangchen
 * @description ConferenceManageServiceImpl
 * @date 2021/1/6 11:08
 */
@Service
@Transactional
@Validated
public class ConferenceManageServiceImpl implements ConferenceManageService {

    @Autowired
    private ConferenceMapper conferenceMapper;
    @Autowired
    private ConferenceRuleMapper conferenceRuleMapper;
    @Autowired
    private ConferenceParticipantService conferenceParticipantService;

    @Override
    public boolean isConferenceExist(String conferenceNo) {
        return conferenceMapper.findIdByNo(conferenceNo) != null;
    }

    @Override
    public void addConference(Conference conference, ConferenceRule conferenceRule) {

        conferenceRuleMapper.add(conferenceRule);
        conference.setRuleId(conferenceRule.getId());

        long now = new Date().getTime();
        conference.setCreateTime(now);
        conference.setModifyTime(now);

        conferenceMapper.add(conference);
    }

    @Override
    public Conference findConferenceByNo(Conference conference) {
        return conferenceMapper.findByNo(conference.getConferenceNo());
    }

    @Override
    public boolean hasPermission(String conferenceNo, String mobile) {

        if (!conferenceParticipantService.isParticipantExist(conferenceNo, mobile)) {
            return false;
        }

        ConferenceParticipant conferenceParticipant = conferenceParticipantService.findParticipant(conferenceNo, mobile);

        return conferenceParticipant.getRole() == CONFERENCE_CREATOR_ROLE || conferenceParticipant.getRole() == CONFERENCE_ADMIN_ROLE;
    }

    @Override
    public void deleteRule(String ruleId) {
        conferenceRuleMapper.delete(ruleId);
    }

    @Override
    public void deleteConference(String conferenceNo) {
        conferenceMapper.delete(conferenceMapper.findIdByNo(conferenceNo));

    }

    @Override
    public void updateConference(Conference conference) {
        conference.setModifyTime(new Date().getTime());
        conferenceMapper.update(conference);
    }

    @Override
    public ConferenceRule findRuleByNo(Conference conference) {
        return conferenceRuleMapper.find(conferenceMapper.findByNo(conference.getConferenceNo()).getRuleId());
    }

    @Override
    public void updateRule(ConferenceRule conferenceRule) {
        conferenceRule.setModifyTime(new Date().getTime());
        conferenceRuleMapper.update(conferenceRule);
    }

    @Override
    public Conference findConferenceById(String id) {
        return conferenceMapper.findById(id);
    }

    @Override
    public String findRuleIdByNo(String conferenceNo) {
        return conferenceMapper.findRuleIdByNo(conferenceNo);
    }

    @Override
    public String findIdByNo(String conferenceNo) {
        return conferenceMapper.findIdByNo(conferenceNo);
    }

    @Override
    public List<Schedule> findSchedule(List<String> conferenceIdList) {

        HashMap<ConferenceRule, Conference> ruleMap = getConferenceRuleConferenceHashMap(conferenceIdList);

        return scheduleSort(getScheduleByCycleRule(ruleMap));
    }

    public List<Schedule> getScheduleByCycleRule(Map<ConferenceRule, Conference> ruleMap) {

        List<Schedule> scheduleList = new ArrayList<>();

        for (Map.Entry<ConferenceRule, Conference> entry : ruleMap.entrySet()) {
            switch (entry.getKey().getType()) {
                case CYCLE_TYPE_SINGLE:
                    scheduleList.addAll(singleConference(entry.getValue(), entry.getKey()));
                    break;
                case CYCLE_TYPE_BY_DAY:
                    scheduleList.addAll(cycleByDay(entry.getValue(), entry.getKey()));
                    break;
                case CYCLE_TYPE_BY_WEEK:
                    scheduleList.addAll(cycleByWeek(entry.getValue(), entry.getKey()));
                    break;
                case CYCLE_TYPE_BY_MONTH_DAY:
                    scheduleList.addAll(cycleByMonthDay(entry.getValue(), entry.getKey()));
                    break;
                case CYCLE_TYPE_BY_MONTH_WEEK:
                    scheduleList.addAll(cycleByMonthWeek(entry.getValue(), entry.getKey()));
                    break;
                case CYCLE_TYPE_BY_YEAR_DAY:
                    scheduleList.addAll(cycleByYearDay(entry.getValue(), entry.getKey()));
                    break;
                case CYCLE_TYPE_BY_YEAR_WEEK:
                    scheduleList.addAll(cycleByYearMonth(entry.getValue(), entry.getKey()));
                    break;
                default:
                    break;
            }
        }

        return scheduleList;
    }

    private HashMap<ConferenceRule, Conference> getConferenceRuleConferenceHashMap(List<String> conferenceIdList) {

        List<String> ruleIdList = new ArrayList<>();
        HashMap<ConferenceRule, Conference> ruleMap = new HashMap<>();

        List<Conference> conferenceList = conferenceMapper.findByIdList(conferenceIdList);


        for (Conference s : conferenceList) {
            ruleIdList.add(s.getRuleId());
        }

        List<ConferenceRule> ruleList = conferenceRuleMapper.findByIdList(ruleIdList);

        for (ConferenceRule rule : ruleList) {
            ruleMap.put(rule, findConferenceByRuleId(conferenceList, rule.getId()));
        }

        return ruleMap;
    }

    private Conference findConferenceByRuleId(List<Conference> conferenceList, String id) {

        for (Conference c : conferenceList) {
            if (c.getRuleId().equals(id)) {
                return c;
            }
        }

        return null;
    }

    @Override
    public List<Schedule> singleConference(Conference conference, ConferenceRule conferenceRule) {

        List<Schedule> scheduleList = new ArrayList<>();

        long scheduleDay = conferenceRule.getStartDay();

        saveSchedule(scheduleList, conference.getStartTime(), conference.getEndTime(), scheduleDay);

        updateSchedule(conference, scheduleList);

        return scheduleList;
    }

    @Override
    public List<Schedule> cycleByDay(Conference conference, ConferenceRule conferenceRule) {

        if (conferenceRule.getGap() <= 0) return singleConference(conference, conferenceRule);

        List<Schedule> scheduleList = new ArrayList<>();

        long scheduleDay = conferenceRule.getStartDay();

        while (scheduleDay < conferenceRule.getEndDay()) {

            saveSchedule(scheduleList, conference.getStartTime(), conference.getEndTime(), scheduleDay);
            scheduleDay = addDay(scheduleDay, conferenceRule.getGap());
        }

        updateSchedule(conference, scheduleList);

        return scheduleList;
    }

    @Override
    public List<Schedule> cycleByWeek(Conference conference, ConferenceRule conferenceRule) {

        if (!(conferenceRule.getGap() > 0 && conferenceRule.getWeek() != null)) {
            return singleConference(conference, conferenceRule);
        }

        List<Schedule> scheduleList = new ArrayList<>();

        long scheduleDay = conferenceRule.getStartDay();

        int gap = conferenceRule.getGap();

        int[] week = DataConversion.weekTransferToInt(conferenceRule.getWeek());
        Arrays.sort(week);

        //获取第一周的schedule
        if (getDayOfWeek(scheduleDay) < week[week.length - 1]) {
            for (int i = week.length - 1; getDayOfWeek(scheduleDay) < week[i]; i--) {
                saveSchedule(scheduleList, conference.getStartTime(), conference.getEndTime(), addDay(scheduleDay, week[i] - getDayOfWeek(scheduleDay)));
            }
        }

        scheduleDay = addWeek(scheduleDay, gap);
        long firstDay = addDay(scheduleDay, -getDayOfWeek(scheduleDay) + 1);

        for (int value : week) {

            scheduleDay = addDay(firstDay, value - 1);

            while (scheduleDay < conferenceRule.getEndDay()) {

                saveSchedule(scheduleList, conference.getStartTime(), conference.getEndTime(), scheduleDay);

                scheduleDay = addWeek(scheduleDay, gap);
            }
        }

        updateSchedule(conference, scheduleList);

        return scheduleList;
    }

    @Override
    public List<Schedule> cycleByMonthDay(Conference conference, ConferenceRule conferenceRule) {

        if (!(conferenceRule.getGap() > 0 && conferenceRule.getDay() > 0)) {
            return singleConference(conference, conferenceRule);
        }

        List<Schedule> scheduleList = new ArrayList<>();

        long scheduleDay = conferenceRule.getStartDay();

        int gap = conferenceRule.getGap();

        int day = conferenceRule.getDay();

        if (getDayOfMonth(scheduleDay) > day) {
            scheduleDay = addMonth(scheduleDay, gap);
        }

        scheduleDay = addDay(scheduleDay, day - getDayOfMonth(scheduleDay));

        while (scheduleDay < conferenceRule.getEndDay()) {

            saveSchedule(scheduleList, conference.getStartTime(), conference.getEndTime(), scheduleDay);

            scheduleDay = addMonth(scheduleDay, gap);
        }

        updateSchedule(conference, scheduleList);

        return scheduleList;
    }

    @Override
    public List<Schedule> cycleByMonthWeek(Conference conference, ConferenceRule conferenceRule) {

        if (!(conferenceRule.getGap() > 0 && conferenceRule.getOrdinalWeek() > 0 && conferenceRule.getWeek() != null)) {
            return singleConference(conference, conferenceRule);
        }

        List<Schedule> scheduleList = new ArrayList<>();

        long scheduleDay = conferenceRule.getStartDay();

        int gap = conferenceRule.getGap();

        int ordinalWeek = conferenceRule.getOrdinalWeek();

        int[] week = DataConversion.weekTransferToInt(conferenceRule.getWeek());

        if (!(getWeekOfMonth(scheduleDay) <= ordinalWeek && getDayOfWeek(scheduleDay) <= week[0])) {
            scheduleDay = addMonth(scheduleDay, gap);
        }

        scheduleDay = addDay(scheduleDay, -getDayOfMonth(scheduleDay) + 1);
        long firstDay = scheduleDay;

        while (scheduleDay < conferenceRule.getEndDay()) {

            scheduleDay = firstDay;
            scheduleDay = addWeek(scheduleDay, ordinalWeek - 1);
            scheduleDay = addDay(scheduleDay, week[0] - getDayOfWeek(scheduleDay));

            saveSchedule(scheduleList, conference.getStartTime(), conference.getEndTime(), scheduleDay);

            firstDay = addMonth(firstDay, gap);
        }

        updateSchedule(conference, scheduleList);

        return scheduleList;
    }

    @Override
    public List<Schedule> cycleByYearDay(Conference conference, ConferenceRule conferenceRule) {

        if (!(conferenceRule.getGap() > 0 && conferenceRule.getOrdinalMonth() > 0 && conferenceRule.getDay() > 0)) {
            return singleConference(conference, conferenceRule);
        }

        List<Schedule> scheduleList = new ArrayList<>();

        long scheduleDay = conferenceRule.getStartDay();

        int gap = conferenceRule.getGap();

        int ordinalMonth = conferenceRule.getOrdinalMonth();

        int day = conferenceRule.getDay();

        if (!(getMonth(scheduleDay) <= ordinalMonth && conferenceRule.getDay() <= day)) {
            scheduleDay = addYear(scheduleDay, gap);
        }

        scheduleDay = addMonth(scheduleDay, ordinalMonth - getMonth(scheduleDay));
        scheduleDay = addDay(scheduleDay, day - getDayOfMonth(scheduleDay));

        while (scheduleDay < conferenceRule.getEndDay()) {

            saveSchedule(scheduleList, conference.getStartTime(), conference.getEndTime(), scheduleDay);

            scheduleDay = addYear(scheduleDay, gap);
        }

        updateSchedule(conference, scheduleList);

        return scheduleList;
    }

    @Override
    public List<Schedule> cycleByYearMonth(Conference conference, ConferenceRule conferenceRule) {

        if (!(conferenceRule.getGap() > 0 && conferenceRule.getOrdinalMonth() > 0 && conferenceRule.getOrdinalWeek() > 0 && conferenceRule.getWeek() != null)) {
            return singleConference(conference, conferenceRule);
        }

        List<Schedule> scheduleList = new ArrayList<>();

        long scheduleDay = conferenceRule.getStartDay();

        int gap = conferenceRule.getGap();

        int ordinalMonth = conferenceRule.getOrdinalMonth();

        int ordinalWeek = conferenceRule.getOrdinalWeek();

        int[] week = DataConversion.weekTransferToInt(conferenceRule.getWeek());

        if (!(getMonth(scheduleDay) <= ordinalMonth && getWeekOfMonth(scheduleDay) <= ordinalWeek && getDayOfWeek(scheduleDay) <= week[0])) {
            scheduleDay = addYear(scheduleDay, gap);
        }

        scheduleDay = addMonth(scheduleDay, ordinalMonth - getMonth(scheduleDay));
        long firstDay = scheduleDay = addDay(scheduleDay, -getDayOfMonth(scheduleDay) + 1);

        while (scheduleDay < conferenceRule.getEndDay()) {
            scheduleDay = firstDay;
            scheduleDay = addWeek(scheduleDay, ordinalWeek - getWeekOfMonth(scheduleDay));
            scheduleDay = addDay(scheduleDay, week[0] - getDayOfWeek(scheduleDay));

            saveSchedule(scheduleList, conference.getStartTime(), conference.getEndTime(), scheduleDay);

            firstDay = addYear(firstDay, gap);
        }

        updateSchedule(conference, scheduleList);

        return scheduleList;

    }

    private void saveSchedule(List<Schedule> scheduleList, String startTime, String endTime, long scheduleDay) {

        Schedule schedule = new Schedule();

        schedule.setStartTime(getYMDDate(scheduleDay) + " " + startTime);
        schedule.setEndTime(getYMDDate(scheduleDay) + " " + endTime);

        scheduleList.add(schedule);
    }

    private void updateSchedule(Conference conference, List<Schedule> scheduleList) {
        for (Schedule s : scheduleList) {
            s.setTitle(conference.getTitle());
            s.setConferenceNo(conference.getConferenceNo());
        }
    }

    @Override
    public List<Schedule> scheduleSort(List<Schedule> scheduleList) {

        scheduleList.sort(Schedule::compareByTime);

        return scheduleList;
    }

    @Override
    public boolean conferenceRoomDetection(Conference conference, ConferenceRule conferenceRule) {

        List<Schedule> scheduleListExist = checkOccupancyOfConferenceRoom(conference.getConferenceRoom());

        Map<ConferenceRule, Conference> map = new HashMap<>();
        map.put(conferenceRule, conference);

        List<Schedule> scheduleListUnderDetection = scheduleSort(getScheduleByCycleRule(map));

        return conflictDetection(createVirtualInterspace(scheduleListExist), scheduleListUnderDetection);

    }

    @Override
    public List<Schedule> checkOccupancyOfConferenceRoom(String conferenceRoom) {
        return findSchedule(conferenceMapper.findIdByConferenceRoom(conferenceRoom));
    }

    private boolean conflictDetection(List<Schedule> scheduleListExist, List<Schedule> scheduleListUnderDetection) {

        int existIndex = 0;
        int detectionIndex = 0;

        while (detectionIndex <= scheduleListUnderDetection.size() - 1 && existIndex <= scheduleListExist.size() - 2) {

            if (getYMDHMTimeStamp(scheduleListUnderDetection.get(detectionIndex).getStartTime()) < getYMDHMTimeStamp(scheduleListExist.get(existIndex).getEndTime())) {
                return false;
            }

            if (getYMDHMTimeStamp(scheduleListUnderDetection.get(detectionIndex).getStartTime()) > getYMDHMTimeStamp(scheduleListExist.get(existIndex + 1).getStartTime())) {
                existIndex += 1;
                continue;
            }

            if (getYMDHMTimeStamp(scheduleListUnderDetection.get(detectionIndex).getEndTime()) > getYMDHMTimeStamp(scheduleListExist.get(existIndex + 1).getStartTime())) {
                return false;
            }

            detectionIndex += 1;
        }

        return true;
    }

    private List<Schedule> createVirtualInterspace(List<Schedule> scheduleListExist) {

        scheduleListExist.add(0, Schedule.builder().endTime("2000-01-01 00:00").build());
        scheduleListExist.add(Schedule.builder().startTime("2030-01-01 00:00").build());

        return scheduleListExist;
    }


}
