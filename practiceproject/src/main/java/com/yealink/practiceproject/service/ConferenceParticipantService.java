package com.yealink.practiceproject.service;

import com.yealink.practiceproject.bean.ConferenceParticipant;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author zhangchen
 * @description ConferenceParticipantService
 * @date 2021/1/6 9:55
 */
public interface ConferenceParticipantService {

    void addParticipant(ConferenceParticipant conferenceParticipant);

    void deleteOneParticipant(String conferenceNo, String mobile);

    void deleteParticipant(String conferenceNo);

    @NotNull(message = "无相关会议") List<String> findConferenceIdList(String mobile);

    boolean isParticipantExist(String conferenceNo, String mobile);

    void updateParticipant(ConferenceParticipant conferenceParticipant);


    List<String> findParticipantIdList(String conferenceNo);

    ConferenceParticipant findParticipant(String conferenceNo, String mobile);


}
