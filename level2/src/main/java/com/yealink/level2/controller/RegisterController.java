package com.yealink.level2.controller;

import com.yealink.level2.service.RegisterService;
import com.yealink.level2.bean.request.PersonalRequest;
import com.yealink.level2.bean.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
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
    public Result register(@RequestBody @Validated({PersonalRequest.AccountInsert.class, PersonalRequest.StaffUpdate.class}) PersonalRequest personalRequest) {
        return registerService.accountRegister(personalRequest);
    }


    @PostMapping("/enterprise")
    public Result registerEnterprise(@RequestBody @Validated({PersonalRequest.EnterpriseInsert.class, PersonalRequest.StaffUpdate.class}) PersonalRequest personalRequest) {
        return registerService.enterpriseRegister(personalRequest);
    }


}
