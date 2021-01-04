package com.yealink.level1.service;

import com.yealink.level1.bean.Department;
import com.yealink.level1.bean.Enterprise;
import com.yealink.level1.bean.Staff;
import com.yealink.level1.bean.StaffDepartmentRelation;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * @author zhangchen
 * @description DepManageService
 * @date 2020/12/24 15:19
 */
public interface DepManageService {

    void addDep(@Valid Department dep);

    void addStaffDepRelation(@Valid StaffDepartmentRelation staffDepartmentRelation);

    void deleteDep(@Valid Department dep);

    void deleteStaffDepRelation(@Valid StaffDepartmentRelation staffDepartmentRelation);

    void updateDep(@Valid Department oldDep,@Valid Department newDep);

    void updateStaffDepRelation(@Valid StaffDepartmentRelation oldRelation, @Valid StaffDepartmentRelation newRelation);

    StaffDepartmentRelation findRelation(StaffDepartmentRelation relation);

    Department findDep(Department dep);

    List<Map<String,String>> getPosition(@Valid Staff staff);

    List<Department> findDepOfEnterprise(@Valid Enterprise enterprise);

    List getChildDep(String parentId);

    List getChildStaff(String id);

    List getTree(String id);


}
