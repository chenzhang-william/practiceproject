package com.yealink.level1.service.impl;

import com.yealink.level1.bean.Conference;
import com.yealink.level1.bean.ConferenceParticipant;
import com.yealink.level1.bean.ConferenceRule;
import com.yealink.level1.bean.result.Schedule;
import com.yealink.level1.domain.ConferenceMapper;
import com.yealink.level1.domain.ConferenceRuleMapper;
import com.yealink.level1.service.ConferenceManageService;
import com.yealink.level1.service.ConferenceParticipantService;
import com.yealink.level1.service.StaffService;
import com.yealink.level1.util.ScheduleComparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.lang.reflect.Array;
import java.util.*;

import static com.yealink.level1.util.DateUtil.*;

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
    public boolean isConferenceExist(Conference conference) {
        if(conferenceMapper.findIdByNo(conference.getConferenceNo())!=null){
            return true;
        }else return false;
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
        if(conferenceParticipantService.isParticipantExist(conferenceNo,mobile)){
            ConferenceParticipant conferenceParticipant = new ConferenceParticipant();
            conferenceParticipant.setConferenceId(conferenceMapper.findIdByNo(conferenceNo));
            conferenceParticipant.setParticipantId(staffService.findIdByMobile(mobile));
            conferenceParticipant = conferenceParticipantService.findParticipant(conferenceNo,mobile);
            if(conferenceParticipant.getRole()==1) return true;
            else return false;
        }else return false;
    }

    @Override
    public void deleteRule(String conferenceNo) {
        conferenceRuleMapper.delete(conferenceMapper.findByNo(conferenceNo).getRuleId());
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
    public String findRuleIdById(String id) {
        return findConferenceById(id).getRuleId();
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
        Schedule schedule = new Schedule();
        List<Schedule> scheduleList= new ArrayList<>();
        schedule.setTitle(conference.getTitle());
        schedule.setConferenceNo(conference.getConferenceNo());
        long startDay = conferenceRule.getStartDay();
        String startTime = conference.getStartTime();
        String endTime = conference.getEndTime();
        startTime = getYMDDate(startDay)+" "+startTime;
        endTime = getYMDDate(startDay)+" "+endTime;
        schedule.setStartTime(startTime);
        schedule.setEndTime(endTime);
        scheduleList.add(schedule);
        return scheduleList;
    }

    @Override
    public List<Schedule> cycleByDay(Conference conference,ConferenceRule conferenceRule) {
        if(conferenceRule.getGap()>0){
            List<Schedule> scheduleList= new ArrayList<>();


            long startDay = conferenceRule.getStartDay();
            long endDay = conferenceRule.getEndDay();
            String startTime = conference.getStartTime();
            String endTime = conference.getEndTime();
            int gap = conferenceRule.getGap();
            long scheduleDay = startDay;

                while (scheduleDay <= endDay){

                    Schedule schedule = new Schedule();
                    schedule.setStartTime(getYMDDate(scheduleDay)+" "+startTime);
                    schedule.setEndTime(getYMDDate(scheduleDay)+" "+endTime);
                    scheduleList.add(schedule);
                    scheduleDay = addDay(scheduleDay,gap);
                }

            for(Schedule schedule:scheduleList){
                schedule.setTitle(conference.getTitle());
                schedule.setConferenceNo(conference.getConferenceNo());
            }
                return scheduleList;
        }else {
            return singleConference(conference,conferenceRule);
        }

    }

    @Override
    public List<Schedule> cycleByWeek(Conference conference,ConferenceRule conferenceRule) {
        if (conferenceRule.getGap()>0&&conferenceRule.getWeek()!=null){
            List<Schedule> scheduleList= new ArrayList<>();

            long startDay = conferenceRule.getStartDay();
            long endDay = conferenceRule.getEndDay();
            String startTime = conference.getStartTime();
            String endTime = conference.getEndTime();
            long scheduleDay = startDay;
            int gap = conferenceRule.getGap();
            int[] week = weekTransferToInt(conferenceRule.getWeek());

            Arrays.sort(week);
            if(getDayOfWeek(scheduleDay)<week[0]){

            }else {
                for(int i = 0;i<week.length;i++){
                    if(getDayOfWeek(scheduleDay)<week[i]){
                        for(int j=i;j<week.length;j++){
                            scheduleDay = addDay(scheduleDay,week[i]-getDayOfWeek(scheduleDay));
                            Schedule schedule = new Schedule();
                            schedule.setStartTime(getYMDDate(scheduleDay)+" "+startTime);
                            schedule.setEndTime(getYMDDate(scheduleDay)+" "+endTime);
                            scheduleList.add(schedule);
                        }
                        break;
                    }
                }
                scheduleDay = addWeek(scheduleDay,gap);
            }
            scheduleDay = addDay(scheduleDay,-getDayOfWeek(scheduleDay)+1);
            long firstDay = scheduleDay;


            for(int i = 0;i<week.length;i++){
                scheduleDay = addDay(firstDay,week[i]-1);
                while(scheduleDay<endDay){
                    Schedule schedule = new Schedule();
                    schedule.setStartTime(getYMDDate(scheduleDay)+" "+startTime);
                    schedule.setEndTime(getYMDDate(scheduleDay)+" "+endTime);
                    scheduleList.add(schedule);

                    scheduleDay = addWeek(scheduleDay,gap);
                }
            }


            for(Schedule schedule:scheduleList){
                schedule.setTitle(conference.getTitle());
                schedule.setConferenceNo(conference.getConferenceNo());
            }
            return scheduleList;
        }else {
            return singleConference(conference,conferenceRule);
        }

    }

    @Override
    public List<Schedule> cycleByMonthDay(Conference conference,ConferenceRule conferenceRule) {
        if(conferenceRule.getGap()>0&&conferenceRule.getDay()>0){
            List<Schedule> scheduleList= new ArrayList<>();


            long startDay = conferenceRule.getStartDay();
            long endDay = conferenceRule.getEndDay();
            String startTime = conference.getStartTime();
            String endTime = conference.getEndTime();
            long scheduleDay = startDay;
            int gap = conferenceRule.getGap();
            int day = conferenceRule.getDay();

            if(getDayOfMonth(scheduleDay)<=day){

            }else {
                scheduleDay = addMonth(scheduleDay,gap);
            }
            scheduleDay = addDay(scheduleDay,day-getDayOfMonth(scheduleDay));

            while (scheduleDay<endDay){
                Schedule schedule = new Schedule();
                schedule.setStartTime(getYMDDate(scheduleDay) + " " + startTime);
                schedule.setEndTime(getYMDDate(scheduleDay) + " " + endTime);

                scheduleList.add(schedule);
                scheduleDay = addMonth(scheduleDay, gap);
            }


            for(Schedule schedule:scheduleList){
                schedule.setTitle(conference.getTitle());
                schedule.setConferenceNo(conference.getConferenceNo());
            }
            return scheduleList;
        }else {
            return singleConference(conference,conferenceRule);
        }
    }

    @Override
    public List<Schedule> cycleByMonthWeek(Conference conference,ConferenceRule conferenceRule) {
        if(conferenceRule.getGap()>0&&conferenceRule.getOrdinalWeek()>0&&conferenceRule.getWeek()!=null){
            List<Schedule> scheduleList= new ArrayList<>();

            long startDay = conferenceRule.getStartDay();
            long endDay = conferenceRule.getEndDay();
            String startTime = conference.getStartTime();
            String endTime = conference.getEndTime();
            long scheduleDay = startDay;
            int gap = conferenceRule.getGap();
            int ordinalWeek = conferenceRule.getOrdinalWeek();
            int[] week = weekTransferToInt(conferenceRule.getWeek());

            if(getWeekOfMonth(scheduleDay)<=ordinalWeek && getDayOfWeek(scheduleDay)<=week[0]){

            }else {
                scheduleDay = addMonth(scheduleDay,gap);
            }

            scheduleDay = addDay(scheduleDay,-getDayOfMonth(scheduleDay)+1);
            long firstDay = scheduleDay;

            while (scheduleDay<endDay){
                scheduleDay = firstDay;
                scheduleDay = addWeek(scheduleDay,ordinalWeek-1);
                scheduleDay = addDay(scheduleDay,week[0]-getDayOfWeek(scheduleDay));

                Schedule schedule = new Schedule();
                schedule.setStartTime(getYMDDate(scheduleDay) + " " + startTime);
                schedule.setEndTime(getYMDDate(scheduleDay) + " " + endTime);
                scheduleList.add(schedule);

                firstDay = addMonth(firstDay,gap);


            }

            for(Schedule schedule:scheduleList){
                schedule.setTitle(conference.getTitle());
                schedule.setConferenceNo(conference.getConferenceNo());
            }
            return scheduleList;
        }else {
            return singleConference(conference,conferenceRule);
        }
    }

    @Override
    public List<Schedule> cycleByYearDay(Conference conference,ConferenceRule conferenceRule) {
        if(conferenceRule.getGap()>0&&conferenceRule.getOrdinalMonth()>0&&conferenceRule.getDay()>0){
            List<Schedule> scheduleList= new ArrayList<>();


            long startDay = conferenceRule.getStartDay();
            long endDay = conferenceRule.getEndDay();
            String startTime = conference.getStartTime();
            String endTime = conference.getEndTime();
            long scheduleDay = startDay;
            int gap = conferenceRule.getGap();
            int ordinalMonth = conferenceRule.getOrdinalMonth();
            int day = conferenceRule.getDay();


            if(getMonth(scheduleDay)<=ordinalMonth&&conferenceRule.getDay()<=day){

            }else {
                scheduleDay = addYear(scheduleDay,gap);
            }
            scheduleDay = addMonth(scheduleDay,ordinalMonth-getMonth(scheduleDay));
            scheduleDay = addDay(scheduleDay,day-getDayOfMonth(scheduleDay));

            while (scheduleDay<endDay){
                Schedule schedule = new Schedule();
                schedule.setStartTime(getYMDDate(scheduleDay)+" "+startTime);
                schedule.setEndTime(getYMDDate(scheduleDay)+" "+endTime);
                scheduleList.add(schedule);

                scheduleDay = addYear(scheduleDay,gap);
            }

            for(Schedule schedule:scheduleList){
                schedule.setTitle(conference.getTitle());
                schedule.setConferenceNo(conference.getConferenceNo());
            }

            return scheduleList;
        }else {
            return singleConference(conference,conferenceRule);
        }
    }

    @Override
    public List<Schedule> cycleByYearMonth(Conference conference, ConferenceRule conferenceRule) {
        if(conferenceRule.getGap()>0&&conferenceRule.getOrdinalMonth()>0&&conferenceRule.getOrdinalWeek()>0&&conferenceRule.getWeek()!=null){
            List<Schedule> scheduleList= new ArrayList<>();

            long startDay = conferenceRule.getStartDay();
            long endDay = conferenceRule.getEndDay();
            String startTime = conference.getStartTime();
            String endTime = conference.getEndTime();
            long scheduleDay = startDay;
            int gap = conferenceRule.getGap();
            int ordinalMonth = conferenceRule.getOrdinalMonth();
            int ordinalWeek = conferenceRule.getOrdinalWeek();
            int[] week = weekTransferToInt(conferenceRule.getWeek());
            //获取当前月份
            //如果比ordinalMonth大，加一年
            if(getMonth(scheduleDay)<=ordinalMonth && getWeekOfMonth(scheduleDay)<=ordinalWeek && getDayOfWeek(scheduleDay)<=week[0]){

            }else {
                scheduleDay = addYear(scheduleDay,gap);
            }
            //该年的指定月份
            scheduleDay = addMonth(scheduleDay,ordinalMonth-getMonth(scheduleDay));
            //指定月份的第一天
            scheduleDay = addDay(scheduleDay,-getDayOfMonth(scheduleDay)+1);

            long firstDay = scheduleDay;
            /*
            循环
            do 获取该月份指定日期
            年份加gap
             */
            while(scheduleDay<endDay){
                scheduleDay =firstDay;
                scheduleDay = addWeek(scheduleDay,ordinalWeek-getWeekOfMonth(scheduleDay));
                scheduleDay = addDay(scheduleDay,week[0]-getDayOfWeek(scheduleDay));

                Schedule scheduleAdd = new Schedule();
                scheduleAdd.setStartTime(getYMDDate(scheduleDay)+" "+startTime);
                scheduleAdd.setEndTime(getYMDDate(scheduleDay)+" "+endTime);
                scheduleList.add(scheduleAdd);

                firstDay = addYear(firstDay,gap);

            }

            for(Schedule s:scheduleList){
                s.setTitle(conference.getTitle());
                s.setConferenceNo(conference.getConferenceNo());
            }

            return scheduleList;
        }else {
            return singleConference(conference,conferenceRule);
        }
    }

    @Override
    public List<Schedule> scheduleSort(List<Schedule> scheduleList) {
        ScheduleComparator scheduleComparator = new ScheduleComparator();
        Collections.sort(scheduleList,scheduleComparator);
        return scheduleList;
    }


}
