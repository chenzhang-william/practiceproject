package com.yealink.level1.domain;

import com.yealink.level1.bean.Department;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

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
    @Insert("insert into department(id,enterprise_id,name,parent_id,create_time,modify_time) values (#{id}, #{enterpriseId}, #{name},#{parentId},#{createTime}, #{modifyTime})")
    int add(Department department);

    @Select("select id from department where name = #{name} and enterprise_id = #{enterpriseId}")
    String findId(String name,String enterpriseId);

    @Delete("delete from department where id = #{id}")
    int delete(String id);

    @Update("<script>" +
            "update department set" +
            "<if test ='name !=null and name !=\"\"'> name = #{name}, </if>" +
            "<if test ='enterpriseId !=null and enterpriseId !=\"\"'>enterprise_id = #{enterpriseId}, </if>" +
            "<if test ='parentId !=null and parentId !=\"\"'>parent_id = #{parentId}, </if>" +
            " modify_time = #{modifyTime} " +
            "where id = #{id}" +
            "</script>")
    int update(Department department);

    @Select("select enterprise_id from department where id =#{id}")
    String findEnterpriseById(String id);

    @Select("select name from department where id = #{id}")
    List<String> findNameById(String id);

    @Select("select id from department where parent_id= #{parentId}")
    String findIdByParentId(String parentId);

    @Select("select id,enterprise_id,name,parent_id from department where id = #{id}")
    Department findById(String id);

    @Select("select id,name,enterprise_id,parent_id from department where parent_id = #{parentId} order by name")
    List<Department> findByParentId(String parentId);

    @Select("select id,name,enterprise_id,parent_id from department where name = #{name} and enterprise_id = #{enterpriseId}")
    Department findDep(String name, String enterpriseId);
}
