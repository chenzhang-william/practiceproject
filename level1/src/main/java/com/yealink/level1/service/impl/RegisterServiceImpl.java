package com.yealink.level1.service.impl;

import com.yealink.level1.bean.Account;
import com.yealink.level1.bean.Department;
import com.yealink.level1.bean.Enterprise;
import com.yealink.level1.bean.Staff;
import com.yealink.level1.domain.AccountMapper;
import com.yealink.level1.domain.EnterpriseMapper;
import com.yealink.level1.domain.StaffMapper;
import com.yealink.level1.service.DepManageService;
import com.yealink.level1.service.RegisterService;
import com.yealink.level1.service.RoleManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

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
    private EnterpriseMapper enterpriseMapper;
    @Autowired
    private RoleManageService roleManageService;
    @Autowired
    private DepManageService depManageService;

    @Override
    public int[] accountRegister(Account account,String mobile) {
        int results[]=new int[2];
        if(accountMapper.findIdByUsername(account.getUsername())==null){
            long now = new Date().getTime();
            account.setCreateTime(now);
            account.setModifyTime(now);
            results[0] = accountMapper.add(account);
            results[1] = bindAccountStaff(account.getUsername(),mobile);
        }else {
            results[0]=-1;
        }
        return results;
    }

    public int staffRegister(Staff staff) {
        if(staffMapper.findIdByMobile(staff.getMobile())==null){
            staff.setCreateTime(new Date().getTime());
            staff.setModifyTime(new Date().getTime());
            return staffMapper.add(staff);
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

        }else {
            Staff staff = new Staff();
            staff.setMobile(mobile);
            staffRegister(staff);
        }
        staffId = staffMapper.findIdByMobile(mobile);
        account.setStaffId(staffId);
        account.setModifyTime(new Date().getTime());
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
    public int[] enterpriseRegister(Enterprise enterprise,String mobile) {
        int[] results = new int[6];
        String staffId = staffMapper.findIdByMobile(mobile);
        if(enterpriseMapper.findIdByName(enterprise.getName())==null){
            long now =new Date().getTime();
            enterprise.setCreateTime(now);
            enterprise.setModifyTime(now);
            results[0] = enterpriseMapper.add(enterprise);
            results[1] = bindStaffEnterprise(enterprise.getName(),mobile);
            List<Account> accounts = accountMapper.findAccountByStaffId(staffId);
            for(Account x:accounts) {
                results[2] += bindAccountEnterprise(x.getUsername(), enterprise.getName());
            }
            results[3] = roleManageService.addStaffRoleRelation(staffId, "创建者");
            Department department = new Department();
            department.setEnterpriseId(enterpriseMapper.findIdByName(enterprise.getName()));
            department.setName(enterprise.getName());
            results[4] = depManageService.addDep(department);
            results[5] = depManageService.addStaffDepRelation(staffId, enterprise.getName(),"Boss");
            return results;
        }else {
            results[0]=-1;
            return results;
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
