package com.yealink.level1.domain;

import com.yealink.level1.bean.Department;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

/**
 * @author zhangchen
 * @description Department
 * @date 2020/12/24 15:13
 */
@Mapper
@Component
public interface DepartmentMapper {
    @SelectKey(keyProperty = "id",resultType = String.class, before = true,statement = "select replace(uuid(), '-', '')")
    @Options(keyProperty = "id", useGeneratedKeys = true)
    @Insert("insert into department(id,enterprise_id,name,create_time,modify_time) values (#{id}, #{enterpriseId}, #{name},#{createTime}, #{modifyTime})")
    int add(Department department);

    @Select("select id from department where name = #{name}")
    String findIdByName(String name);
}
