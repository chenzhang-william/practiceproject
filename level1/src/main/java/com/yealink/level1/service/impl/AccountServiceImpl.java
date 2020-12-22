package com.yealink.level1.service.impl;

import com.yealink.level1.bean.Account;
import com.yealink.level1.domain.AccountMapper;
import com.yealink.level1.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountMapper accountMapper;

    @Override
    public int add(Account account) {
        return accountMapper.add(account);
    }

    @Override
    public Account findAccountById(String id) {
        return accountMapper.findAccountById(id);
    }

    @Override
    public List<Account> findAccountByStaffId(String staffId) {
        return accountMapper.findAccountByStaffId(staffId);
    }


}
