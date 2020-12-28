package com.yealink.level1.service.impl;

import com.yealink.level1.bean.Account;
import com.yealink.level1.bean.Staff;
import com.yealink.level1.domain.AccountMapper;
import com.yealink.level1.service.AccountInfoService;
import com.yealink.level1.service.EnterpriseInfoService;
import com.yealink.level1.service.StaffInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class AccountInfoServiceImpl implements AccountInfoService {

    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private StaffInfoService staffInfoService;
    @Autowired
    private EnterpriseInfoService enterpriseInfoService;

    @Override
    public int add(Account account) {
        long now = new Date().getTime();
        account.setCreateTime(now);
        account.setModifyTime(now);
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

    @Override
    public String findIdByUsername(String username) {
        return accountMapper.findIdByUsername(username);
    }

    @Override
    public int update(Account account,String username) {
        account.setId(findIdByUsername(username));
        account.setModifyTime(new Date().getTime());
        return accountMapper.update(account);
    }

    @Override
    public int delete(String username) {
        return accountMapper.delete(findIdByUsername(username));
    }

    @Override
    public List<Account> findAccountByEnterpriseName(String name) {
        return accountMapper.findAccountByEnterpriseName(name);
    }

    @Override
    public int bindAccountEnterprise(String username, String name) {
        if(findIdByUsername(username) !=null && enterpriseInfoService.findIdByName(name)!= null){
            Account account = findAccountByUsername(username);
            account.setEnterpriseId(enterpriseInfoService.findIdByName(name));
            return update(account,username);
        }else return -1;
    }

    @Override
    public int bindAccountStaff(String username, String mobile) {
        Account account = findAccountByUsername(username);
        if (account.getId() != null){
            if(staffInfoService.findIdByMobile(mobile) == null){
                Staff staff =new Staff();
                staff.setMobile(mobile);
                staffInfoService.add(staff);
            }
            account.setStaffId(staffInfoService.findIdByMobile(mobile));
            return update(account,username);
        }else return -1;
    }

    @Override
    public Account findAccountByUsername(String username) {
        return findAccountById(findIdByUsername(username));
    }

    @Override
    public List<Account> findAccountByMobile(String mobile) {
        return findAccountByStaffId(staffInfoService.findIdByMobile(mobile));
    }
}
