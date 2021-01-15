package com.yealink.practiceproject.service.userservice.impl;

import com.yealink.practiceproject.bean.Account;
import com.yealink.practiceproject.service.domain.AccountService;
import com.yealink.practiceproject.service.userservice.LoginService;
import com.yealink.practiceproject.bean.result.ErrorCode;
import com.yealink.practiceproject.bean.request.PersonalRequest;
import com.yealink.practiceproject.bean.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

/**
 * @author zhangchen
 * @description Login
 * @date 2020/12/23 17:08
 */
@Service
@Transactional
@Validated
public class LoginServiceImpl implements LoginService {

    @Autowired
    private AccountService accountService;

    @Override
    public Result login(PersonalRequest personalRequest) {
        Account account = new Account();
        account.setUsername(personalRequest.getUsername());

        Account accountVerify = accountService.findAccountByUsername(account);

        if(accountVerify == null) {
            return Result.failure(ErrorCode.ACCOUNT_IS_NOT_EXIST);
        }

        account.setPassword(personalRequest.getPassword());

        if (accountVerify.getPassword().equals(account.getPassword())) {
            return Result.failure(ErrorCode.PASSWORD_IS_WRONG);
        }

        return Result.success();
    }
}
