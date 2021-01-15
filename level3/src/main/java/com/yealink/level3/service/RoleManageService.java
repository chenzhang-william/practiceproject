package com.yealink.level3.service;

import com.yealink.level3.bean.Enterprise;
import com.yealink.level3.bean.Role;
import com.yealink.level3.bean.Staff;
import com.yealink.level3.bean.StaffRoleRelation;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author zhangchen
 * @description RoleManageService
 * @date 2020/12/23 20:21
 */
public interface RoleManageService {

    void addStaffRoleRelation(@Valid StaffRoleRelation staffRoleRelation);

    void deleteStaffRoleRelation(@Valid StaffRoleRelation staffRoleRelation);

    void updateStaffRoleRelation(@Valid StaffRoleRelation oldRelation, @Valid StaffRoleRelation newRelation);

    StaffRoleRelation findRelation(@Valid StaffRoleRelation staffRoleRelation);

    Role findRoleByName(@Valid Role role);

    List<Role> findRoleOfStaff(@Valid Staff staff);

    @NotNull(message = "该企业无角色关系") List<Staff> findStaffOfRoleInEnterprise(@Valid Role role, @Valid Enterprise enterprise);

    List<Staff> findStaffOfRole(@Valid Role role);

}
