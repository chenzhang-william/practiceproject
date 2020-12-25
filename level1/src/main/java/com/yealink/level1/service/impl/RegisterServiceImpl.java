package com.yealink.level1.service.impl;

import com.yealink.level1.bean.Account;
import com.yealink.level1.bean.Department;
import com.yealink.level1.bean.Enterprise;
import com.yealink.level1.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    private EnterpriseInfoService enterpriseInfoService;
    @Autowired
    private RoleManageService roleManageService;
    @Autowired
    private DepManageService depManageService;
    @Autowired
    private StaffInfoService staffInfoService;
    @Autowired
    private AccountInfoService accountInfoService;

    @Override
    public int[] accountRegister(Account account,String mobile) {
        int results[]=new int[2];
        if(accountInfoService.findIdByUsername(account.getUsername())==null){
            results[0] = accountInfoService.add(account);
            results[1] = accountInfoService.bindAccountStaff(account.getUsername(),mobile);
        }else {
            results[0]=-1;
        }
        return results;
    }




    @Override
    public int[] enterpriseRegister(Enterprise enterprise,String mobile) {
        int[] results = new int[6];
        String staffId = staffInfoService.findIdByMobile(mobile);
        if(enterpriseInfoService.findIdByName(enterprise.getName())==null){
            results[0] = enterpriseInfoService.add(enterprise);
            results[1] = staffInfoService.bindStaffEnterprise(enterprise.getName(),mobile);
            List<Account> accounts = accountInfoService.findAccountByStaffId(staffId);
            for(Account x:accounts) {
                results[2] += accountInfoService.bindAccountEnterprise(x.getUsername(), enterprise.getName());
            }
            results[3] = roleManageService.addStaffRoleRelation(staffId, "创建者");
            Department department = new Department();
            department.setEnterpriseId(enterpriseInfoService.findIdByName(enterprise.getName()));
            department.setName(enterprise.getName());
            results[4] = depManageService.addDep(department);
            results[5] = depManageService.addStaffDepRelation(staffId, enterprise.getName(),"Boss");
            return results;
        }else {
            results[0]=-1;
            return results;
        }
    }




}
