package com.yealink.level2.controller;

import com.yealink.level2.service.LoginService;
import com.yealink.level2.bean.request.PersonalRequest;
import com.yealink.level2.bean.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhangchen
 * @description LoginController
 * @date 2020/12/23 17:16
 */
@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/byAccount")
    public Result login(@RequestBody @Validated({PersonalRequest.Login.class}) PersonalRequest personalRequest) {
        return loginService.login(personalRequest);
    }
}
