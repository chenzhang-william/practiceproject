package com.yealink.level1.domain;

import com.yealink.level1.bean.Enterprise;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface EnterpriseMapper {

    @Select("select * from enterprise where id = #{id}")
    Enterprise findEnterpriseById(String id);

    @SelectKey(keyProperty = "id",resultType = String.class, before = true,statement = "select replace(uuid(), '-', '')")
    @Options(keyProperty = "id", useGeneratedKeys = true)
    @Insert("insert into enterprise(id, no, name, create_time, modify_time) values(#{id}, #{no}, #{name}, #{createTime}, #{modifyTime})")
    int add(Enterprise enterprise);

    @Select("select id from enterprise where name = #{name}")
    String findIdByName(String name);
}
