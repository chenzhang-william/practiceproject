package com.yealink.level2.service.impl;

import com.yealink.level2.bean.Account;
import com.yealink.level2.bean.Department;
import com.yealink.level2.bean.Enterprise;
import com.yealink.level2.bean.Staff;
import com.yealink.level2.bean.request.PersonalRequest;
import com.yealink.level2.bean.result.ErrorCode;
import com.yealink.level2.bean.result.Result;
import com.yealink.level2.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

/**
 * @author zhangchen
 * @description PersonalInfoService
 * @date 2021/1/4 13:57
 */
@Service
@Transactional
@Validated
public class PersonalInfoServiceImpl implements PersonalInfoService {

    @Autowired
    private StaffService staffService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private RoleManageService roleManageService;
    @Autowired
    private EnterpriseService enterpriseService;
    @Autowired
    private DepManageService depManageService;


    @Override
    public Result updateStaff(PersonalRequest personalRequest) {
        Staff staff = new Staff();
        staff.setMobile(personalRequest.getMobile());
        if(staffService.isStaffExist(staff)){
            Staff oldStaff = staffService.findStaffByMobile(staff);
            if(personalRequest.getNewMobile()!=null) staff.setMobile(personalRequest.getNewMobile());
            if(personalRequest.getGender()!=null) staff.setGender(staffService.genderTransfer(personalRequest.getGender()));
            staff.setEmail(personalRequest.getEmail());
            staff.setName(personalRequest.getName());
            staffService.update(oldStaff,staff);
            return Result.success();
        }else return Result.failure(ErrorCode.STAFF_IS_NOT_EXIST);

    }

    @Override
    public Result findEnterprise(PersonalRequest personalRequest) {
        Enterprise enterprise = new Enterprise();
        enterprise.setName(personalRequest.getEnterpriseName());
        return Result.success(enterpriseService.findEnterpriseByName(enterprise));
    }


    @Override
    public Result updateEnterpriseId(PersonalRequest personalRequest) {
        Enterprise enterprise = new Enterprise();
        enterprise.setNo(personalRequest.getEnterpriseNo());
        Account account = new Account();
        account.setUsername(personalRequest.getUsername());
        if(accountService.isAccountExist(account)){
            accountService.bindAccountEnterprise(enterprise,account);
            return Result.success();
        }else return Result.failure(ErrorCode.ACCOUNT_IS_NOT_EXIST);
    }

    @Override
    public Result updateStaffId(PersonalRequest personalRequest) {
        Staff staff = new Staff();
        staff.setMobile(personalRequest.getMobile());
        Account account = new Account();
        account.setUsername(personalRequest.getUsername());
        if(accountService.isAccountExist(account)){
            accountService.bindAccountStaff(staff,account);
            return Result.success();
        }else return Result.failure(ErrorCode.ACCOUNT_IS_NOT_EXIST);
    }

    @Override
    public Result updateAccount(PersonalRequest personalRequest) {
        Account account = new Account();
        account.setUsername(personalRequest.getUsername());
        if(accountService.isAccountExist(account)){
            Account oldAccount = accountService.findAccountByUsername(account);
            if(personalRequest.getNewUsername()!=null) account.setUsername(personalRequest.getNewUsername());
            account.setPassword(personalRequest.getPassword());
            accountService.update(oldAccount,account);
            return Result.success();
        }else return Result.failure(ErrorCode.ACCOUNT_IS_NOT_EXIST);
    }

    @Override
    public Result getAllAccounts(PersonalRequest personalRequest) {
        Staff staff = new Staff();
        staff.setMobile(personalRequest.getMobile());
        if(staffService.isStaffExist(staff)){
            return Result.success(accountService.findAccountOfStaff(staff));
        }else return Result.failure(ErrorCode.STAFF_IS_NOT_EXIST);

    }

    @Override
    public Result deleteAccount(PersonalRequest personalRequest) {
        Account account = new Account();
        account.setUsername(personalRequest.getUsername());
        Account accountVerify = accountService.findAccountByUsername(account);
        if(accountVerify!=null){
            if(accountVerify.getPassword()==personalRequest.getPassword()){
                accountService.delete(account);
                return Result.success();
            }else return Result.failure(ErrorCode.PASSWORD_IS_WRONG);
        }else return Result.failure(ErrorCode.ACCOUNT_IS_NOT_EXIST);
    }

    @Override
    public Result personalInfo(PersonalRequest personalRequest) {
        Staff staff =new Staff();
        staff.setMobile(personalRequest.getMobile());
        return Result.success(staffService.findStaffByMobile(staff));
    }

    @Override
    public Result findRoleOfStaff(PersonalRequest personalRequest) {
        Staff staff =new Staff();
        staff.setMobile(personalRequest.getMobile());
        if(staffService.isStaffExist(staff)){
            return Result.success(roleManageService.findRoleOfStaff(staff));
        }else return Result.failure(ErrorCode.STAFF_IS_NOT_EXIST);

    }

    @Override
    public Result enterpriseInfo(PersonalRequest personalRequest) {
        Enterprise enterprise = new Enterprise();
        enterprise.setNo(personalRequest.getEnterpriseNo());
        return Result.success(enterpriseService.findEnterpriseByNo(enterprise));
    }

    @Override
    public Result findTree(PersonalRequest personalRequest) {
        Department dep = new Department();
        Enterprise enterprise = new Enterprise();
        enterprise .setNo(personalRequest.getEnterpriseNo());
        enterprise = enterpriseService.findEnterpriseByNo(enterprise);
        dep.setName(enterprise.getName());
        dep.setEnterpriseId(enterprise.getId());
        return Result.success(depManageService.getTree(depManageService.findDep(dep).getId()));
    }
}
