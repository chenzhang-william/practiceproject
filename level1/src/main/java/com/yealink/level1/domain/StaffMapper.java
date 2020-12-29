package com.yealink.level1.domain;

import com.yealink.level1.bean.Staff;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;
import java.util.List;

@Mapper
@Component
public interface StaffMapper {
    @SelectKey(keyProperty = "id",resultType = String.class, before = true,statement = "select replace(uuid(), '-', '')")
    @Options(keyProperty = "id", useGeneratedKeys = true)
    @Insert("insert into staff(id, name, gender, enterprise_id, mobile, email, create_time, modify_time) values (#{id}, #{name}, #{gender}, #{enterpriseId},  #{mobile}, #{email}, #{createTime}, #{modifyTime})")
    int add(Staff staff);

    @Update("<script>" +
            "update staff set" +
            "<if test='name != null and name !=\"\"'> name = #{name}, </if>" +
            "<if test='enterpriseId != null and enterpriseId != \"\"'> enterprise_Id = #{enterpriseId}, </if>" +
            "<if test='gender != null and gender != 0'> gender = #{gender}, </if>" +
            "<if test='mobile != null and mobile != \"\"'> mobile = #{mobile}, </if>" +
            "<if test='email != null and mobile != \"\"'> email = #{email}, </if>" +
            " modify_time = #{modifyTime} " +
            " where id = #{id}" +
            "</script>")
    int update(Staff staff);

    @Delete("delete from staff where id = #{id}")
    int delete(String id);

    @Select("select id, enterprise_id, name, gender, mobile, email from staff where id = #{id}")
    Staff findStaffById(String id);

    @Select("select id, enterprise_id, name, gender, mobile, email from staff where mobile = #{mobile}")
    String findIdByMobile(String mobile);

    @Select("select id, enterprise_id, name, gender, mobile, email from staff where name = #{name}")
    List<Staff> findStaffByName(String name);

    @Select("select id, enterprise_id, name, gender, mobile, email from staff where enterprise_id = (select id from enterprise where name = #{name})")
    List<Staff> findStaffByEnterpriseName(String name);

    @Select("select enterprise_id from staff where id = #{id}")
    String findEnterpriseById(String id);




}
