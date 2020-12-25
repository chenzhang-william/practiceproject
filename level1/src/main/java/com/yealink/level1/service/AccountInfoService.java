package com.yealink.level1.service;

import com.yealink.level1.bean.Account;
import java.util.List;

public interface AccountInfoService {

    int add(Account account);

    Account findAccountById(String id);

    List<Account> findAccountByStaffId(String staffId);

    String findIdByUsername(String username);

    int update(Account account);

    int delete(String id);

    List<Account> findAccountByEnterpriseName(String name);

    int bindAccountEnterprise(String username, String name);

    int bindAccountStaff(String username, String mobile);
}
