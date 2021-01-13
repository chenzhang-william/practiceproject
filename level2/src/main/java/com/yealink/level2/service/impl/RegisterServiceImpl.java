package com.yealink.level2.service.impl;

import com.yealink.level2.bean.*;
import com.yealink.level2.service.*;
import com.yealink.level2.bean.result.ErrorCode;
import com.yealink.level2.bean.request.PersonalRequest;
import com.yealink.level2.bean.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

/**
 * @author zhangchen
 * @description RegisterServiceImpl
 * @date 2020/12/23 11:41
 */
@Service
@Transactional
@Validated
public class RegisterServiceImpl implements RegisterService {

    @Autowired
    private EnterpriseService enterpriseService;
    @Autowired
    private RoleManageService roleManageService;
    @Autowired
    private DepManageService depManageService;
    @Autowired
    private StaffService staffService;
    @Autowired
    private AccountService accountService;

    @Override
    public Result accountRegister(PersonalRequest personalRequest) {
        Account account = new Account();
        account.setUsername(personalRequest.getUsername());

        Staff staff = new Staff();
        staff.setMobile(personalRequest.getMobile());

        if (accountService.isAccountExist(account.getUsername())) {
            return Result.failure(ErrorCode.ACCOUNT_HAS_EXIST);
        }

        account.setPassword(personalRequest.getPassword());
        accountService.add(account);

        if (!staffService.isStaffExist(staff)) {
            staffService.add(staff);
        }

        accountService.bindAccountStaff(staff, account);

        return Result.success();
    }


    @Override
    public Result enterpriseRegister(PersonalRequest personalRequest) {
        Staff staff = new Staff();
        staff.setMobile(personalRequest.getMobile());

        Enterprise enterprise = new Enterprise();
        enterprise.setNo(personalRequest.getEnterpriseNo());
        enterprise.setName(personalRequest.getEnterpriseName());

        if (!staffService.isStaffExist(staff)) {
            return Result.failure(ErrorCode.STAFF_IS_NOT_EXIST);
        }
        if (enterpriseService.isEnterpriseExist(enterprise)) {
            return Result.failure(ErrorCode.ENTERPRISE_HAS_EXIST);
        }

        enterpriseService.add(enterprise);
        String staffId = staffService.findIdByMobile(staff.getMobile());

        staffService.bindStaffEnterprise(enterprise, staff);

        addRoleRelation(staffId, "创建者");

        String depId = addDep(personalRequest.getEnterpriseName(), enterprise.getId());

        addDepRelation(staffId, depId, "boss");
        return Result.success();
    }

    private void addDepRelation(String staffId, String depId, String position) {
        StaffDepartmentRelation staffDepartmentRelation = new StaffDepartmentRelation();
        staffDepartmentRelation.setStaffId(staffId);
        staffDepartmentRelation.setPosition(position);
        staffDepartmentRelation.setDepartmentId(depId);
        depManageService.addStaffDepRelation(staffDepartmentRelation);
    }

    private String addDep(String enterpriseName, String enterpriseId) {
        Department dep = new Department();
        dep.setName(enterpriseName);
        dep.setEnterpriseId(enterpriseId);
        depManageService.addDep(dep);
        return dep.getId();
    }

    private void addRoleRelation(String staffId, String roleName) {
        Role role = new Role();
        role.setName(roleName);
        StaffRoleRelation staffRoleRelation = new StaffRoleRelation();
        staffRoleRelation.setStaffId(staffId);
        staffRoleRelation.setRoleId(roleManageService.findRoleByName(role).getId());
        roleManageService.addStaffRoleRelation(staffRoleRelation);
    }

}
