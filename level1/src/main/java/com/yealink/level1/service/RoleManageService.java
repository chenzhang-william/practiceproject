package com.yealink.level1.service;

import com.yealink.level1.bean.Role;
import com.yealink.level1.bean.StaffRoleRelation;

import java.util.List;

/**
 * @author zhangchen
 * @description RoleManageService
 * @date 2020/12/23 20:21
 */
public interface RoleManageService {

    int addRole(Role role);

    int addStaffRoleRelation(String mobile,String name);

    int deleteRole(String name);

    int deleteRelation(String mobile,String name);

    int updateRole(Role role,String name);

    int updateRoleOfStaff(String mobile, String oldName, String newName);

    int updateStaffOfRole(String oldMobile, String name,String newMobile);

    String findRoleIdByName(String name);

    String findRelationId(String staffId,String roleId);

    StaffRoleRelation findRelationById(String id);

    List<String> findRelationByMobile(String mobile);

    List<String> listRoleOfEnterprise(String name);
}
