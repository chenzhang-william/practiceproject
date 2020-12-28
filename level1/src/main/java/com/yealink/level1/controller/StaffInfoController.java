package com.yealink.level1.controller;

import com.yealink.level1.bean.Staff;
import com.yealink.level1.service.StaffInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/staff")
public class StaffInfoController {

    @Autowired
    private StaffInfoService staffInfoService;

    @PostMapping("/add")
    public int addStaff(@RequestBody Staff staff){
        return staffInfoService.add(staff);
    }

    @PostMapping("/update")
    public int updateStaff(@RequestParam(value = "mobile",required = true)String mobile,
                           @RequestBody Staff staff){
        return staffInfoService.update(staff,mobile);
    }


    @DeleteMapping("/delete")
    public void deleteStaff(@RequestParam(value = "mobile") String mobile){
        staffInfoService.delete(mobile);
    }

    @PostMapping("/bindEnterprise")
    public int bindEnterprise(@RequestParam(value = "name",required = true)String name,
                              @RequestParam(value = "mobile",required = true)String mobile){
        return staffInfoService.bindStaffEnterprise(name,mobile);
    }

    @GetMapping("/findStaffByEnterpriseName")
    public List<Staff> findStaffByEnterpriseName(@RequestParam(value = "name",required = true)String name){
        return staffInfoService.findStaffByEnterpriseName(name);
    }

    @GetMapping("/findStaffByMobile")
    public Staff findStaffByMobile(@RequestParam(value = "mobile",required = true)String mobile){
        return staffInfoService.findStaffByMobile(mobile);
    }

    @GetMapping("/findStaffByName")
    public List<Staff> findStaffByName(@RequestParam(value = "name",required = true)String name){
        return staffInfoService.findStaffByName(name);
    }
}
