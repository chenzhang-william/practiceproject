package com.yealink.practiceproject.controller;

import com.yealink.practiceproject.service.userservice.LoginService;
import com.yealink.practiceproject.bean.request.PersonalRequest;
import com.yealink.practiceproject.bean.result.Result;
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
