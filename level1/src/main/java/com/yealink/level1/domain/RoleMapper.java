package com.yealink.level1.domain;

import com.yealink.level1.bean.Role;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

/**
 * @author zhangchen
 * @description RoleMapper
 * @date 2020/12/23 19:00
 */
@Mapper
@Component
public interface RoleMapper {
    @SelectKey(keyProperty = "id",resultType = String.class, before = true,statement = "select replace(uuid(), '-', '')")
    @Options(keyProperty = "id", useGeneratedKeys = true)
    @Insert("insert into role(id, name, definition, create_time, modify_time) values (#{id}, #{name}, #{definition}, #{createTime}, #{modifyTime})")
    int add(Role role);

    @Select("select id from role where name=#{name}")
    String findIdByName(String name);
}
