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
    @Insert("insert into staff(id, name, gender, mobile, email, create_time, modify_time) values (#{id}, #{name}, #{gender}, #{mobile}, #{email}, #{createTime}, #{modifyTime})")
    int add(Staff staff);

    @Update("<script>" +
            "update staff set" +
            "<if test='name != null and name !=\"\"'> name = #{name}, </if>" +
            "<if test='enterpriseId != null and enterpriseId != \"\"'> enterprise_Id = #{enterpriseId}, </if>" +
            "<if test='gender != null and gender != \"\"'> gender = #{gender}, </if>" +
            "<if test='mobile != null and mobile != \"\"'> mobile = #{mobile}, </if>" +
            "<if test='email != null and mobile != \"\"'> email = #{email}, </if>" +
            " modify_time = #{modifyTime} " +
            " where id = #{id}" +
            "</script>")
    int update(Staff staff);

    @Delete("delete from staff where id = #{id}")
    int delete(String id);

    @Select("select id, name as name, gender as gender, mobile as mobile, email as email from staff where id = #{id}")
    Staff findStaffById(@Param("id") String id);

    @Select("select id from staff where mobile = #{mobile}")
    String findIdByMobile(@Param("mobile") String mobile);

    @Select("select enterprise_id from staff where id = #{id}")
    String findEnterpriseIdById(@Param("id")String id);

    @Select("select id, name as name, gender as gender, mobile as mobile, email as email from staff")
    List<Staff> findStaffList();


    //无法在staff中添加enterprise属性，该查询暂时无法实现
    @Select("select * from staff where id = #{id}")
    @Results({
            @Result(property = "enterprise",column = "enterprise_id",
            one = @One(select = "com.yealink.level1.domain.EnterpriseMapper.findEnterpriseById"))
    })
    Staff findStaffWithEnterprise(String id);

    @Select("select * from staff where id = #{id}")
    @Results({
            @Result(property = "accounts",column = "id",
            many = @Many(select = "com.yealink.level1.domain.AccountMapper.findAccountByStaffId"))
    })
    Staff getStaffWithAccount(String id);


}
