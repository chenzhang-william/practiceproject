package com.yealink.level1.domain;

import com.yealink.level1.bean.Enterprise;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface EnterpriseMapper {

    @Select("select * from enterprise where id = #{id}")
    Enterprise findEnterpriseById(String id);


}
