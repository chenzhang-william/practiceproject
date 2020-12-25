package com.yealink.level1.controller;

import com.yealink.level1.bean.Account;
import com.yealink.level1.bean.Enterprise;
import com.yealink.level1.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author zhangchen
 * @description RegisterController
 * @date 2020/12/23 14:25
 */

@RestController
@RequestMapping("/register")
public class RegisterController {

    @Autowired
    private RegisterService registerService;

    @PostMapping("/account")
    public int[] register(@RequestBody Account account,
                          @RequestParam(value = "mobile",required = true)String mobile){
        return registerService.accountRegister(account,mobile);
    }



    @PostMapping("/enterprise")
    public int[] registerEnterprise(@RequestParam(value = "mobile", required = true)String mobile,
                                  @RequestBody Enterprise enterprise){
        return registerService.enterpriseRegister(enterprise,mobile);
    }




}
