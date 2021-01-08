package com.yealink.level2.service;

import com.yealink.level2.bean.Account;
import com.yealink.level2.bean.Enterprise;
import com.yealink.level2.bean.Staff;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

public interface AccountService {

    void add(@Valid Account account);

    void delete(@Valid Account account);

    void update(@Valid Account oldAccount,@Valid Account newAccount);

    void bindAccountEnterprise(@Valid Enterprise enterprise, @Valid Account account);

    void bindAccountStaff(@Valid Staff staff,@Valid Account account);

    @NotNull(message = "账号不存在") Account findAccountByUsername(@Valid Account account);

    @NotNull(message = "无账号") List<Account> findAccountOfStaff(@Valid Staff staff);

    List<Account> findAccountOfEnterprise(@Valid Enterprise enterprise);

    boolean isAccountExist(@Valid Account account);

    void unbindEnterprise(@Valid Account account);
}
