package com.yealink.level1.service.impl;

import com.yealink.level1.bean.Conference;
import com.yealink.level1.bean.ConferenceParticipant;
import com.yealink.level1.bean.ConferenceRule;
import com.yealink.level1.bean.Schedule;
import com.yealink.level1.domain.ConferenceMapper;
import com.yealink.level1.domain.ConferenceRuleMapper;
import com.yealink.level1.service.ConferenceManageService;
import com.yealink.level1.service.ConferenceParticipantService;
import com.yealink.level1.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    public List<Schedule> findScheduleOfStaff(List<String> ruleIdList) {
        List<Schedule> scheduleList= new ArrayList<>();
        for(String id:ruleIdList){
            ConferenceRule conferenceRule = conferenceRuleMapper.find(id);
            switch (conferenceRule.getType()){
                case 0:
                    scheduleList.addAll(singleConference(conferenceRule));
                    break;
                case 1:
                    scheduleList.addAll(cycleByDay(conferenceRule));
                    break;
                case 2:
                    scheduleList.addAll(cycleByWeek(conferenceRule));
                    break;
                case 30:
                    scheduleList.addAll(cycleByMonthDay(conferenceRule));
                    break;
                case 31:
                    scheduleList.addAll(cycleByMonthWeek(conferenceRule));
                    break;
                case 40:
                    scheduleList.addAll(cycleByYearDay(conferenceRule));
                    break;
                case 41:
                    scheduleList.addAll(cycleByYearMonth(conferenceRule));
                    break;

            }
        }
        return scheduleList;
    }

    @Override
    public List<Schedule> singleConference(ConferenceRule conferenceRule) {
        return null;
    }

    @Override
    public List<Schedule> cycleByDay(ConferenceRule conferenceRule) {
        return null;
    }

    @Override
    public List<Schedule> cycleByWeek(ConferenceRule conferenceRule) {
        return null;
    }

    @Override
    public List<Schedule> cycleByMonthDay(ConferenceRule conferenceRule) {
        return null;
    }

    @Override
    public List<Schedule> cycleByMonthWeek(ConferenceRule conferenceRule) {
        return null;
    }

    @Override
    public List<Schedule> cycleByYearDay(ConferenceRule conferenceRule) {
        return null;
    }

    @Override
    public List<Schedule> cycleByYearMonth(ConferenceRule conferenceRule) {
        return null;
    }


}
