package com.yealink.level1.service;

import com.yealink.level1.bean.Enterprise;

public interface EnterpriseInfoService {

    Enterprise findEnterpriseById(String id);

    int add(Enterprise enterprise);

    int delete(String id);

    int update(Enterprise enterprise);

    String findIdByName(String name);


}
