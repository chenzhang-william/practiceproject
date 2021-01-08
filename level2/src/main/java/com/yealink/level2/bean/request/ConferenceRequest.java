package com.yealink.level2.bean.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * @author zhangchen
 * @description ConferenceRequest
 * @date 2021/1/5 17:37
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConferenceRequest {


    public interface ConferenceInsert{}
    public interface ConferenceDelete{}
    public interface ConferenceUpdate{}

    public interface RuleInsert{}
    public interface RuleUpdate{}

    public interface ParticipantInsert{}
    public interface ParticipantDelete{}
    public interface ParticipantUpdate{}

    public interface Mobile{}
    public interface ConferenceNo{}

    private String staffId;

    private String title;

    @NotNull(message = "会议号不能为空",groups = {
            ConferenceInsert.class,ConferenceDelete.class,ConferenceUpdate.class,
            RuleUpdate.class,
            ParticipantInsert.class,ParticipantDelete.class,ParticipantUpdate.class,
            ConferenceNo.class})
    private String conferenceNo;

    private String newConferenceNo;

    @NotNull(message = "手机号不能为空",groups = {
            ConferenceInsert.class,ConferenceDelete.class,ConferenceUpdate.class,
            RuleUpdate.class,
            ParticipantInsert.class,ParticipantDelete.class,ParticipantUpdate.class,
            Mobile.class})
    private String mobile;

    @NotNull(message = "开始时间不能为空",groups = {ConferenceInsert.class})
    private String startTime;

    @NotNull(message = "结束时间不能为空",groups = {ConferenceInsert.class})
    private String endTime;

    @NotNull(message = "类型不能为空",groups = {RuleInsert.class})
    private int type;

    private int gap;

    private int day;

    private String week;

    private int ordinalWeek;

    private int ordinalMonth;

    @NotNull(message = "开始日期不能为空",groups = {RuleInsert.class})
    private String startDay;

    @NotNull(message = "结束日期不能为空",groups = {RuleInsert.class})
    private String endDay;


    private int status;

    private int role;
}
