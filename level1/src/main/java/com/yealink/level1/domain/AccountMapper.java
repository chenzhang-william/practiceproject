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
    @Insert("insert into account(id,username,password) values(#{id},#{userName},#{passWord})")
    int add(Account account);

    @Select("select id, username as username, password as password from account where id = #{id}")
    Account findAccountById(@Param("id") String id);

    @Select("select * from account where staff_id = #{staffId}")
    List<Account> findAccountByStaffId(String staffId);

}
