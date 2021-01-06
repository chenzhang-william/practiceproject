package com.yealink.level1.service.impl;

import com.yealink.level1.bean.ConferenceParticipant;
import com.yealink.level1.domain.ConferenceParticipantMapper;
import com.yealink.level1.service.ConferenceManageService;
import com.yealink.level1.service.ConferenceParticipantService;
import com.yealink.level1.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author zhangchen
 * @description ConferenceParticipantServiceImpl
 * @date 2021/1/6 11:07
 */
@Service
@Transactional
@Validated
public class ConferenceParticipantServiceImpl implements ConferenceParticipantService {
    @Autowired
    private ConferenceManageService conferenceManageService;
    @Autowired
    private ConferenceParticipantMapper conferenceParticipantMapper;
    @Autowired
    private StaffService staffService;

    @Override
    public void addParticipant(ConferenceParticipant conferenceParticipant) {
        long now = new Date().getTime();
        conferenceParticipant.setCreateTime(now);
        conferenceParticipant.setModifyTime(now);
        conferenceParticipantMapper.add(conferenceParticipant);

    }

    @Override
    public void deleteOneParticipant(String conferenceNo, String mobile) {
        conferenceParticipantMapper.delete(findParticipant(conferenceNo,mobile).getId());
    }

    @Override
    public void deleteParticipant(String conferenceNo) {
        String conferenceId = conferenceManageService.findIdByNo(conferenceNo);

        conferenceParticipantMapper.deleteAllParticipantOfConference(conferenceId);
    }

    @Override
    public @NotNull(message = "无相关会议") List<String> findConferenceIdList(String mobile) {
        return conferenceParticipantMapper.findConference(staffService.findIdByMobile(mobile));

    }

    @Override
    public boolean isParticipantExist(String conferenceNo, String mobile) {
        if(findParticipant(conferenceNo,mobile)!=null) return true;
        else return false;
    }

    @Override
    public void updateParticipant(ConferenceParticipant conferenceParticipant) {
        conferenceParticipant.setModifyTime(new Date().getTime());
        conferenceParticipantMapper.update(conferenceParticipant);
    }


    @Override
    public List<String> findParticipantIdList(String conferenceNo) {
        return conferenceParticipantMapper.findParticipant(conferenceManageService.findIdByNo(conferenceNo));
    }

    @Override
    public ConferenceParticipant findParticipant(String conferenceNo, String mobile) {
        ConferenceParticipant conferenceParticipant = new ConferenceParticipant();
        conferenceParticipant.setConferenceId(conferenceManageService.findIdByNo(conferenceNo));
        conferenceParticipant.setParticipantId(staffService.findIdByMobile(mobile));
        return conferenceParticipantMapper.find(conferenceParticipant);
    }
}
