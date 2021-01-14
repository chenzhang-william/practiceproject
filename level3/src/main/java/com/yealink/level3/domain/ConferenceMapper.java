package com.yealink.level3.domain;

import com.yealink.level3.bean.Conference;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author zhangchen
 * @description ConferenceMapper
 * @date 2021/1/5 17:40
 */
@Mapper
@Component
public interface ConferenceMapper {
    @SelectKey(keyProperty = "id", resultType = String.class, before = true, statement = "select replace(uuid(), '-', '')")
    @Options(keyProperty = "id", useGeneratedKeys = true)
    @Insert("insert into conference(id,title,conference_no,rule_id,start_time,end_time,create_time,modify_time) values(#{id},#{title},#{conferenceNo},#{ruleId},#{startTime},#{endTime},#{createTime},#{modifyTime})")
    int add(Conference conference);

    @Delete("delete from conference where id = #{id}")
    int delete(String id);

    @Update("update conference set " +
            "title = #{title},conference_NO = #{conferenceNo},rule_id = #{ruleId},start_time = #{startTime},end_time = #{endTime},modify_time = #{modifyTime} " +
            "where id = #{id}")
    int update(Conference Conference);

    @Select("select id from conference where conference_no = #{conferenceNo} ")
    String findIdByNo(String conferenceNo);

    @Select("select id,title,conference_no,rule_id,start_time,end_time from conference where conference_no = #{conferenceNo} ")
    Conference findByNo(String conferenceNo);

    @Select("select id,title,conference_no,rule_id,start_time,end_time from conference where id = #{id} ")
    Conference findById(String id);

    @Select("select rule_id from conference where conference_no = #{conferenceNo} ")
    String findRuleIdByNo(String conferenceNo);

    @Select("select title,conference_no,rule_id,start_time,end_time from conference where id in #{conferenceIdList} ")
    List<Conference> findByIdList(List<String> conferenceIdList);

    List<String> findIdByConferenceRoom(String conferenceRoom);
}
