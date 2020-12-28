package com.yealink.level1.service.impl;

import com.yealink.level1.bean.Role;
import com.yealink.level1.bean.StaffRoleRelation;
import com.yealink.level1.domain.RoleMapper;
import com.yealink.level1.domain.StaffMapper;
import com.yealink.level1.domain.StaffRoleRelationMapper;
import com.yealink.level1.service.RoleManageService;
import com.yealink.level1.service.StaffInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    @Autowired
    private StaffInfoService staffInfoService;

    @Override
    public int addRole(Role role) {
        long now = new Date().getTime();
        role.setCreateTime(now);
        role.setModifyTime(now);
        return roleMapper.add(role);
    }

    @Override
    public int addStaffRoleRelation(String mobile, String name) {
        StaffRoleRelation staffRoleRelation = new StaffRoleRelation();
        staffRoleRelation.setStaffId(staffInfoService.findIdByMobile(mobile));
        staffRoleRelation.setRoleId(findRoleIdByName(name));
        long now = new Date().getTime();
        staffRoleRelation.setCreateTime(now);
        staffRoleRelation.setModifyTime(now);
        return staffRoleRelationMapper.add(staffRoleRelation);
    }

    @Override
    public int deleteRole(String name) {
        return roleMapper.delete(findRoleIdByName(name));
    }

    @Override
    public int deleteRelation(String mobile,String name) {
        return staffRoleRelationMapper.delete(findRelationId(mobile,name));
    }

    @Override
    public int updateRole(Role role,String name) {
        role.setId(findRoleIdByName(name));
        role.setModifyTime(new Date().getTime());
        return roleMapper.update(role);
    }

    @Override
    public int updateRoleOfStaff(String mobile, String oldName, String newName) {
        StaffRoleRelation staffRoleRelation = findRelationById(findRelationId(mobile,oldName));
        staffRoleRelation.setRoleId(findRoleIdByName(newName));
        staffRoleRelation.setModifyTime(new Date().getTime());
        return staffRoleRelationMapper.update(staffRoleRelation);
    }

    @Override
    public int updateStaffOfRole(String oldMobile, String name, String newMobile) {
        StaffRoleRelation staffRoleRelation = findRelationById(findRelationId(oldMobile,name));
        staffRoleRelation.setStaffId(staffInfoService.findIdByMobile(newMobile));
        staffRoleRelation.setModifyTime(new Date().getTime());
        return staffRoleRelationMapper.update(staffRoleRelation);
    }

    @Override
    public String findRoleIdByName(String name) {
        return roleMapper.findIdByName(name);
    }



    @Override
    public String findRelationId(String mobile, String name) {
        return staffRoleRelationMapper.findId(staffInfoService.findIdByMobile(mobile),roleMapper.findIdByName(name));
    }


    @Override
    public StaffRoleRelation findRelationById(String id) {
        return staffRoleRelationMapper.find(id);
    }

    @Override
    public List<String> findRelationByMobile(String mobile) {
        List<StaffRoleRelation> relations=staffRoleRelationMapper.findByStaffId(staffInfoService.findIdByMobile(mobile));
        List<String> roles= new ArrayList();
        for(StaffRoleRelation relation:relations){
            roles.add(roleMapper.findNameById(relation.getRoleId()));
        }
        return roles;
    }

    @Override
    public List<String> listRoleOfEnterprise(String name) {
        return roleMapper.listRoleOfEnterprise(name);
    }


}
