package com.yealink.level3.service.impl;

import com.yealink.level3.bean.*;
import com.yealink.level3.bean.request.ConferenceRequest;
import com.yealink.level3.bean.result.ErrorCode;
import com.yealink.level3.bean.result.Result;
import com.yealink.level3.bean.result.Schedule;
import com.yealink.level3.service.ConferenceInfoService;
import com.yealink.level3.service.ConferenceManageService;
import com.yealink.level3.service.ConferenceParticipantService;
import com.yealink.level3.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;

import static com.yealink.level3.util.DateUtil.*;

/**
 * @author zhangchen
 * @description ConferenceInfoServiceImpl
 * @date 2021/1/6 9:03
 */
@Service
@Transactional
@Validated
public class ConferenceInfoServiceImpl implements ConferenceInfoService {
    @Autowired
    private ConferenceManageService conferenceManageService;
    @Autowired
    private ConferenceParticipantService conferenceParticipantService;
    @Autowired
    private StaffService staffService;


    @Override
    public Result addConference(ConferenceRequest conferenceRequest) {
        //1.判断创建者是否是员工，获取会议信息，会议规则，封装成conference和rule
        Staff staff = new Staff();
        staff.setMobile(conferenceRequest.getMobile());

        if (!staffService.isStaffExist(staff)) {
            return Result.failure(ErrorCode.STAFF_IS_NOT_EXIST);
        }

        if (conferenceManageService.isConferenceExist(conferenceRequest.getConferenceNo())) {
            return Result.failure(ErrorCode.CONFERENCE_HAS_EXIST);
        }
        Conference conference = new Conference();
        conference.setConferenceNo(conferenceRequest.getConferenceNo());
        conference.setTitle(conferenceRequest.getTitle());
        if (!judgeTime(conferenceRequest)) return Result.failure(ErrorCode.TIME_IS_ILLEGAL);
        conference.setStartTime(conferenceRequest.getStartTime());
        conference.setEndTime(conferenceRequest.getEndTime());

        ConferenceRule conferenceRule = new ConferenceRule();
        if (ruleValuation(conferenceRule, conferenceRequest) != Result.success()){
            return Result.failure(ErrorCode.TIME_IS_ILLEGAL);
        }

        conferenceManageService.addConference(conference, conferenceRule);

        String conferenceId = conference.getId();
        //3.将创建者添加进participant,角色为创建者，状态为未在会议中
        addAdminParticipant(staff.getMobile(), conferenceId);

        return Result.success();
    }

    private boolean judgeTime(ConferenceRequest conferenceRequest) {
        return !(mergeYMDHMTimeStamp(conferenceRequest.getStartDay(), conferenceRequest.getStartTime())
                >= mergeYMDHMTimeStamp(conferenceRequest.getEndDay(), conferenceRequest.getEndTime()));
    }

    private Result ruleValuation(ConferenceRule conferenceRule, ConferenceRequest conferenceRequest) {

        conferenceRule.setType(conferenceRequest.getType());
        conferenceRule.setGap(conferenceRequest.getGap());
        conferenceRule.setDay(conferenceRequest.getDay());
        conferenceRule.setWeek(conferenceRequest.getWeek());
        conferenceRule.setOrdinalWeek(conferenceRequest.getOrdinalWeek());
        conferenceRule.setOrdinalMonth(conferenceRequest.getOrdinalMonth());
        long startDay = getYMDTimeStamp(conferenceRequest.getStartDay());
        long endDay = getYMDTimeStamp(conferenceRequest.getEndDay());

        if (startDay > endDay) return Result.failure(ErrorCode.TIME_IS_ILLEGAL);
        conferenceRule.setStartDay(startDay);
        conferenceRule.setEndDay(endDay);
        return Result.success();
    }

    private void addAdminParticipant(String mobile, String conferenceId) {
        ConferenceParticipant conferenceParticipant = new ConferenceParticipant();
        conferenceParticipant.setConferenceId(conferenceId);
        conferenceParticipant.setParticipantId(staffService.findIdByMobile(mobile));
        conferenceParticipant.setStatus(0);
        conferenceParticipant.setRole(0);
        conferenceParticipantService.addParticipant(conferenceParticipant);
    }

    @Override
    public Result deleteConference(ConferenceRequest conferenceRequest) {
        //1.判断是否是创建者，会议号和创建者是否吻合
        String no = conferenceRequest.getConferenceNo();
        if (!conferenceManageService.hasPermission(no, conferenceRequest.getMobile())) {
            return Result.failure(ErrorCode.NO_PERMISSION);
        }
        //2.获取ruleid
        String ruleId = conferenceManageService.findRuleIdByNo(no);
        //3.删除参会人
        conferenceParticipantService.deleteParticipant(no);
        //4.删除会议
        conferenceManageService.deleteConference(no);
        //4.删除rule
        conferenceManageService.deleteRule(ruleId);
        return Result.success();
    }

