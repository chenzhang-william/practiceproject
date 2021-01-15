package com.yealink.level3.service;

import com.yealink.level3.bean.request.ConferenceRequest;
import com.yealink.level3.bean.result.Result;

/**
 * @author zhangchen
 * @description ConferenceManageService
 * @date 2021/1/6 8:53
 */
public interface ConferenceInfoService {

    Result addConference(ConferenceRequest conferenceRequest);

    Result deleteConference(ConferenceRequest conferenceRequest);

    //更新标题等信息
    Result updateConferenceInfo(ConferenceRequest conferenceRequest);

    //更新规则
    Result updateConferenceRule(ConferenceRequest conferenceRequest);

    Result findConferenceOfStaff(ConferenceRequest conferenceRequest);

    Result findScheduleOfStaff(ConferenceRequest conferenceRequest);

    Result addParticipant(ConferenceRequest conferenceRequest);

    Result deleteParticipant(ConferenceRequest conferenceRequest);

    Result deleteOneParticipant(ConferenceRequest conferenceRequest);

    Result updateParticipant(ConferenceRequest conferenceRequest);

    Result findParticipantOfConference(ConferenceRequest conferenceRequest);

    Result getOccupancyOfConferenceRoom(ConferenceRequest conferenceRequest);
}
