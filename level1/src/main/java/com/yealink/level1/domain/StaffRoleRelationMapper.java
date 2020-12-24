package com.yealink.level1.domain;

import com.yealink.level1.bean.Staff;
import com.yealink.level1.bean.StaffRoleRelation;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

/**
 * @author zhangchen
 * @description StaffRoleRelationMapper
 * @date 2020/12/24 14:17
 */
@Mapper
@Component
public interface StaffRoleRelationMapper {
    @SelectKey(keyProperty = "id",resultType = String.class, before = true,statement = "select replace(uuid(), '-', '')")
    @Options(keyProperty = "id", useGeneratedKeys = true)
    @Insert("insert into staff_role_relation(id, staff_id, role_id, create_time, modify_time) values (#{id}, #{staffId}, #{roleId}, #{createTime}, #{modifyTime})")
    int add(StaffRoleRelation staffRoleRelation);


}
