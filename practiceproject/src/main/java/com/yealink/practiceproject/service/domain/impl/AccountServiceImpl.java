package com.yealink.practiceproject.service.domain.impl;


import com.yealink.practiceproject.bean.Account;
import com.yealink.practiceproject.bean.Enterprise;
import com.yealink.practiceproject.bean.Staff;
import com.yealink.practiceproject.dao.AccountMapper;
import com.yealink.practiceproject.service.domain.AccountService;
import com.yealink.practiceproject.service.domain.EnterpriseService;
import com.yealink.practiceproject.service.domain.StaffService;
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

        update(account, Account.builder().enterpriseId(enterpriseService.findEnterpriseByNo(enterprise).getId()).build());
    }

    @Override
    public void bindAccountStaff(@Valid Staff staff, @Valid Account account) {

        update(account, Account.builder().staffId(staffService.findStaffByMobile(staff).getId()).build());
    }

    @Override
    public @NotNull(message = "???????????????") Account findAccountByUsername(@Valid Account account) {
        return accountMapper.findAccountByUsername(account.getUsername());
    }

    @Override
    public @NotNull(message = "?????????") List<Account> findAccountOfStaff(@Valid Staff staff) {
        return accountMapper.findAccountByStaffId(staffService.findStaffByMobile(staff).getId());
    }

    @Override
    public List<Account> findAccountOfEnterprise(@Valid Enterprise enterprise) {
        return accountMapper.findAccountByEnterpriseNo(enterprise.getNo());
    }

    @Override
    public boolean isAccountExist(@Valid String username) {
        return accountMapper.findIdByUsername(username) != null;
    }

    @Override
    public void unbindEnterprise(@Valid Account account) {
        accountMapper.unbindEnterprise(account.getUsername());
    }

}
