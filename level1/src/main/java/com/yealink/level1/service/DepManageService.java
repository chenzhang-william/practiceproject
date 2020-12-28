package com.yealink.level1.service;

import com.yealink.level1.bean.Department;
import com.yealink.level1.bean.StaffDepartmentRelation;
import java.util.List;
import java.util.Map;

/**
 * @author zhangchen
 * @description DepManageService
 * @date 2020/12/24 15:19
 */
public interface DepManageService {

    int addDep(Department dep);

    int addStaffDepRelation(String mobile,String name,String position);

    int deleteDep(String name);

    int deleteStaffDepRelation(String mobile,String name);

    int updateDep(String name,Department dep);

    int updateStaffDepRelation(String mobile,String oldDep,Map<String,String> newRelation);

    List<Map<String,String>> getPosition(String mobile);

    String findEnterpriseById(String id);

    List<Department> findDepByEnterprise(String name);

}
