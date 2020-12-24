package com.yealink.level1.controller;

import com.yealink.level1.service.DepManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public int addRelation(@RequestParam(value = "staffId",required = true)String staffId,
                           @RequestParam(value = "name",required = true)String name,
                           @RequestParam(value = "position",required = true)String position){
        return depManageService.addStaffDepRelation(staffId,name,position);
    }


}
