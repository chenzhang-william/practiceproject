package com.yealink.level1.service.impl;

import com.yealink.level1.service.LoginService;
import com.yealink.level1.bean.request.PersonalRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author zhangchen
 * @description LoginServiceImplTest
 * @date 2020/12/31 16:58
 */
@SpringBootTest
class LoginServiceImplTest {

    @Autowired
    private LoginService loginService;
    @Test
    void login() {
        PersonalRequest personalRequest = new PersonalRequest();
        personalRequest.setUsername("test");
        personalRequest.setPassword("123456");
        loginService.login(personalRequest);
    }
}