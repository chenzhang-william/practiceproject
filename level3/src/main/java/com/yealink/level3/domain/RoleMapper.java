package com.yealink.level3.domain;

import com.yealink.level3.bean.Role;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author zhangchen
 * @description RoleMapper
 * @date 2020/12/23 19:00
 */
@Mapper
@Component
public interface RoleMapper {
    @SelectKey(keyProperty = "id", resultType = String.class, before = true, statement = "select replace(uuid(), '-', '')")
    @Options(keyProperty = "id", useGeneratedKeys = true)
    @Insert("insert into role(id, name, definition, create_time, modify_time) values (#{id}, #{name}, #{definition}, #{createTime}, #{modifyTime})")
    int add(Role role);

    @Select("select id from role where name=#{name}")
    String findIdByName(String name);

    @Delete("delete from role where id = #{id}")
    int delete(String id);

    @Update("<script> " +
            "update role set " +
            "<if test ='name !=null and name !=\"\"'> name = #{name}, </if>" +
            "<if test ='definition !=null and definition !=\"\"'> definition = #{definition}, </if>" +
            " modify_time = #{modifyTime} " +
            "where id = #{id}" +
            "</script>")
    int update(Role role);

    @Select("SELECT name from role WHERE id in(SELECT DISTINCT role_id FROM staff_role_relation WHERE staff_id in(SELECT id from staff where enterprise_id=(SELECT id from enterprise where name = #{name})))")
    List<String> listRoleOfEnterprise(String name);

    @Select("select name from role where id = #{id}")
    String findNameById(String id);

    @Select("select id,name,definition from role where id in (select role_id from staff_role_relation where staff_id = #{staffId})")
    List<Role> findRoleOfStaff(String staffId);

    @Select("select id,name,definition from role where name=#{name}")
    Role findRoleByName(String name);
}
