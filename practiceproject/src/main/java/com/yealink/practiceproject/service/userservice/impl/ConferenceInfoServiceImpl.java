package com.yealink.practiceproject.service.userservice.impl;

import com.yealink.practiceproject.bean.Conference;
import com.yealink.practiceproject.bean.ConferenceParticipant;
import com.yealink.practiceproject.bean.ConferenceRule;
import com.yealink.practiceproject.bean.Staff;
import com.yealink.practiceproject.bean.request.ConferenceRequest;
import com.yealink.practiceproject.bean.result.ErrorCode;
import com.yealink.practiceproject.bean.result.Result;
import com.yealink.practiceproject.bean.result.Schedule;
import com.yealink.practiceproject.service.domain.ConferenceManageService;
import com.yealink.practiceproject.service.domain.ConferenceParticipantService;
import com.yealink.practiceproject.service.domain.StaffService;
import com.yealink.practiceproject.service.userservice.ConferenceInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;

import static com.yealink.practiceproject.util.ConstantPool.Role.CONFERENCE_ADMIN_ROLE;
import static com.yealink.practiceproject.util.ConstantPool.Role.CONFERENCE_PARTICIPANT_STATUS_OFF;
import static com.yealink.practiceproject.util.DateUtil.getYMDTimeStamp;
import static com.yealink.practiceproject.util.DateUtil.mergeYMDHMTimeStamp;

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
        Conference conference = new Conference();
        ConferenceRule conferenceRule = new ConferenceRule();

        staff.setMobile(conferenceRequest.getMobile());

        if (!staffService.isStaffExist(conferenceRequest.getMobile())) {
            return Result.failure(ErrorCode.STAFF_IS_NOT_EXIST);
        }

        if (conferenceManageService.isConferenceExist(conferenceRequest.getConferenceNo())) {
            return Result.failure(ErrorCode.CONFERENCE_HAS_EXIST);
        }

        if (newConferenceAssignment(conferenceRequest, conference)) {
            return Result.failure(ErrorCode.TIME_IS_ILLEGAL);
        }

        if (newRuleAssignment(conferenceRule, conferenceRequest)) {
            return Result.failure(ErrorCode.TIME_IS_ILLEGAL);
        }

        if (conferenceRoomConflictDetection(conference, conferenceRule)) {
            return Result.failure(ErrorCode.CONFERENCE_ROOM_CONFLICT);
        }

        conferenceManageService.addConference(conference, conferenceRule);

        String conferenceId = conference.getId();
        //3.将创建者添加进participant,角色为创建者，状态为未在会议中
        addAdminParticipant(staff.getMobile(), conferenceId);

        return Result.success();
    }

    private boolean conferenceRoomConflictDetection(Conference conference, ConferenceRule conferenceRule) {

        if (conference.getConferenceRoom() != null) {
            return !conferenceManageService.conferenceRoomDetection(conference, conferenceRule);
        }

        return false;
    }

    private boolean newConferenceAssignment(ConferenceRequest conferenceRequest, Conference conference) {

        conference.setConferenceNo(conferenceRequest.getConferenceNo());
        conference.setConferenceRoom(conferenceRequest.getConferenceRoom());
        conference.setTitle(conferenceRequest.getTitle());

        if (judgeTime(conferenceRequest)) {
            return true;
        }

        conference.setStartTime(conferenceRequest.getStartTime());
        conference.setEndTime(conferenceRequest.getEndTime());

        return false;
    }

    private boolean judgeTime(ConferenceRequest conferenceRequest) {
        return (mergeYMDHMTimeStamp(conferenceRequest.getStartDay(), conferenceRequest.getStartTime())
                > mergeYMDHMTimeStamp(conferenceRequest.getEndDay(), conferenceRequest.getEndTime()));
    }

    private boolean newRuleAssignment(ConferenceRule conferenceRule, ConferenceRequest conferenceRequest) {

        conferenceRule.setType(conferenceRequest.getType());
        conferenceRule.setGap(conferenceRequest.getGap());
        conferenceRule.setDay(conferenceRequest.getDay());
        conferenceRule.setWeek(conferenceRequest.getWeek());
        conferenceRule.setOrdinalWeek(conferenceRequest.getOrdinalWeek());
        conferenceRule.setOrdinalMonth(conferenceRequest.getOrdinalMonth());

        long startDay = getYMDTimeStamp(conferenceRequest.getStartDay());
        long endDay = getYMDTimeStamp(conferenceRequest.getEndDay());

        if (startDay > endDay) {
            return true;
        }

        conferenceRule.setStartDay(startDay);
        conferenceRule.setEndDay(endDay);
        return false;
    }

    private void addAdminParticipant(String mobile, String conferenceId) {

        ConferenceParticipant conferenceParticipant = new ConferenceParticipant();

        conferenceParticipant.setConferenceId(conferenceId);
        conferenceParticipant.setParticipantId(staffService.findIdByMobile(mobile));
        conferenceParticipant.setStatus(CONFERENCE_PARTICIPANT_STATUS_OFF);
        conferenceParticipant.setRole(CONFERENCE_ADMIN_ROLE);

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
        if (conferenceAssignment(conferenceRequest, conference)) {
            return Result.failure(ErrorCode.TIME_IS_ILLEGAL);
        }
        //4.判断新的no和room不冲突，调用更新服务
        if (conferenceManageService.isConferenceExist(conference.getConferenceNo())) {
            return Result.failure(ErrorCode.CONFERENCE_HAS_EXIST);
        }
        if (conferenceRoomConflictDetection(conference, conferenceManageService.findRuleByNo(conference))) {
            return Result.failure(ErrorCode.CONFERENCE_ROOM_CONFLICT);
        }

        conferenceManageService.updateConference(conference);

        return Result.success();
    }

    private boolean conferenceAssignment(ConferenceRequest conferenceRequest, Conference conference) {

        if (conferenceRequest.getNewConferenceNo() != null) {
            conference.setConferenceNo(conferenceRequest.getNewConferenceNo());
        }

        if (conferenceRequest.getConferenceRoom() != null) {
            conference.setConferenceRoom(conferenceRequest.getConferenceRoom());
        }

        if (conferenceRequest.getTitle() != null) {
            conference.setTitle(conferenceRequest.getTitle());
        }

        if (conferenceRequest.getStartTime() != null && conferenceRequest.getEndTime() != null) {
            if (judgeTime(conferenceRequest)) {
                return true;
            }
            conference.setStartTime(conferenceRequest.getStartTime());
            conference.setEndTime(conferenceRequest.getEndTime());
        }

        return false;
    }

    @Override
    public Result updateConferenceRule(ConferenceRequest conferenceRequest) {
        //1.判断是否是创建者，会议号和创建者是否吻合
        String no = conferenceRequest.getConferenceNo();

        if (!conferenceManageService.hasPermission(no, conferenceRequest.getMobile())) {
            return Result.failure(ErrorCode.NO_PERMISSION);
        }

        Conference conference = new Conference();
        conference.setConferenceNo(conferenceRequest.getConferenceNo());
        //2.通过会议号获取oldRule
        ConferenceRule conferenceRule = conferenceManageService.findRuleByNo(conference);
        //3.将新的规则封装进rule
        if (ruleAssignment(conferenceRequest, conferenceRule)) {
            return Result.failure(ErrorCode.TIME_IS_ILLEGAL);
        }

        if (conferenceRoomConflictDetection(conference, conferenceRule)) {
            return Result.failure(ErrorCode.CONFERENCE_ROOM_CONFLICT);
        }
        //4.调用更新服务
        conferenceManageService.updateRule(conferenceRule);

        return Result.success();
    }

    private boolean ruleAssignment(ConferenceRequest conferenceRequest, ConferenceRule conferenceRule) {

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

            if (startDay > conferenceRule.getEndDay()) {
                return true;
            }

            conferenceRule.setStartDay(startDay);
        }
        if (conferenceRequest.getEndDay() != null) {

            long endDay = getYMDTimeStamp(conferenceRequest.getEndDay());

            if (endDay < conferenceRule.getStartDay()) {
                return true;
            }

            conferenceRule.setEndDay(endDay);
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

        if (!conferenceManageService.isConferenceExist(conferenceRequest.getConferenceNo())) {
            return Result.failure(ErrorCode.CONFERENCE_IS_NOT_EXIST);
        }
        if (!staffService.isStaffExist(conferenceRequest.getMobile())) {
            return Result.failure(ErrorCode.STAFF_IS_NOT_EXIST);
        }
        if (conferenceParticipantService.isParticipantExist(conferenceRequest.getConferenceNo(), conferenceRequest.getMobile())) {
            return Result.failure(ErrorCode.PARTICIPANT_HAS_EXIST);
        }

        ConferenceParticipant conferenceParticipant = new ConferenceParticipant();

        conferenceParticipant.setConferenceId(conferenceManageService.findIdByNo(conferenceRequest.getConferenceNo()));

        conferenceParticipant.setParticipantId(staffService.findIdByMobile(conferenceRequest.getMobile()));
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

        if (!conferenceManageService.isConferenceExist(conferenceRequest.getConferenceNo())) {
            return Result.failure(ErrorCode.CONFERENCE_IS_NOT_EXIST);
        }

        conferenceParticipantService.deleteParticipant(conferenceRequest.getConferenceNo());

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

        if (conferenceManageService.isConferenceExist(conferenceRequest.getConferenceNo())) {
            return Result.failure(ErrorCode.CONFERENCE_IS_NOT_EXIST);
        }

        return Result.success(conferenceParticipantService.findParticipantIdList(conferenceRequest.getConferenceNo()));
    }

    @Override
    public Result getOccupancyOfConferenceRoom(ConferenceRequest conferenceRequest) {

        if (conferenceRequest.getConferenceRoom() == null) {
            return Result.failure(ErrorCode.PARAM_IS_INVALID);
        }

        return Result.success(conferenceManageService.checkOccupancyOfConferenceRoom(conferenceRequest.getConferenceRoom()));
    }
}
