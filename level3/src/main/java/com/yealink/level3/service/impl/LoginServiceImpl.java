package com.yealink.level3.service.impl;

import com.yealink.level3.bean.Account;
import com.yealink.level3.service.AccountService;
import com.yealink.level3.service.LoginService;
import com.yealink.level3.bean.result.ErrorCode;
import com.yealink.level3.bean.request.PersonalRequest;
import com.yealink.level3.bean.result.Result;
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

        if(accountVerify!=null) {
            return Result.failure(ErrorCode.ACCOUNT_IS_NOT_EXIST);
        }

        account.setPassword(personalRequest.getPassword());

        if(accountVerify.getPassword().equals(account.getPassword())) {
            return Result.failure(ErrorCode.PASSWORD_IS_WRONG);
        }

        return Result.success();
    }
}
