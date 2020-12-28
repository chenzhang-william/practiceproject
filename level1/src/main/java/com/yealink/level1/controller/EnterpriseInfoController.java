package com.yealink.level1.controller;

import com.yealink.level1.bean.Enterprise;
import com.yealink.level1.bean.Staff;
import com.yealink.level1.service.EnterpriseInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/enterprise")
public class EnterpriseInfoController {

    @Autowired
    private EnterpriseInfoService enterpriseInfoService;

    @PostMapping("/add")
    public int add(@RequestBody Enterprise enterprise){
        return enterpriseInfoService.add(enterprise);
    }

    @DeleteMapping("/delete")
    public void delete(@RequestParam(value = "name",required = true)String name){
        enterpriseInfoService.delete(name);
    }

    @PostMapping("/update")
    public int update(@RequestParam(value = "name",required = true) String name,@RequestBody Enterprise enterprise){
        return enterpriseInfoService.update(enterprise,name);
    }

    @GetMapping("/findByName")
    public Enterprise findByName(@RequestParam(value = "name",required = true)String name){
        return enterpriseInfoService.findByName(name);
    }

    @GetMapping("/findByNo")
    public Enterprise findByNo(@RequestParam(value = "no",required = true)String no){
        return enterpriseInfoService.findByNo(no);
    }
}
