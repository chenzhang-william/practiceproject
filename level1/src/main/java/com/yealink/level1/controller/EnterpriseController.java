package com.yealink.level1.controller;

import com.yealink.level1.bean.Enterprise;
import com.yealink.level1.service.EnterpriseInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/enterprise")
public class EnterpriseController {

    @Autowired
    private EnterpriseInfoService enterpriseInfoService;

    @GetMapping("/find/{id}")
    public Enterprise findEnterpriseById(@PathVariable("id") String id){
        return enterpriseInfoService.findEnterpriseById(id);
    }

    @PostMapping("/add")
    public int add(@RequestBody Enterprise enterprise){
        return enterpriseInfoService.add(enterprise);
    }

    @DeleteMapping("/delete")
    public void delete(@RequestParam(value = "id",required = true)String id){
        enterpriseInfoService.delete(id);
    }

    @PostMapping("/update")
    public int update(@RequestBody Enterprise enterprise){
        return enterpriseInfoService.update(enterprise);
    }

    @GetMapping("/findIdByName")
    public String findIdByName(@RequestParam(value = "name",required = true)String name){
        return enterpriseInfoService.findIdByName(name);
    }

}
