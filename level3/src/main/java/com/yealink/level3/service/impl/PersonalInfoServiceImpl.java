package com.yealink.level3.service.impl;

import com.yealink.level3.bean.Account;
import com.yealink.level3.bean.Department;
import com.yealink.level3.bean.Enterprise;
import com.yealink.level3.bean.Staff;
import com.yealink.level3.bean.request.PersonalRequest;
import com.yealink.level3.bean.result.ErrorCode;
import com.yealink.level3.bean.result.Result;
import com.yealink.level3.service.*;
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

        if (!staffService.isStaffExist(staff)) {
            return Result.failure(ErrorCode.STAFF_IS_NOT_EXIST);
        }

        Staff oldStaff = staffService.findStaffByMobile(staff);

        if (personalRequest.getNewMobile() != null) {
            staff.setMobile(personalRequest.getNewMobile());
        }
        if (personalRequest.getGender() != null)
            staff.setGender(staffService.genderTransfer(personalRequest.getGender()));
        staff.setEmail(personalRequest.getEmail());
        staff.setName(personalRequest.getName());

        staffService.update(oldStaff, staff);
        return Result.success();

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
        if (!accountService.isAccountExist(account.getUsername())) {
            return Result.failure(ErrorCode.ACCOUNT_IS_NOT_EXIST);
        }

        accountService.bindAccountEnterprise(enterprise, account);
        return Result.success();
    }

    @Override
    public Result updateStaffId(PersonalRequest personalRequest) {
        Staff staff = new Staff();
        staff.setMobile(personalRequest.getMobile());
        Account account = new Account();
        account.setUsername(personalRequest.getUsername());
        if (!accountService.isAccountExist(account.getUsername())) {
            return Result.failure(ErrorCode.ACCOUNT_IS_NOT_EXIST);
        }

        accountService.bindAccountStaff(staff, account);
        return Result.success();
    }

    @Override
    public Result updateAccount(PersonalRequest personalRequest) {
        Account account = new Account();
        account.setUsername(personalRequest.getUsername());

        if (!accountService.isAccountExist(account.getUsername())) {
            return Result.failure(ErrorCode.ACCOUNT_IS_NOT_EXIST);
        }

        Account oldAccount = accountService.findAccountByUsername(account);
        if (personalRequest.getNewUsername() != null) {
            account.setUsername(personalRequest.getNewUsername());
        }
        account.setPassword(personalRequest.getPassword());

        accountService.update(oldAccount, account);
        return Result.success();
    }

    @Override
    public Result getAllAccounts(PersonalRequest personalRequest) {
        Staff staff = new Staff();
        staff.setMobile(personalRequest.getMobile());
        if (!staffService.isStaffExist(staff)) {
            return Result.failure(ErrorCode.STAFF_IS_NOT_EXIST);
        }

        return Result.success(accountService.findAccountOfStaff(staff));
    }

    @Override
    public Result deleteAccount(PersonalRequest personalRequest) {
        Account account = new Account();
        account.setUsername(personalRequest.getUsername());
        Account accountVerify = accountService.findAccountByUsername(account);
        if (accountVerify == null) {
            return Result.failure(ErrorCode.ACCOUNT_IS_NOT_EXIST);
        }
        if (!accountVerify.getPassword().equals(personalRequest.getPassword())) {
            return Result.failure(ErrorCode.PASSWORD_IS_WRONG);
        }
        accountService.delete(account);
        return Result.success();
    }

    @Override
    public Result personalInfo(PersonalRequest personalRequest) {
        Staff staff = new Staff();
        staff.setMobile(personalRequest.getMobile());
        return Result.success(staffService.findStaffByMobile(staff));
    }

    @Override
    public Result findRoleOfStaff(PersonalRequest personalRequest) {
        Staff staff = new Staff();
        staff.setMobile(personalRequest.getMobile());
        if (!staffService.isStaffExist(staff)) {
            return Result.failure(ErrorCode.STAFF_IS_NOT_EXIST);
        }
        return Result.success(roleManageService.findRoleOfStaff(staff));
    }

    @Override
    public Result enterpriseInfo(PersonalRequest personalRequest) {
        Enterprise enterprise = new Enterprise();
        enterprise.setNo(personalRequest.getEnterpriseNo());
        return Result.success(enterpriseService.findEnterpriseByNo(enterprise));
    }

    @Override
    public Result findTree(PersonalRequest personalRequest) {
        Enterprise enterprise = new Enterprise();
        enterprise.setNo(personalRequest.getEnterpriseNo());
        enterprise = enterpriseService.findEnterpriseByNo(enterprise);

        Department dep = new Department();
        dep.setName(enterprise.getName());
        dep.setEnterpriseId(enterprise.getId());
        return Result.success(depManageService.getTree(depManageService.findDep(dep).getId()));
    }
}
