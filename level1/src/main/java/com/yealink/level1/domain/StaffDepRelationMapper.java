package com.yealink.level1.domain;

import com.yealink.level1.bean.StaffDepartmentRelation;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.SelectKey;
import org.springframework.stereotype.Component;

/**
 * @author zhangchen
 * @description StaffDepRelationMapper
 * @date 2020/12/24 15:23
 */
@Mapper
@Component
public interface StaffDepRelationMapper {
    @SelectKey(keyProperty = "id",resultType = String.class, before = true,statement = "select replace(uuid(), '-', '')")
    @Options(keyProperty = "id", useGeneratedKeys = true)
    @Insert("insert into staff_department_relation(id, staff_id, department_id, position, create_time, modify_time) values (#{id}, #{staffId}, #{departmentId} , #{position}, #{createTime}, #{modifyTime})")
    int add(StaffDepartmentRelation staffDepartmentRelation);
}
