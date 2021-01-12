package com.yealink.level2.service.impl;

import com.yealink.level2.bean.Conference;
import com.yealink.level2.bean.ConferenceParticipant;
import com.yealink.level2.bean.ConferenceRule;
import com.yealink.level2.bean.result.Schedule;
import com.yealink.level2.domain.ConferenceMapper;
import com.yealink.level2.domain.ConferenceRuleMapper;
import com.yealink.level2.service.ConferenceManageService;
import com.yealink.level2.service.ConferenceParticipantService;
import com.yealink.level2.service.StaffService;
import com.yealink.level2.util.ScheduleComparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.*;

import static com.yealink.level2.util.DateUtil.*;

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
    @Autowired
    private StaffService staffService;

    @Override
    public boolean isConferenceExist(String conferenceNo) {
        return conferenceMapper.findIdByNo(conferenceNo)!=null?true:false;
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
        if(!conferenceParticipantService.isParticipantExist(conferenceNo,mobile)) return false;
        ConferenceParticipant conferenceParticipant = new ConferenceParticipant();
        conferenceParticipant.setConferenceId(conferenceMapper.findIdByNo(conferenceNo));
        conferenceParticipant.setParticipantId(staffService.findIdByMobile(mobile));
        conferenceParticipant = conferenceParticipantService.findParticipant(conferenceNo,mobile);
        if(conferenceParticipant.getRole()!=1) return false;
        return true;
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
    public List<Schedule> findScheduleOfStaff(List<String> conferenceIdList) {
        List<Schedule> scheduleList= new ArrayList<>();
        for(String id: conferenceIdList){
            Conference conference = conferenceMapper.findById(id);
            ConferenceRule conferenceRule = conferenceRuleMapper.find(conference.getRuleId());
            switch (conferenceRule.getType()){
                case 0:
                    scheduleList.addAll(singleConference(conference, conferenceRule));
                    break;
                case 1:
                    scheduleList.addAll(cycleByDay(conference,conferenceRule));
                    break;
                case 2:
                    scheduleList.addAll(cycleByWeek(conference,conferenceRule));
                    break;
                case 30:
                    scheduleList.addAll(cycleByMonthDay(conference,conferenceRule));
                    break;
                case 31:
                    scheduleList.addAll(cycleByMonthWeek(conference,conferenceRule));
                    break;
                case 40:
                    scheduleList.addAll(cycleByYearDay(conference,conferenceRule));
                    break;
                case 41:
                    scheduleList.addAll(cycleByYearMonth(conference, conferenceRule));
                    break;

            }
        }
        return scheduleSort(scheduleList);
    }

    @Override
    public List<Schedule> singleConference(Conference conference, ConferenceRule conferenceRule) {

        List<Schedule> scheduleList= new ArrayList<>();
        long scheduleDay = conferenceRule.getStartDay();

        saveSchedule(scheduleList,conference.getStartTime(),conference.getEndTime(),scheduleDay);
        updateSchedule(conference,scheduleList);
        return scheduleList;
    }

    @Override
    public List<Schedule> cycleByDay(Conference conference,ConferenceRule conferenceRule) {
        if(conferenceRule.getGap()<=0) return singleConference(conference,conferenceRule);

        List<Schedule> scheduleList= new ArrayList<>();
        long scheduleDay = conferenceRule.getStartDay();

        while (scheduleDay < conferenceRule.getEndDay()){

            saveSchedule(scheduleList, conference.getStartTime(), conference.getEndTime(), scheduleDay);
            scheduleDay = addDay(scheduleDay,conferenceRule.getGap());
        }

        updateSchedule(conference, scheduleList);
        return scheduleList;
    }

    @Override
    public List<Schedule> cycleByWeek(Conference conference,ConferenceRule conferenceRule) {
        if (!(conferenceRule.getGap()>0&&conferenceRule.getWeek()!=null)) return singleConference(conference,conferenceRule);

        List<Schedule> scheduleList= new ArrayList<>();
        long scheduleDay = conferenceRule.getStartDay();

        int gap = conferenceRule.getGap();
        int[] week = weekTransferToInt(conferenceRule.getWeek());
        Arrays.sort(week);

        //获取第一周的schedule
        if(getDayOfWeek(scheduleDay)<week[week.length-1]){
            for(int i = week.length-1;getDayOfWeek(scheduleDay)<week[i];i--){
                saveSchedule(scheduleList,conference.getStartTime(), conference.getEndTime(),addDay(scheduleDay,week[i]-getDayOfWeek(scheduleDay)));
            }
        }

        scheduleDay = addWeek(scheduleDay,gap);
        long firstDay = addDay(scheduleDay,-getDayOfWeek(scheduleDay)+1);
        for(int i = 0;i<week.length;i++){
            scheduleDay = addDay(firstDay,week[i]-1);
            while(scheduleDay<conferenceRule.getEndDay()){
                saveSchedule(scheduleList, conference.getStartTime(), conference.getEndTime(), scheduleDay);
                scheduleDay = addWeek(scheduleDay,gap);
            }
        }

        updateSchedule(conference, scheduleList);
        return scheduleList;
    }

    @Override
    public List<Schedule> cycleByMonthDay(Conference conference,ConferenceRule conferenceRule) {
        if(!(conferenceRule.getGap()>0&&conferenceRule.getDay()>0)) return singleConference(conference,conferenceRule);

        List<Schedule> scheduleList= new ArrayList<>();
        long scheduleDay = conferenceRule.getStartDay();

        int gap = conferenceRule.getGap();
        int day = conferenceRule.getDay();

        if(getDayOfMonth(scheduleDay)>day) scheduleDay = addMonth(scheduleDay,gap);

        scheduleDay = addDay(scheduleDay,day-getDayOfMonth(scheduleDay));
        while (scheduleDay<conferenceRule.getEndDay()){
            saveSchedule(scheduleList,conference.getStartTime(), conference.getEndTime(), scheduleDay);
            scheduleDay = addMonth(scheduleDay, gap);
        }

        updateSchedule(conference, scheduleList);
        return scheduleList;
    }

    @Override
    public List<Schedule> cycleByMonthWeek(Conference conference,ConferenceRule conferenceRule) {
        if(!(conferenceRule.getGap()>0&&conferenceRule.getOrdinalWeek()>0&&conferenceRule.getWeek()!=null)) return singleConference(conference,conferenceRule);

        List<Schedule> scheduleList= new ArrayList<>();
        long scheduleDay = conferenceRule.getStartDay();

        int gap = conferenceRule.getGap();
        int ordinalWeek = conferenceRule.getOrdinalWeek();
        int[] week = weekTransferToInt(conferenceRule.getWeek());

        if(!(getWeekOfMonth(scheduleDay)<=ordinalWeek && getDayOfWeek(scheduleDay)<=week[0])) scheduleDay = addMonth(scheduleDay,gap);

        scheduleDay = addDay(scheduleDay,-getDayOfMonth(scheduleDay)+1);
        long firstDay = scheduleDay;
        while (scheduleDay<conferenceRule.getEndDay()){
            scheduleDay = firstDay;
            scheduleDay = addWeek(scheduleDay,ordinalWeek-1);
            scheduleDay = addDay(scheduleDay,week[0]-getDayOfWeek(scheduleDay));

            saveSchedule(scheduleList, conference.getStartTime(), conference.getEndTime(), scheduleDay);

            firstDay = addMonth(firstDay,gap);
        }

        updateSchedule(conference, scheduleList);
        return scheduleList;
    }

    @Override
    public List<Schedule> cycleByYearDay(Conference conference,ConferenceRule conferenceRule) {
        if(!(conferenceRule.getGap()>0&&conferenceRule.getOrdinalMonth()>0&&conferenceRule.getDay()>0)) return singleConference(conference,conferenceRule);

        List<Schedule> scheduleList= new ArrayList<>();
        long scheduleDay = conferenceRule.getStartDay();

        int gap = conferenceRule.getGap();
        int ordinalMonth = conferenceRule.getOrdinalMonth();
        int day = conferenceRule.getDay();

        if(!(getMonth(scheduleDay)<=ordinalMonth&&conferenceRule.getDay()<=day)) scheduleDay = addYear(scheduleDay,gap);

        scheduleDay = addMonth(scheduleDay,ordinalMonth-getMonth(scheduleDay));
        scheduleDay = addDay(scheduleDay,day-getDayOfMonth(scheduleDay));
        while (scheduleDay<conferenceRule.getEndDay()){
            saveSchedule(scheduleList, conference.getStartTime(), conference.getEndTime(), scheduleDay);

            scheduleDay = addYear(scheduleDay,gap);
        }

        updateSchedule(conference, scheduleList);
        return scheduleList;
    }

    @Override
    public List<Schedule> cycleByYearMonth(Conference conference, ConferenceRule conferenceRule) {
        if(!(conferenceRule.getGap()>0&&conferenceRule.getOrdinalMonth()>0&&conferenceRule.getOrdinalWeek()>0&&conferenceRule.getWeek()!=null)) return singleConference(conference,conferenceRule);

        List<Schedule> scheduleList= new ArrayList<>();
        long scheduleDay = conferenceRule.getStartDay();

        int gap = conferenceRule.getGap();
        int ordinalMonth = conferenceRule.getOrdinalMonth();
        int ordinalWeek = conferenceRule.getOrdinalWeek();
        int[] week = weekTransferToInt(conferenceRule.getWeek());

        if(!(getMonth(scheduleDay)<=ordinalMonth && getWeekOfMonth(scheduleDay)<=ordinalWeek && getDayOfWeek(scheduleDay)<=week[0])) scheduleDay = addYear(scheduleDay,gap);

        scheduleDay = addMonth(scheduleDay,ordinalMonth-getMonth(scheduleDay));
        long firstDay = scheduleDay = addDay(scheduleDay,-getDayOfMonth(scheduleDay)+1);

        while(scheduleDay<conferenceRule.getEndDay()){
            scheduleDay =firstDay;
            scheduleDay = addWeek(scheduleDay,ordinalWeek-getWeekOfMonth(scheduleDay));
            scheduleDay = addDay(scheduleDay,week[0]-getDayOfWeek(scheduleDay));

            saveSchedule(scheduleList, conference.getStartTime(), conference.getEndTime(), scheduleDay);

            firstDay = addYear(firstDay,gap);
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
        ScheduleComparator scheduleComparator = new ScheduleComparator();
        Collections.sort(scheduleList,scheduleComparator);
        return scheduleList;
    }


}
