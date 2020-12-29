package com.yealink.level1.controller;

import com.yealink.level1.bean.Account;
import com.yealink.level1.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public int login(@RequestBody Account account) {
        return loginService.login(account);
    }
}
