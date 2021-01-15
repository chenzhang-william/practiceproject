package com.yealink.practiceproject.domain;

import com.yealink.practiceproject.bean.Enterprise;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface EnterpriseMapper {

    @SelectKey(keyProperty = "id", resultType = String.class, before = true, statement = "select replace(uuid(), '-', '')")
    @Options(keyProperty = "id", useGeneratedKeys = true)
    @Insert("insert into enterprise(id, no, name, create_time, modify_time) values(#{id}, #{no}, #{name}, #{createTime}, #{modifyTime})")
    int add(Enterprise enterprise);

    @Update("<script>" +
            "update enterprise set" +
            "<if test='no != null and no !=\"\"'> no = #{no}, </if>" +
            "<if test='name != null and name !=\"\"'> name = #{name}, </if>" +
            "modify_time = #{modifyTime} " +
            "where id = #{id}" +
            "</script>")
    int update(Enterprise enterprise);

    @Delete("delete from enterprise where id = #{id} ")
    int delete(String id);

    @Select("select id,no,name from enterprise where no = #{no}")
    Enterprise findByNo(String no);

    @Select("select id,no,name from enterprise where name = #{name}")
    List<Enterprise> findByName(String name);

    @Select("select id from enterprise where no =#{no}")
    String findIdByNo(String no);
}
