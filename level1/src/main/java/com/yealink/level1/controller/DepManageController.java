package com.yealink.level1.controller;

import com.yealink.level1.bean.Department;
import com.yealink.level1.bean.StaffDepartmentRelation;
import com.yealink.level1.service.DepManageService;
import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author zhangchen
 * @description DepManageController
 * @date 2020/12/24 16:15
 */
@RestController
@RequestMapping("/depManage")
public class DepManageController {
    @Autowired
    private DepManageService depManageService;

    @PostMapping("/addRelation")
    public int addRelation(@RequestParam(value = "mobile",required = true)String mobile,
                           @RequestParam(value = "name",required = true)String name,
                           @RequestParam(value = "position",required = true)String position){
        return depManageService.addStaffDepRelation(mobile,name,position);
    }

    @PostMapping("/addDep")
    public int addDepartment(@RequestBody Department dep){
        return depManageService.addDep(dep);
    }

    @DeleteMapping("/deleteDep")
    public int deleteDep(@RequestParam(value = "depName",required = true)String depName,
                         @RequestParam(value = "enterpriseName",required = true)String enterpriseName){
        return depManageService.deleteDep(depName,enterpriseName);
    }

    @DeleteMapping("/deleteRelation")
    public int deleteRelation(@RequestParam(value = "mobile",required = true)String mobile,
                              @RequestParam(value = "name",required = true)String name){
        return depManageService.deleteStaffDepRelation(mobile,name);
    }

    @PostMapping("/updateDep")
    public int updateDep(@RequestParam(value = "depName",required = true)String depName,
                         @RequestParam(value = "enterpriseName",required = true)String enterpriseName,
                         @RequestBody Department dep){
        return depManageService.updateDep(depName,enterpriseName,dep);
    }

    @PostMapping("/updateRelation")
    public int updateRelation(@RequestParam(value = "mobile",required = true)String mobile,
                      @RequestParam(value = "oldDep",required = true)String oldDep,
                      @RequestParam(value = "enterpriseName",required = true)String enterpriseName,
                      @RequestBody Map<String,String> newRelation){
        return depManageService.updateStaffDepRelation(mobile,oldDep,enterpriseName,newRelation);
    }

    @GetMapping("/getPosition")
    public List<Map<String,String>> getPosition(@RequestParam(value = "mobile",required = true)String mobile){
        return depManageService.getPosition(mobile);
    }

    @GetMapping("/getTree")
    public List getTree(@RequestParam(value = "departmentId",required = true)String departmentId){
        return depManageService.getTree(departmentId);
    }

    @GetMapping("/getChildStaff")
    public List getChildStaff(@RequestParam(value = "departmentId",required = true)String departmentId){
        return depManageService.getChildStaff(departmentId);
    }

    @GetMapping("/getChildDep")
    public List getChildDep(@RequestParam(value = "departmentId",required = true)String departmentId){
        return depManageService.getChildDep(departmentId);
    }
}
