package com.yealink.level3.domain;

import com.yealink.level3.bean.Staff;
import com.yealink.level3.bean.StaffDepartmentRelation;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author zhangchen
 * @description StaffDepRelationMapper
 * @date 2020/12/24 15:23
 */
@Mapper
@Component
public interface StaffDepRelationMapper {
    @SelectKey(keyProperty = "id", resultType = String.class, before = true, statement = "select replace(uuid(), '-', '')")
    @Options(keyProperty = "id", useGeneratedKeys = true)
    @Insert("insert into staff_department_relation(id, staff_id, department_id, position, create_time, modify_time) values (#{id}, #{staffId}, #{departmentId} , #{position}, #{createTime}, #{modifyTime})")
    int add(StaffDepartmentRelation staffDepartmentRelation);

    @Select("select id from staff_department_relation where staff_id=#{staffId} and department_id = #{departmentId}")
    String findId(String staffId, String departmentId);

    @Delete("delete from staff_department_relation where id = #{id}")
    int delete(String id);

    @Update("<script>" +
            "update staff_department_relation set " +
            "<if test='staffId !=null and staffId !=\"\"'>staff_id = #{staffId}, </if>" +
            "<if test='departmentId !=null and departmentId !=\"\"'>department_id = #{departmentId}, </if>" +
            "<if test='position !=null and position !=\"\"'>position = #{position} , </if>" +
            "modify_time = #{modifyTime} " +
            "where id = #{id}" +
            "</script>")
    int update(StaffDepartmentRelation staffDepartmentRelation);

    @Select("select a.name as depName, b.position from department as a, " +
            "(SELECT department_id,position from staff_department_relation where staff_id = #{staffId}) as b " +
            "where a.id = b.department_id")
    List<Map<String, String>> getPosition(String id);

    @Select("select id,department_id,staff_id,position from staff_department_relation where id =#{id} ")
    StaffDepartmentRelation findRelationById(String id);

    @Select("select id,name,mobile from staff where id in (select staff_id from staff_department_relation where department_id = #{departmentId}) order by name")
    List<Staff> getStaffOfDep(String departmentId);

    @Select("select id,staff_id,department_id,position from staff_department_relation where staff_id = #{staffId} and department_id = #{departmentId}")
    StaffDepartmentRelation findRelation(String staffId, String departmentId);
}
