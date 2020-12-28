package com.yealink.level1.service;

import com.yealink.level1.bean.Enterprise;

public interface EnterpriseInfoService {

    Enterprise findEnterpriseById(String id);

    int add(Enterprise enterprise);

    int delete(String name);

    int update(Enterprise enterprise,String name);

    String findIdByName(String name);

    Enterprise findByNo(String no);

    Enterprise findByName(String name);
}
