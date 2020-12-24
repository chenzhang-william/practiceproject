package com.yealink.level1.controller;

import com.yealink.level1.bean.Role;
import com.yealink.level1.service.RoleManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhangchen
 * @description RoleManageController
 * @date 2020/12/23 20:29
 */
@RestController
@RequestMapping("/roleManage")
public class RoleManageController {
    @Autowired
    private RoleManageService roleManageService;

    @PostMapping("/add")
    public int add(@RequestBody Role role){
        return roleManageService.addRole(role);
    }
}
