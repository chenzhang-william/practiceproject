package com.yealink.practiceproject.service.impl;

import com.yealink.practiceproject.bean.ConferenceParticipant;
import com.yealink.practiceproject.domain.ConferenceMapper;
import com.yealink.practiceproject.domain.ConferenceParticipantMapper;
import com.yealink.practiceproject.service.ConferenceParticipantService;
import com.yealink.practiceproject.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
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
    ConferenceMapper conferenceMapper;
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
        conferenceParticipantMapper.delete(findParticipant(conferenceNo, mobile).getId());
    }

    @Override
    public void deleteParticipant(String conferenceNo) {
        conferenceParticipantMapper.deleteAllParticipantOfConference(conferenceMapper.findIdByNo(conferenceNo));
    }

    @Override
    public @NotNull(message = "无相关会议") List<String> findConferenceIdList(String mobile) {
        return conferenceParticipantMapper.findConference(staffService.findIdByMobile(mobile));
    }

    @Override
    public boolean isParticipantExist(String conferenceNo, String mobile) {
        return findParticipant(conferenceNo, mobile) != null;
    }

    @Override
    public void updateParticipant(ConferenceParticipant conferenceParticipant) {
        conferenceParticipant.setModifyTime(new Date().getTime());
        conferenceParticipantMapper.update(conferenceParticipant);
    }


    @Override
    public List<String> findParticipantIdList(String conferenceNo) {
        return conferenceParticipantMapper.findParticipant(conferenceMapper.findIdByNo(conferenceNo));
    }

    @Override
    public ConferenceParticipant findParticipant(String conferenceNo, String mobile) {
        ConferenceParticipant conferenceParticipant = new ConferenceParticipant();
        conferenceParticipant.setConferenceId(conferenceMapper.findIdByNo(conferenceNo));
        conferenceParticipant.setParticipantId(staffService.findIdByMobile(mobile));
        return conferenceParticipantMapper.find(conferenceParticipant);
    }
}
