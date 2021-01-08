package com.yealink.level2.controller;

import com.yealink.level2.bean.request.ConferenceRequest;
import com.yealink.level2.bean.result.Result;
import com.yealink.level2.service.ConferenceInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhangchen
 * @description ConferenceInfoController
 * @date 2021/1/6 16:56
 */
@RestController
@RequestMapping("/conference")
@Validated
public class ConferenceInfoController {
    @Autowired
    private ConferenceInfoService conferenceInfoService;

    @PostMapping("/addConference")
    public Result addConference(@RequestBody@Validated({ConferenceRequest.ConferenceInsert.class,ConferenceRequest.RuleInsert.class})ConferenceRequest conferenceRequest){
        return conferenceInfoService.addConference(conferenceRequest);
    }

    @PostMapping("/deleteConference")
    public Result deleteConference(@RequestBody@Validated({ConferenceRequest.ConferenceDelete.class})ConferenceRequest conferenceRequest){
        return conferenceInfoService.deleteConference(conferenceRequest);
    }

    @PostMapping("/updateConferenceInfo")
    public Result updateConferenceInfo(@RequestBody@Validated({ConferenceRequest.ConferenceUpdate.class})ConferenceRequest conferenceRequest){
        return conferenceInfoService.updateConferenceInfo(conferenceRequest);
    }

    @PostMapping("/updateConferenceRule")
    public Result updateConferenceRule(@RequestBody@Validated({ConferenceRequest.RuleUpdate.class})ConferenceRequest conferenceRequest){
        return conferenceInfoService.updateConferenceRule(conferenceRequest);
    }

    @PostMapping("/findConferenceOfStaff")
    public Result findConferenceOfStaff(@RequestBody@Validated({ConferenceRequest.Mobile.class})ConferenceRequest conferenceRequest){
        return conferenceInfoService.findConferenceOfStaff(conferenceRequest);
    }

    @PostMapping("/findScheduleOfStaff")
    public Result findScheduleOfStaff(@RequestBody@Validated({ConferenceRequest.Mobile.class})ConferenceRequest conferenceRequest){
        return conferenceInfoService.findScheduleOfStaff(conferenceRequest);
    }

    @PostMapping("/addParticipant")
    public Result addParticipant(@RequestBody@Validated({ConferenceRequest.ParticipantInsert.class})ConferenceRequest conferenceRequest){
        return conferenceInfoService.addParticipant(conferenceRequest);
    }

    @PostMapping("/deleteParticipant")
    public Result deleteParticipant(@RequestBody@Validated({ConferenceRequest.ConferenceNo.class})ConferenceRequest conferenceRequest){
        return conferenceInfoService.deleteParticipant(conferenceRequest);
    }

    @PostMapping("/deleteOneParticipant")
    public Result deleteOneParticipant(@RequestBody@Validated({ConferenceRequest.ParticipantDelete.class})ConferenceRequest conferenceRequest){
        return conferenceInfoService.deleteOneParticipant(conferenceRequest);
    }

    @PostMapping("/updateParticipant")
    public Result updateParticipant(@RequestBody@Validated({ConferenceRequest.ParticipantUpdate.class})ConferenceRequest conferenceRequest){
        return conferenceInfoService.updateParticipant(conferenceRequest);
    }

    @PostMapping("/findParticipantOfConference")
    public Result findParticipantOfConference(@RequestBody@Validated({ConferenceRequest.ConferenceNo.class})ConferenceRequest conferenceRequest){
        return conferenceInfoService.findParticipantOfConference(conferenceRequest);
    }
}