package com.yealink.level1.service;

import com.yealink.level1.bean.Department;

/**
 * @author zhangchen
 * @description DepManageService
 * @date 2020/12/24 15:19
 */
public interface DepManageService {

    int addDep(Department dep);

    int addStaffDepRelation(String staffId,String name,String position);
}
