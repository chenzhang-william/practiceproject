package com.yealink.level1.controller;

import com.yealink.level1.bean.Enterprise;
import com.yealink.level1.service.EnterpriseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/enterprise")
public class EnterpriseController {

    @Autowired
    private EnterpriseService enterpriseService;

    @GetMapping("/find/{id}")
    public Enterprise findEnterpriseById(@PathVariable("id") String id){
        return enterpriseService.findEnterpriseById(id);
    }


}
