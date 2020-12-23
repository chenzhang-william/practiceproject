package com.yealink.level1.domain;

import com.yealink.level1.bean.Account;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Mapper
@Component
public interface AccountMapper {
    @SelectKey(keyProperty = "id",resultType = String.class, before = true,statement = "select replace(uuid(), '-', '')")
    @Options(keyProperty = "id", useGeneratedKeys = true)
    @Insert("insert into account(id,username,password) values(#{id},#{username},#{password})")
    int add(Account account);

    @Select("select id, username as username, password as password, enterprise_id as enterpriseId, staff_id as staffId from account where id = #{id}")
    Account findAccountById(@Param("id") String id);

    @Select("select * from account where staff_id = #{staffId}")
    List<Account> findAccountByStaffId(String staffId);

    @Update("update account set username = #{username},password = #{password},enterprise_id = #{enterpriseId},staff_id = #{staffId},modify_time = #{modifyTime} where id = #{id}")
    int update(Account account);

    @Select("select id as id from account where username = #{username}")
    String findIdByUsername(@Param("username") String username);
}
