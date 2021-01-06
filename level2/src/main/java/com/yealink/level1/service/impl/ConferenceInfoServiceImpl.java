package com.yealink.level1.service.impl;

import com.yealink.level1.bean.*;
import com.yealink.level1.bean.request.ConferenceRequest;
import com.yealink.level1.bean.result.ErrorCode;
import com.yealink.level1.bean.result.Result;
import com.yealink.level1.service.ConferenceInfoService;
import com.yealink.level1.service.ConferenceManageService;
import com.yealink.level1.service.ConferenceParticipantService;
import com.yealink.level1.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;

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

        if(staffService.isStaffExist(staff)){

            Conference conference = new Conference();
            conference.setConferenceNo(conferenceRequest.getConferenceNo());
            if(!conferenceManageService.isConferenceExist(conference)){
                //2.判断无重复会议后，添加进数据库
                ConferenceRule conferenceRule = new ConferenceRule();
                conferenceRule.setType(conferenceRequest.getType());
                conferenceRule.setGap(conferenceRequest.getGap());
                conferenceRule.setDay(conferenceRequest.getDay());
                conferenceRule.setWeek(conferenceRequest.getWeek());
                conferenceRule.setOrdinalWeek(conferenceRequest.getOrdinalWeek());
                conferenceRule.setOrdinalMonth(conferenceRequest.getOrdinalMonth());
                if(conferenceRequest.getStartDay()<conferenceRequest.getEndDay()) {
                    conferenceRule.setStartDay(conferenceRequest.getStartDay());
                    conferenceRule.setEndDay(conferenceRequest.getEndDay());
                }else return Result.failure(ErrorCode.TIME_IS_ILLEGAL);
                conference.setTitle(conferenceRequest.getTitle());
                conference.setStartTime(conferenceRequest.getStartTime());
                conference.setEndTime(conferenceRequest.getEndTime());
                conferenceManageService.addConference(conference,conferenceRule);

                //3.将创建者添加进participant,角色为创建者，状态为未在会议中
                ConferenceParticipant conferenceParticipant = new ConferenceParticipant();
                conferenceParticipant.setConferenceId(conferenceManageService.findConferenceByNo(conference).getId());
                conferenceParticipant.setParticipantId(staffService.findStaffByMobile(staff).getId());
                conferenceParticipant.setStatus(0);
                conferenceParticipant.setRole(0);
                conferenceParticipantService.addParticipant(conferenceParticipant);
                return Result.success();
            }else return Result.failure(ErrorCode.CONFERENCE_HAS_EXIST);
        }else return Result.failure(ErrorCode.STAFF_IS_NOT_EXIST);
    }

    @Override
    public Result deleteConference(ConferenceRequest conferenceRequest) {
        //1.判断是否是创建者，会议号和创建者是否吻合
        String no = conferenceRequest.getConferenceNo();
        if(conferenceManageService.hasPermission(no,conferenceRequest.getMobile())){
            //2.删除相关participant
            conferenceParticipantService.deleteParticipant(no);
            //3.删除相关rule
            conferenceManageService.deleteRule(no);
            //4.删除conference
            conferenceManageService.deleteConference(no);
            return Result.success();
        }else return Result.failure(ErrorCode.NO_PERMISSION);
    }

    @Override
    public Result updateConferenceInfo(ConferenceRequest conferenceRequest) {
        //1.判断是否是创建者，会议号和创建者是否吻合
        String no = conferenceRequest.getConferenceNo();
        if(conferenceManageService.hasPermission(no,conferenceRequest.getMobile())){
            //2.通过会议号获取oldConference
            Conference conference = new Conference();
            conference.setConferenceNo(no);
            conference = conferenceManageService.findConferenceByNo(conference);
            //3.将新的不为空的信息封装进conference
            if(conferenceRequest.getNewConferenceNo()!=null) conference.setConferenceNo(conferenceRequest.getNewConferenceNo());
            if(conferenceRequest.getTitle()!=null) conference.setTitle(conferenceRequest.getTitle());
            if(conferenceRequest.getStartTime()!=0&&conferenceRequest.getStartTime()<conference.getEndTime()) conference.setStartTime(conferenceRequest.getStartTime());
            if(conferenceRequest.getEndTime()!=0&&conferenceRequest.getEndTime()>conference.getStartTime()) conference.setEndTime(conferenceRequest.getEndTime());
            //4.判断新的no不冲突，调用更新服务
            if(!conferenceManageService.isConferenceExist(conference)){
                conferenceManageService.updateConference(conference);
                return Result.success();
            }else return Result.failure(ErrorCode.CONFERENCE_HAS_EXIST);
        }else return Result.failure(ErrorCode.NO_PERMISSION);

    }

    @Override
    public Result updateConferenceRule(ConferenceRequest conferenceRequest) {
        //1.判断是否是创建者，会议号和创建者是否吻合
        String no = conferenceRequest.getConferenceNo();
        if(conferenceManageService.hasPermission(no,conferenceRequest.getMobile())){
            Conference conference = new Conference();
            conference.setConferenceNo(conferenceRequest.getConferenceNo());
            //2.通过会议号获取oldRule
            ConferenceRule conferenceRule = new ConferenceRule();
            conferenceRule = conferenceManageService.findRuleByNo(conference);
            //3.将新的规则封装进rule
            if(conferenceRequest.getType()!=0) conferenceRule.setType(conferenceRequest.getType());
            if(conferenceRequest.getGap()!=0) conferenceRule.setGap(conferenceRequest.getGap());
            if(conferenceRequest.getDay()!=0) conferenceRule.setDay(conferenceRequest.getDay());
            if(conferenceRequest.getWeek()!=0) conferenceRule.setWeek(conferenceRequest.getWeek());
            if(conferenceRequest.getOrdinalWeek()!=0) conferenceRule.setOrdinalWeek(conferenceRequest.getOrdinalWeek());
            if(conferenceRequest.getOrdinalMonth()!=0) conferenceRule.setOrdinalMonth(conferenceRequest.getOrdinalMonth());
            if(conferenceRequest.getStartDay()!=0&&conferenceRequest.getStartDay()<conferenceRule.getEndDay()) conferenceRule.setStartDay(conferenceRequest.getStartDay());
            if(conferenceRequest.getEndDay()!=0&&conferenceRequest.getEndTime()>conferenceRule.getStartDay()) conferenceRule.setEndDay(conferenceRequest.getEndDay());
            //4.调用更新服务
            conferenceManageService.updateRule(conferenceRule);
            return Result.success();
        }else return Result.failure(ErrorCode.NO_PERMISSION);

    }

    @Override
    public Result findConferenceOfStaff(ConferenceRequest conferenceRequest) {
        //1.在participant查出conferenceId的list

        List<String> conferenceIdList= conferenceParticipantService.findConferenceIdList(conferenceRequest.getMobile());
        //2.获取conference的list
        List<Conference> conferenceList = new ArrayList<>();
        for(String id:conferenceIdList){
            conferenceList.add(conferenceManageService.findConferenceById(id));
        }

        //3.将list排序
        return Result.success(conferenceList);
    }

    //取出规则后算出所有的会议
    @Override
    public Result findScheduleOfStaff(ConferenceRequest conferenceRequest) {
        //1.在participant查出conferenceId的list
        List<String> conferenceIdList= conferenceParticipantService.findConferenceIdList(conferenceRequest.getMobile());
        //2.查出所有rule的idList
        List<String> ruleIdList = new ArrayList<>();
        for(String id:conferenceIdList){
            ruleIdList.add(conferenceManageService.findRuleIdById(id));
        }
        //3.算出所有的会议包含title，No，时间，日期，加入list
        List<Schedule> scheduleList= conferenceManageService.findScheduleOfStaff(ruleIdList);
        //4.排序

        return Result.success(scheduleList);
    }

    @Override
    public Result addParticipant(ConferenceRequest conferenceRequest) {
        //1.获取会议号和手机号，分别判断是否存在
        Conference conference = new Conference();
        conference.setConferenceNo(conferenceRequest.getConferenceNo());
        if(conferenceManageService.isConferenceExist(conference)){
            Staff staff = new Staff();
            staff.setMobile(conferenceRequest.getMobile());
            if(staffService.isStaffExist(staff)){
                if(!conferenceParticipantService.isParticipantExist(conference.getConferenceNo(),staff.getMobile())){

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
                }else return Result.failure(ErrorCode.PARTICIPANT_HAS_EXIST);
            }else return Result.failure(ErrorCode.STAFF_IS_NOT_EXIST);
        }else return Result.failure(ErrorCode.CONFERENCE_IS_NOT_EXIST);


    }

    @Override
    public Result deleteParticipant(ConferenceRequest conferenceRequest) {
        //1.获取会议号和手机号，分别判断是否存在
        Conference conference = new Conference();
        conference.setConferenceNo(conferenceRequest.getConferenceNo());
        if(conferenceManageService.isConferenceExist(conference)){
                    conferenceParticipantService.deleteParticipant(conference.getConferenceNo());
                    return Result.success();
        }else return Result.failure(ErrorCode.CONFERENCE_IS_NOT_EXIST);
    }

    @Override
    public Result deleteOneParticipant(ConferenceRequest conferenceRequest) {
        if(conferenceParticipantService.isParticipantExist(conferenceRequest.getConferenceNo(),conferenceRequest.getMobile())){
            conferenceParticipantService.deleteOneParticipant(conferenceRequest.getConferenceNo(),conferenceRequest.getMobile());
            return Result.success();
        }else return Result.failure(ErrorCode.CONFERENCE_IS_NOT_EXIST);
    }

    @Override
    public Result updateParticipant(ConferenceRequest conferenceRequest) {
        //1.获取会议号和手机号，分别判断是否存在
        //2.存在的话取出会议id和staffId封装进participant
        //3.设置status
        //4.调用update
        if(conferenceParticipantService.isParticipantExist(conferenceRequest.getConferenceNo(),conferenceRequest.getMobile())){
            ConferenceParticipant conferenceParticipant = conferenceParticipantService.findParticipant(conferenceRequest.getConferenceNo(),conferenceRequest.getMobile());
            if(conferenceRequest.getStatus()!=0) conferenceParticipant.setStatus(conferenceRequest.getStatus());
            if(conferenceRequest.getRole()!=0) conferenceParticipant.setRole(conferenceRequest.getRole());
            conferenceParticipantService.updateParticipant(conferenceParticipant);
            return Result.success();
        }else return Result.failure(ErrorCode.CONFERENCE_IS_NOT_EXIST);
    }

    @Override
    public Result findParticipantOfConference(ConferenceRequest conferenceRequest) {
        //1.获取会议号，判断是否存在
        //2.取出会议id
        //3.查出participantList
        Conference conference = new Conference();
        conference.setConferenceNo(conferenceRequest.getConferenceNo());
        if(conferenceManageService.isConferenceExist(conference)){
            return Result.success(conferenceParticipantService.findParticipantIdList(conference.getConferenceNo()));
        }else return Result.failure(ErrorCode.CONFERENCE_IS_NOT_EXIST);
    }
}
