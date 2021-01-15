package com.yealink.level3.service.impl;


import com.yealink.level3.bean.Account;

import com.yealink.level3.bean.Enterprise;
import com.yealink.level3.bean.Staff;
import com.yealink.level3.domain.AccountMapper;
import com.yealink.level3.service.AccountService;
import com.yealink.level3.service.EnterpriseService;
import com.yealink.level3.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Service
@Transactional
@Validated
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private StaffService staffService;
    @Autowired
    private EnterpriseService enterpriseService;

    @Override
    public void add(@Valid Account account) {
        long now = new Date().getTime();
        account.setCreateTime(now);
        account.setModifyTime(now);
        accountMapper.add(account);
    }

    @Override
    public void delete(@Valid Account account) {
        accountMapper.delete(findAccountByUsername(account).getId());
    }

    @Override
    public void update(@Valid Account oldAccount, @Valid Account newAccount) {
        newAccount.setId(findAccountByUsername(oldAccount).getId());
        newAccount.setModifyTime(new Date().getTime());
        accountMapper.update(newAccount);
    }


    @Override
    public void bindAccountEnterprise(@Valid Enterprise enterprise, @Valid Account account) {
        Account newAccount = new Account();
        newAccount.setEnterpriseId(enterpriseService.findEnterpriseByNo(enterprise).getId());
        update(account, newAccount);
    }

    @Override
    public void bindAccountStaff(@Valid Staff staff, @Valid Account account) {
        Account newAccount = new Account();
        newAccount.setStaffId(staffService.findStaffByMobile(staff).getId());
        update(account, newAccount);
    }

    @Override
    public @NotNull(message = "账号不存在") Account findAccountByUsername(@Valid Account account) {
        return accountMapper.findAccountByUsername(account.getUsername());
    }

    @Override
    public @NotNull(message = "无账号") List<Account> findAccountOfStaff(@Valid Staff staff) {
        return accountMapper.findAccountByStaffId(staffService.findStaffByMobile(staff).getId());
    }

    @Override
    public List<Account> findAccountOfEnterprise(@Valid Enterprise enterprise) {
        return accountMapper.findAccountByEnterpriseNo(enterprise.getNo());
    }

    @Override
    public boolean isAccountExist(@Valid String username) {
        boolean result = accountMapper.findIdByUsername(username) != null ? true : false;
        return result;
    }

    @Override
    public void unbindEnterprise(@Valid Account account) {
        accountMapper.unbindEnterprise(account.getUsername());
    }

}