    @Override
    public Result updateConferenceInfo(ConferenceRequest conferenceRequest) {
        //1.判断是否是创建者，会议号和创建者是否吻合
        String no = conferenceRequest.getConferenceNo();
        if (!conferenceManageService.hasPermission(no, conferenceRequest.getMobile())) {
            return Result.failure(ErrorCode.NO_PERMISSION);
        }
        //2.通过会议号获取oldConference
        Conference conference = new Conference();
        conference.setConferenceNo(no);
        conference = conferenceManageService.findConferenceByNo(conference);
        //3.将新的不为空的信息封装进conference
        conferenceValuation(conferenceRequest, conference);
        //4.判断新的no不冲突，调用更新服务
        if (conferenceManageService.isConferenceExist(conference.getConferenceNo())) {
            return Result.failure(ErrorCode.CONFERENCE_HAS_EXIST);
        }
        conferenceManageService.updateConference(conference);
        return Result.success();
    }

    private void conferenceValuation(ConferenceRequest conferenceRequest, Conference conference) {
        if (conferenceRequest.getNewConferenceNo() != null) {
            conference.setConferenceNo(conferenceRequest.getNewConferenceNo());
        }
        if (conferenceRequest.getTitle() != null) {
            conference.setTitle(conferenceRequest.getTitle());
        }
        if (conferenceRequest.getStartTime() != null && conferenceRequest.getEndTime() != null) {
            String startDay = getYMDDate(conferenceManageService.findRuleByNo(conference).getStartDay());
            long startTime = mergeYMDHMTimeStamp(startDay, conferenceRequest.getStartTime());
            long endTime = mergeYMDHMTimeStamp(startDay, conferenceRequest.getEndTime());
            if (startTime < endTime) {
                conference.setStartTime(conferenceRequest.getStartTime());
                conference.setEndTime(conferenceRequest.getEndTime());
            }
        }
    }

    @Override
    public Result updateConferenceRule(ConferenceRequest conferenceRequest) {
        //1.判断是否是创建者，会议号和创建者是否吻合
        String no = conferenceRequest.getConferenceNo();
        if (conferenceManageService.hasPermission(no, conferenceRequest.getMobile())) {
            Conference conference = new Conference();
            conference.setConferenceNo(conferenceRequest.getConferenceNo());
            //2.通过会议号获取oldRule
            ConferenceRule conferenceRule = conferenceManageService.findRuleByNo(conference);
            //3.将新的规则封装进rule
            if (newRuleValuation(conferenceRequest, conferenceRule)) return Result.failure(ErrorCode.TIME_IS_ILLEGAL);
            //4.调用更新服务
            conferenceManageService.updateRule(conferenceRule);
            return Result.success();
        } else return Result.failure(ErrorCode.NO_PERMISSION);

    }

    private boolean newRuleValuation(ConferenceRequest conferenceRequest, ConferenceRule conferenceRule) {
        if (conferenceRequest.getType() != 0) {
            conferenceRule.setType(conferenceRequest.getType());
        }
        if (conferenceRequest.getGap() != 0) {
            conferenceRule.setGap(conferenceRequest.getGap());
        }
        if (conferenceRequest.getDay() != 0) {
            conferenceRule.setDay(conferenceRequest.getDay());
        }
        if (conferenceRequest.getWeek() != null) {
            conferenceRule.setWeek(conferenceRequest.getWeek());
        }
        if (conferenceRequest.getOrdinalWeek() != 0) {
            conferenceRule.setOrdinalWeek(conferenceRequest.getOrdinalWeek());
        }
        if (conferenceRequest.getOrdinalMonth() != 0) {
            conferenceRule.setOrdinalMonth(conferenceRequest.getOrdinalMonth());
        }
        if (conferenceRequest.getStartDay() != null) {
            long startDay = getYMDTimeStamp(conferenceRequest.getStartDay());
            if (startDay <= conferenceRule.getEndDay()) conferenceRule.setStartDay(startDay);
            else return true;
        }
        if (conferenceRequest.getEndDay() != null) {
            long endDay = getYMDTimeStamp(conferenceRequest.getEndDay());
            if (endDay >= conferenceRule.getStartDay()) conferenceRule.setEndDay(endDay);
            else return true;
        }
        return false;
    }

    @Override
    public Result findConferenceOfStaff(ConferenceRequest conferenceRequest) {
        //1.在participant查出conferenceId的list

        List<String> conferenceIdList = conferenceParticipantService.findConferenceIdList(conferenceRequest.getMobile());
        //2.获取conference的list
        List<Conference> conferenceList = new ArrayList<>();
        for (String id : conferenceIdList) {
            conferenceList.add(conferenceManageService.findConferenceById(id));
        }

        //3.将list排序
        return Result.success(conferenceList);
    }

