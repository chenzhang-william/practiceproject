package com.yealink.practiceproject.dao;

import com.yealink.practiceproject.bean.ConferenceRule;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author zhangchen
 * @description ConferenceRuleMapper
 * @date 2021/1/5 17:41
 */
@Mapper
@Component
public interface ConferenceRuleMapper {
    @SelectKey(keyProperty = "id", resultType = String.class, before = true, statement = "select replace(uuid(), '-', '')")
    @Options(keyProperty = "id", useGeneratedKeys = true)
    @Insert("insert into conference_rule(id,type,gap,day,week,ordinal_week,ordinal_month,start_day,end_day,create_time,modify_time) " +
            "values(#{id},#{type} ,#{gap} ,#{day} ,#{week} ,#{ordinalWeek} ,#{ordinalMonth} ,#{startDay} ,#{endDay} ,#{createTime},#{modifyTime})")
    int add(ConferenceRule conferenceRule);

    @Delete("delete from conference_rule where id = #{id} ")
    int delete(String id);

    @Update("update conference_rule set " +
            "type = #{type} ,gap = #{gap} ,day = #{day} ,week = #{week} ,ordinal_week = #{ordinalWeek},rodinal_month = #{ordinalMonth} ,start_day = #{startDay} ,end_day = #{endDay} " +
            "where id = #{id} ")
    int update(ConferenceRule conferenceRule);

    @Select("select id,type,gap,day,week,ordinal_week,ordinal_month,start_day,end_day from conference_rule where id = #{id} ")
    ConferenceRule find(String id);

    @Select("select id,type,gap,day,week,ordinal_week,ordinal_month,start_day,end_day from conference_rule where id in #{ruleIdList} ")
    List<ConferenceRule> findByIdList(List<String> ruleIdList);
}
