package com.yealink.level1.service.impl;

import com.yealink.level1.bean.Role;
import com.yealink.level1.bean.StaffRoleRelation;
import com.yealink.level1.domain.RoleMapper;
import com.yealink.level1.domain.StaffRoleRelationMapper;
import com.yealink.level1.service.RoleManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author zhangchen
 * @description RoleManageServiceImpl
 * @date 2020/12/23 20:22
 */
@Service
@Transactional
public class RoleManageServiceImpl implements RoleManageService {
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private StaffRoleRelationMapper staffRoleRelationMapper;

    @Override
    public int addRole(Role role) {
        long now = new Date().getTime();
        role.setCreateTime(now);
        role.setModifyTime(now);
        return roleMapper.add(role);
    }

    @Override
    public int addStaffRoleRelation(String staffId, String name) {
        StaffRoleRelation staffRoleRelation = new StaffRoleRelation();
        staffRoleRelation.setStaffId(staffId);
        staffRoleRelation.setRoleId(roleMapper.findIdByName(name));
        long now = new Date().getTime();
        staffRoleRelation.setCreateTime(now);
        staffRoleRelation.setModifyTime(now);
        return staffRoleRelationMapper.add(staffRoleRelation);
    }
}
