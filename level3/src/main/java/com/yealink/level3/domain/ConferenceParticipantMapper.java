package com.yealink.level3.domain;

import com.yealink.level3.bean.ConferenceParticipant;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author zhangchen
 * @description ConferenceParticipantMapper
 * @date 2021/1/5 17:41
 */
@Mapper
@Component
public interface ConferenceParticipantMapper {
    @SelectKey(keyProperty = "id", resultType = String.class, before = true, statement = "select replace(uuid(), '-', '')")
    @Options(keyProperty = "id", useGeneratedKeys = true)
    @Insert("insert into conference_participant(id,conference_id,participant_id,status,role,create_time,modify_time) values(#{id},#{conferenceId},#{participant_id},#{status},#{role} ,#{createTime},#{modifyTime})")
    int add(ConferenceParticipant conferenceParticipant);

    @Delete("delete from conference_participant where id = #{id}")
    int delete(String id);

    @Update("update conference_participant set conference_id = #{conferenceId} ,participant_id = #{participantId} ,status = #{status} ,role = #{role} ,modify_time = #{modifyTime} where id = #{id} ")
    int update(ConferenceParticipant conferenceParticipant);

    @Select("select conference_id from conference_participant where participant_id = #{participantId} ")
    List<String> findConference(String participantId);

    @Select("select participant_id from conference_participant where conference_id = #{conferenceId} ")
    List<String> findParticipant(String conferenceId);

    @Select("select id,conference_id,participant_id,status,role from conference_participant where participant_id = #{participantId} and conference_id = #{conferenceId}")
    ConferenceParticipant find(ConferenceParticipant conferenceParticipant);


    @Delete("delete from conference_participant where conference_id = #{conferenceId} ")
    void deleteAllParticipantOfConference(String conferenceId);
}
