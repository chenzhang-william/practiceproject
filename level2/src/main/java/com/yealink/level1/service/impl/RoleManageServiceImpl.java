package com.yealink.level1.service.impl;

import com.yealink.level1.bean.Enterprise;
import com.yealink.level1.bean.Role;
import com.yealink.level1.bean.Staff;
import com.yealink.level1.bean.StaffRoleRelation;
import com.yealink.level1.domain.RoleMapper;
import com.yealink.level1.domain.StaffRoleRelationMapper;
import com.yealink.level1.service.RoleManageService;
import com.yealink.level1.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * @author zhangchen
 * @description RoleManageServiceImpl
 * @date 2020/12/23 20:22
 */
@Service
@Transactional
@Validated
public class RoleManageServiceImpl implements RoleManageService {
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private StaffRoleRelationMapper staffRoleRelationMapper;
    @Autowired
    private StaffService staffService;


    @Override
    public void addStaffRoleRelation(@Valid StaffRoleRelation staffRoleRelation) {
        long now = new Date().getTime();
        staffRoleRelation.setCreateTime(now);
        staffRoleRelation.setModifyTime(now);
        staffRoleRelationMapper.add(staffRoleRelation);
    }

    @Override
    public void deleteStaffRoleRelation(@Valid StaffRoleRelation staffRoleRelation) {
        staffRoleRelationMapper.delete(findRelation(staffRoleRelation).getId());
    }

    @Override
    public void updateStaffRoleRelation(@Valid StaffRoleRelation oldRelation, @Valid StaffRoleRelation newRelation) {
        newRelation.setId(findRelation(oldRelation).getId());
        newRelation.setModifyTime(new Date().getTime());
        staffRoleRelationMapper.update(newRelation);
    }

    @Override
    public StaffRoleRelation findRelation(@Valid StaffRoleRelation staffRoleRelation) {
        return staffRoleRelationMapper.findRelation(staffRoleRelation.getRoleId(),staffRoleRelation.getStaffId());
    }

    @Override
    public Role findRoleByName(@Valid Role role) {
        return roleMapper.findRoleByName(role.getName());
    }

    @Override
    public List<Role> findRoleOfStaff(@Valid Staff staff) {
        return roleMapper.findRoleOfStaff(staffService.findStaffByMobile(staff).getId());
    }

    //查找一个企业中某个角色的员工列表
    @Override
    public @NotNull(message = "该企业无角色关系") List<Staff> findStaffOfRoleInEnterprise(@Valid Role role, @Valid Enterprise enterprise) {
        List<Staff> staffsInEnterprise = staffService.findStaffByEnterpriseNo(enterprise);
        List<Staff> staffsOfRole = findStaffOfRole(role);
        staffsInEnterprise.retainAll(staffsOfRole);
        return staffsInEnterprise;
    }

    public List<Staff> findStaffOfRole(@Valid Role role) {
        return staffService.findStaffOfRole(findRoleByName(role).getId());
    }
}
