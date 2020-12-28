package com.yealink.level1.controller;

import com.yealink.level1.bean.Role;
import com.yealink.level1.bean.StaffRoleRelation;
import com.yealink.level1.service.RoleManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PostMapping("/addRole")
    public int addRole(@RequestBody Role role){
        return roleManageService.addRole(role);
    }

    @PostMapping("/addRelation")
    public int addRelation(@RequestParam(value = "mobile",required = true)String mobile,
                           @RequestParam(value = "name",required = true)String name){
        return roleManageService.addStaffRoleRelation(mobile,name);
    }

    @DeleteMapping("/deleteRole")
    public void deleteRole(@RequestParam(value = "name",required = true)String name){
        roleManageService.deleteRole(name);
    }

    @DeleteMapping("deleteRelation")
    public void deleteRelation(@RequestParam(value = "mobile",required = true)String mobile,
                               @RequestParam(value = "name",required = true)String name){
        roleManageService.deleteRelation(mobile,name);
    }

    @PostMapping("/updateRole")
    public int updateRole(@RequestParam(value = "name",required = true)String name,
                          @RequestBody Role role){
        return roleManageService.updateRole(role,name);
    }

    @PostMapping("/updateRoleOfStaff")
    public int updateRoleOfStaff(@RequestParam(value = "mobile",required = true)String mobile,
                              @RequestParam(value = "oldName",required = true)String oldName,
                              @RequestParam(value = "newName",required = true)String newName){
        return roleManageService.updateRoleOfStaff(mobile,oldName,newName);
    }

    @PostMapping("/updateStaffOfRole")
    public int updateStaffOfRole(@RequestParam(value = "oldMobile",required = true) String oldMobile,
                                 @RequestParam(value = "name",required = true) String name,
                                 @RequestParam(value = "newMobile",required = true) String newMobile){
        return roleManageService.updateStaffOfRole(oldMobile,name,newMobile);
    }

    @GetMapping("/findRelationByMobile")
    public List<String> findRelationByMobile(@RequestParam(value = "mobile",required = true)String mobile){
        return roleManageService.findRelationByMobile(mobile);
    }

    @GetMapping("/listRoleOfEnterprise")
    public List<String> listRoleOfEnterprise(@RequestParam(value = "name",required = true)String name){
        return roleManageService.listRoleOfEnterprise(name);
    }

}
