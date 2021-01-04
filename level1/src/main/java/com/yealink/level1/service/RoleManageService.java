package com.yealink.level1.service;

import com.yealink.level1.bean.Enterprise;
import com.yealink.level1.bean.Role;
import com.yealink.level1.bean.Staff;
import com.yealink.level1.bean.StaffRoleRelation;

import javax.validation.Valid;
import java.util.List;

/**
 * @author zhangchen
 * @description RoleManageService
 * @date 2020/12/23 20:21
 */
public interface RoleManageService {

    void addStaffRoleRelation(@Valid StaffRoleRelation staffRoleRelation);

    void deleteStaffRoleRelation(@Valid StaffRoleRelation staffRoleRelation);

    void updateStaffRoleRelation(@Valid StaffRoleRelation oldRelation,@Valid StaffRoleRelation newRelation);

    StaffRoleRelation findRelation(@Valid StaffRoleRelation staffRoleRelation);

    Role findRoleByName(@Valid Role role);

    List<Role> findRoleOfStaff(@Valid Staff staff);

    List<Staff> findStaffOfRoleInEnterprise(@Valid Role role,@Valid Enterprise enterprise);

    List<Staff> findStaffOfRole(@Valid Role role);
}
