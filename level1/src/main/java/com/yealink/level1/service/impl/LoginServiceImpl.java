package com.yealink.level1.service.impl;

import com.yealink.level1.bean.Account;
import com.yealink.level1.domain.AccountMapper;
import com.yealink.level1.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author zhangchen
 * @description Login
 * @date 2020/12/23 17:08
 */
@Service
@Transactional
public class LoginServiceImpl implements LoginService {
    @Autowired
    private AccountMapper accountMapper;

    @Override
    public int login(Account account) {
        String id = accountMapper.findIdByUsername(account.getUsername());
        if(id != null) {
            Account accountVerify = accountMapper.findAccountById(id);
            if (accountVerify.getPassword().equals(account.getPassword())) {
                return 1;
            } else {
                return -2;
            }
        }else {
            return -1;
        }
    }
}
