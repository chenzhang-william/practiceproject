package com.yealink.level1.service.impl;

import com.yealink.level1.bean.Account;
import com.yealink.level1.bean.Enterprise;
import com.yealink.level1.bean.Staff;
import com.yealink.level1.domain.AccountMapper;
import com.yealink.level1.domain.EnterpriseMapper;
import com.yealink.level1.domain.StaffMapper;
import com.yealink.level1.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author zhangchen
 * @description RegisterServiceImpl
 * @date 2020/12/23 11:41
 */
@Service
@Transactional
public class RegisterServiceImpl implements RegisterService {
    @Autowired
    private StaffMapper staffMapper;
    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    EnterpriseMapper enterpriseMapper;

    @Override
    public int accountRegister(Account account) {
        if(accountMapper.findIdByUsername(account.getUsername())==null){
            long now = new Date().getTime();
            account.setCreateTime(now);
            account.setModifyTime(now);
            return accountMapper.add(account);
        }else {
            return -1;
        }

    }

    @Override
    public int bindAccountStaff(String username, String mobile) {
        String id = accountMapper.findIdByUsername(username);
        Account account = accountMapper.findAccountById(id);
        String staffId = null;
        if(staffMapper.findIdByMobile(mobile)!=null){
            staffId = staffMapper.findIdByMobile(mobile);
        }else {
            Staff staff = new Staff();
            staff.setMobile(mobile);
            staffMapper.add(staff);
            staffId = staffMapper.findIdByMobile(mobile);
        }
        account.setStaffId(staffId);
        long now = new Date().getTime();
        account.setModifyTime(now);
        return accountMapper.update(account);
    }

    public int bindAccountEnterprise(String username, String name) {
        String id = accountMapper.findIdByUsername(username);
        Account account = accountMapper.findAccountById(id);
        String enterpriseId = enterpriseMapper.findIdByName(name);
        if(enterpriseId !=null){
            account.setEnterpriseId(enterpriseId);
            long now = new Date().getTime();
            account.setModifyTime(now);
            return accountMapper.update(account);
        }else {
            return -1;
        }

    }

    @Override
    public int enterpriseRegister(Enterprise enterprise) {
        if(enterpriseMapper.findIdByName(enterprise.getName())==null){
            long now =new Date().getTime();
            enterprise.setCreateTime(now);
            enterprise.setModifyTime(now);
            return enterpriseMapper.add(enterprise);
        }else {
            return -1;
        }
    }

    public int bindStaffEnterprise(String name,String mobile){
        String enterpriseId = enterpriseMapper.findIdByName(name);
        if(enterpriseMapper.findIdByName(name)==null){
            return -1;
        }else{
            Staff staff = staffMapper.findStaffById(staffMapper.findIdByMobile(mobile));
            staff.setEnterpriseId(enterpriseId);
            staff.setModifyTime(new Date().getTime());
            return staffMapper.update(staff);
        }
    }



}
