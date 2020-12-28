package com.yealink.level1.domain;

import com.yealink.level1.bean.StaffRoleRelation;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author zhangchen
 * @description StaffRoleRelationMapper
 * @date 2020/12/24 14:17
 */
@Mapper
@Component
public interface StaffRoleRelationMapper {
    @SelectKey(keyProperty = "id",resultType = String.class, before = true,statement = "select replace(uuid(), '-', '')")
    @Options(keyProperty = "id", useGeneratedKeys = true)
    @Insert("insert into staff_role_relation(id, staff_id, role_id, create_time, modify_time) values (#{id}, #{staffId}, #{roleId}, #{createTime}, #{modifyTime})")
    int add(StaffRoleRelation staffRoleRelation);

    @Delete("delete from staff_role_relation where id = #{id} ")
    int delete(String id);

    @Select("select id from staff_role_relation where staff_id = #{staffId} and role_id = #{roleId}")
    String findId(String staffId,String roleId);

    @Update("<script>" +
            "update staff_role_relation set " +
            "<if test='staff_id != null and staff_id !=\"\"'> staff_id = #{staffId}, </if>" +
            "<if test='staff_id != null and role_id !=\"\"'> role_id = #{roleId}, </if>" +
            "modify_time = #{modifyTime} " +
            "where id = #{id} " +
            "</script>")
    int update (StaffRoleRelation staffRoleRelation);

    @Select("select id,staff_id,role_id from staff_role_relation where id = #{id} ")
    StaffRoleRelation find(String id);

    @Select("select id,staff_id,role_id from staff_role_relation where staff_id = #{staffId}")
    List<StaffRoleRelation> findByStaffId(String staffId);


}

