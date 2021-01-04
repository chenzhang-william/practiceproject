package com.yealink.level1.service.impl;

import com.yealink.level1.bean.Account;
import com.yealink.level1.bean.Enterprise;
import com.yealink.level1.bean.Staff;
import com.yealink.level1.bean.request.EnterpriseRequest;
import com.yealink.level1.bean.request.PersonalRequest;
import com.yealink.level1.bean.result.ErrorCode;
import com.yealink.level1.bean.result.Result;
import com.yealink.level1.service.AccountService;
import com.yealink.level1.service.EnterpriseInfoService;
import com.yealink.level1.service.EnterpriseService;
import com.yealink.level1.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

/**
 * @author zhangchen
 * @description EnterpriseInfoServiceImpl
 * @date 2021/1/4 18:52
 */
@Service
@Transactional
@Validated
public class EnterpriseInfoServiceImpl implements EnterpriseInfoService {
    @Autowired
    private StaffService staffService;
    @Autowired
    private EnterpriseService enterpriseService;
    @Autowired
    private AccountService accountService;

    @Override
    public Result updateEnterprise(EnterpriseRequest enterpriseRequest) {
        Enterprise enterprise = new Enterprise();
        enterprise.setNo(enterpriseRequest.getEnterpriseNo());
        if(enterpriseService.isEnterpriseExist(enterprise)){
            Enterprise oldEnterprise = enterpriseService.findEnterpriseByNo(enterprise);
            if(enterpriseRequest.getNewEnterpriseNo()!=null) enterprise.setNo(enterpriseRequest.getNewEnterpriseNo());
            enterprise.setName(enterpriseRequest.getEnterpriseName());
            enterpriseService.update(oldEnterprise,enterprise);
            return Result.success();
        }else return Result.failure(ErrorCode.ENTERPRISE_IS_NOT_EXIST);
    }

    @Override
    public Result deleteEnterprise(EnterpriseRequest enterpriseRequest) {
        Enterprise enterprise = new Enterprise();
        enterprise.setNo(enterpriseRequest.getEnterpriseNo());
        if(enterpriseService.isEnterpriseExist(enterprise)){
            enterpriseService.delete(enterprise);
            return Result.success();
        }else return Result.failure(ErrorCode.ENTERPRISE_IS_NOT_EXIST);
    }

    @Override
    public Result bindStaffEnterprise(EnterpriseRequest enterpriseRequest) {
        Staff staff = new Staff();
        staff.setMobile(enterpriseRequest.getMobile());
        Enterprise enterprise = new Enterprise();
        enterprise.setNo(enterpriseRequest.getEnterpriseNo());
        if(staffService.isStaffExist(staff)){
            staffService.bindStaffEnterprise(enterprise,staff);
            return Result.success();
        }else return Result.failure(ErrorCode.STAFF_IS_NOT_EXIST);
    }

    @Override
    public Result bindAccountEnterprise(EnterpriseRequest enterpriseRequest) {
        Enterprise enterprise = new Enterprise();
        enterprise.setNo(enterpriseRequest.getEnterpriseNo());
        Account account = new Account();
        account.setUsername(enterpriseRequest.getUsername());
        if(accountService.isAccountExist(account)){
            accountService.bindAccountEnterprise(enterprise,account);
            return Result.success();
        }else return Result.failure(ErrorCode.ACCOUNT_IS_NOT_EXIST);
    }

    @Override
    public Result addStaff(EnterpriseRequest enterpriseRequest) {
        Staff staff = new Staff();
        staff.setMobile(enterpriseRequest.getMobile());
        Enterprise enterprise = new Enterprise();
        enterprise.setNo(enterpriseRequest.getEnterpriseNo());
        if(enterpriseService.isEnterpriseExist(enterprise)){
            if(!staffService.isStaffExist(staff)){
                staff.setEmail(enterpriseRequest.getEmail());
                staff.setName(enterpriseRequest.getName());
                staff.setGender(enterpriseRequest.getGender());
                staffService.add(staff);
                staffService.bindStaffEnterprise(enterprise,staff);
                return Result.success();
            }else return Result.failure(ErrorCode.STAFF_HAS_EXIST);
        }else return Result.failure(ErrorCode.ENTERPRISE_IS_NOT_EXIST);


    }

    @Override
    public Result addAccount(EnterpriseRequest enterpriseRequest) {
        Account account = new Account();
        account.setUsername(enterpriseRequest.getUsername());
        Enterprise enterprise = new Enterprise();
        enterprise.setNo(enterpriseRequest.getEnterpriseNo());
        if(enterpriseService.isEnterpriseExist(enterprise)){
            if(!accountService.isAccountExist(account)){
                account.setPassword(enterpriseRequest.getPassword());
                accountService.add(account);
                accountService.bindAccountEnterprise(enterprise,account);
                return Result.success();
            }else return Result.failure(ErrorCode.ACCOUNT_HAS_EXIST);
        }else return Result.failure(ErrorCode.ENTERPRISE_IS_NOT_EXIST);
    }

    @Override
    public Result unbindStaff(EnterpriseRequest enterpriseRequest) {
        Staff staff = new Staff();
        staff.setMobile(enterpriseRequest.getMobile());
        Enterprise enterprise = new Enterprise();
        enterprise.setNo(enterpriseRequest.getEnterpriseNo());
        if(staffService.isStaffExist(staff)&&staffService.findStaffByMobile(staff).getEnterpriseId() == enterpriseService.findEnterpriseByNo(enterprise).getId()){
            staffService.unbindEnterprise(staff);
            return Result.success();
        }else return Result.failure(ErrorCode.STAFF_IS_NOT_EXIST);
    }

    @Override
    public Result unbindAccount(EnterpriseRequest enterpriseRequest) {
        Account account = new Account();
        account.setUsername(enterpriseRequest.getUsername());
        Enterprise enterprise = new Enterprise();
        enterprise.setNo((enterpriseRequest.getEnterpriseNo()));
        if(accountService.isAccountExist(account)&&accountService.findAccountByUsername(account).getEnterpriseId() == enterpriseService.findEnterpriseByNo(enterprise).getId()){
            accountService.unbindEnterprise(account);
            return Result.success();
        }else return Result.failure(ErrorCode.ACCOUNT_IS_NOT_EXIST);
    }

    @Override
    public Result addDep(EnterpriseRequest enterpriseRequest) {
        return null;
    }

    @Override
    public Result deleteDep(EnterpriseRequest enterpriseRequest) {
        return null;
    }

    @Override
    public Result updateDep(EnterpriseRequest enterpriseRequest) {
        return null;
    }

    @Override
    public Result updatePosition(EnterpriseRequest enterpriseRequest) {
        return null;
    }
}
