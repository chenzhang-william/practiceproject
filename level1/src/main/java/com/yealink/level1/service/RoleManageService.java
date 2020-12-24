package com.yealink.level1.service;

import com.yealink.level1.bean.Role;

/**
 * @author zhangchen
 * @description RoleManageService
 * @date 2020/12/23 20:21
 */
public interface RoleManageService {

    int addRole(Role role);

    int addStaffRoleRelation(String staffId,String name);
}