    //取出规则后算出所有的会议
    @Override
    public Result findScheduleOfStaff(ConferenceRequest conferenceRequest) {
        //1.在participant查出conferenceId的list
        List<String> conferenceIdList = conferenceParticipantService.findConferenceIdList(conferenceRequest.getMobile());
        //2.查出所有rule的idList
        //3.算出所有的会议包含title，No，时间，日期，加入list
        List<Schedule> scheduleList = conferenceManageService.findSchedule(conferenceIdList);
        //4.排序

        return Result.success(scheduleList);
    }

    @Override
    public Result addParticipant(ConferenceRequest conferenceRequest) {
        //1.获取会议号和手机号，分别判断是否存在
        Conference conference = new Conference();
        conference.setConferenceNo(conferenceRequest.getConferenceNo());
        if (!conferenceManageService.isConferenceExist(conference.getConferenceNo())) {
            return Result.failure(ErrorCode.CONFERENCE_IS_NOT_EXIST);
        }
        Staff staff = new Staff();
        staff.setMobile(conferenceRequest.getMobile());
        if (!staffService.isStaffExist(staff)) {
            return Result.failure(ErrorCode.STAFF_IS_NOT_EXIST);
        }
        if (conferenceParticipantService.isParticipantExist(conference.getConferenceNo(), staff.getMobile())) {
            return Result.failure(ErrorCode.PARTICIPANT_HAS_EXIST);
        }
        //2.存在的话取出会议id和staffId封装进participant
        ConferenceParticipant conferenceParticipant = new ConferenceParticipant();
        conferenceParticipant.setConferenceId(conferenceManageService.findConferenceByNo(conference).getId());
        conferenceParticipant.setParticipantId(staffService.findStaffByMobile(staff).getId());
        //3.将status设为0
        conferenceParticipant.setStatus(0);
        //4.将role设为3
        conferenceParticipant.setStatus(3);
        //5.调用add
        conferenceParticipantService.addParticipant(conferenceParticipant);
        return Result.success();

    }

    @Override
    public Result deleteParticipant(ConferenceRequest conferenceRequest) {
        //1.获取会议号和手机号，分别判断是否存在
        Conference conference = new Conference();
        conference.setConferenceNo(conferenceRequest.getConferenceNo());
        if (!conferenceManageService.isConferenceExist(conference.getConferenceNo())) {
            return Result.failure(ErrorCode.CONFERENCE_IS_NOT_EXIST);
        }
        conferenceParticipantService.deleteParticipant(conference.getConferenceNo());
        return Result.success();
    }

    @Override
    public Result deleteOneParticipant(ConferenceRequest conferenceRequest) {
        if (!conferenceParticipantService.isParticipantExist(conferenceRequest.getConferenceNo(), conferenceRequest.getMobile())) {
            return Result.failure(ErrorCode.CONFERENCE_IS_NOT_EXIST);
        }
        conferenceParticipantService.deleteOneParticipant(conferenceRequest.getConferenceNo(), conferenceRequest.getMobile());
        return Result.success();
    }

    @Override
    public Result updateParticipant(ConferenceRequest conferenceRequest) {
        //1.获取会议号和手机号，分别判断是否存在
        //2.存在的话取出会议id和staffId封装进participant
        //3.设置status
        //4.调用update
        if (!conferenceParticipantService.isParticipantExist(conferenceRequest.getConferenceNo(), conferenceRequest.getMobile())) {
            return Result.failure(ErrorCode.CONFERENCE_IS_NOT_EXIST);
        }
        ConferenceParticipant conferenceParticipant = conferenceParticipantService.findParticipant(conferenceRequest.getConferenceNo(), conferenceRequest.getMobile());
        if (conferenceRequest.getStatus() != 0) {
            conferenceParticipant.setStatus(conferenceRequest.getStatus());
        }
        if (conferenceRequest.getRole() != 0) {
            conferenceParticipant.setRole(conferenceRequest.getRole());
        }
        conferenceParticipantService.updateParticipant(conferenceParticipant);
        return Result.success();
    }

    @Override
    public Result findParticipantOfConference(ConferenceRequest conferenceRequest) {
        //1.获取会议号，判断是否存在
        //2.取出会议id
        //3.查出participantList
        Conference conference = new Conference();
        conference.setConferenceNo(conferenceRequest.getConferenceNo());
        if (conferenceManageService.isConferenceExist(conference.getConferenceNo())) {
            return Result.failure(ErrorCode.CONFERENCE_IS_NOT_EXIST);
        }
        return Result.success(conferenceParticipantService.findParticipantIdList(conference.getConferenceNo()));
    }
}
