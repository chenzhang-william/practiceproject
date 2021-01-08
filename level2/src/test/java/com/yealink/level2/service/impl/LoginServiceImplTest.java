package com.yealink.level2.service.impl;

import com.yealink.level2.bean.Account;
import com.yealink.level2.service.AccountService;
import com.yealink.level2.service.LoginService;
import com.yealink.level2.bean.request.PersonalRequest;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author zhangchen
 * @description LoginServiceImplTest
 * @date 2020/12/31 16:58
 */
@SpringBootTest
class LoginServiceImplTest {


    private LoginService loginService;
    private AccountService accountService;
    private Account account;
    private PersonalRequest personalRequest;


    @Before
    public void setUp() throws Exception{
        account = mock(Account.class);
        personalRequest = new PersonalRequest();
    }

    @Test
    void login() {


    }
}