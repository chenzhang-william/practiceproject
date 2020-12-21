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
    @Insert("insert into staff(id,name, gender, mobile, email) values(#{id}, #{name}, #{gender}, #{mobile}, #{email})")
    int add(Staff staff);


    @Update("update staff set name = #{name}, gender = #{gender}, mobile = #{mobile}, email = #{email}")
    int update(@Param("name") String name, @Param("gender") int gender, @Param("mobile") String mobile, @Param("email") String email);

    @Delete("delete from staff where id = #{id}")
    int delete(String id);

    @Select("select id, name as name, gender as gender, mobile as mobile, email as email from staff where id = #{id}")
    Staff findStaffById(@Param("id") String id);

    @Select("select id, name as name, gender as gender, mobile as mobile, email as email from staff")
    List<Staff> findStaffList();



}
