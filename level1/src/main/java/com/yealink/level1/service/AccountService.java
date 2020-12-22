package com.yealink.level1.service;

import com.yealink.level1.bean.Account;

import java.util.List;


public interface AccountService {

    int add(Account account);

    Account findAccountById(String id);

    List<Account> findAccountByStaffId(String staffId);
}
