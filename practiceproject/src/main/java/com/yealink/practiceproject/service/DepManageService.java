package com.yealink.practiceproject.service;

import com.yealink.practiceproject.bean.Department;
import com.yealink.practiceproject.bean.Enterprise;
import com.yealink.practiceproject.bean.Staff;
import com.yealink.practiceproject.bean.StaffDepartmentRelation;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
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

    void updateDep(@Valid Department oldDep, @Valid Department newDep);

    void updateStaffDepRelation(@Valid StaffDepartmentRelation oldRelation, @Valid StaffDepartmentRelation newRelation);

    @NotNull(message = "员工不在该部门") StaffDepartmentRelation findRelation(StaffDepartmentRelation relation);

    @NotNull(message = "部门不存在") Department findDep(Department dep);

    List<Map<String, String>> getPosition(@Valid Staff staff);

    List<Department> findDepOfEnterprise(@Valid Enterprise enterprise);

    List getChildDep(String parentId);

    List getChildStaff(String id);

    List getDepTree(String id);

    boolean isDepExist(Department dep);

    boolean isRelationExist(StaffDepartmentRelation staffDepartmentRelation);

}
