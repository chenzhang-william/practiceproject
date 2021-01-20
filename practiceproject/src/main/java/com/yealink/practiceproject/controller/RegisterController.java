package com.yealink.practiceproject.controller;

import com.yealink.practiceproject.bean.request.PersonalRequest;
import com.yealink.practiceproject.bean.result.Result;
import com.yealink.practiceproject.service.userservice.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
