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
        String username = account.getUsername();
        if(accountInfoService.findIdByUsername(username)==null){
            results[0] = accountInfoService.add(account);
            results[1] = accountInfoService.bindAccountStaff(username,mobile);
        }else {
            results[0]=-1;
        }
        return results;
    }




    @Override
    public int[] enterpriseRegister(Enterprise enterprise,String mobile) {
        int[] results = new int[6];
        String enterpriseName = enterprise.getName();
        if(enterpriseInfoService.findIdByName(enterpriseName)==null){
            results[0] = enterpriseInfoService.add(enterprise);
            results[1] = staffInfoService.bindStaffEnterprise(enterpriseName,mobile);
            List<Account> accounts = accountInfoService.findAccountByMobile(mobile);
            for(Account x:accounts) {
                results[2] += accountInfoService.bindAccountEnterprise(x.getUsername(), enterpriseName);
            }
            results[3] = roleManageService.addStaffRoleRelation(mobile, "创建者");
            Department department = new Department();
            String enterpriseId = enterpriseInfoService.findIdByName(enterpriseName);
            department.setEnterpriseId(enterpriseId);
            department.setName(enterpriseName);
            results[4] = depManageService.addDep(department);
            results[5] = depManageService.addStaffDepRelation(mobile, enterpriseName,"Boss");
            return results;
        }else {
            results[0]=-1;
            return results;
        }
    }




}
