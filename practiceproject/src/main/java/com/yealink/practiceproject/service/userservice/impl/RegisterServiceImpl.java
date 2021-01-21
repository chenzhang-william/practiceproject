package com.yealink.practiceproject.service.userservice.impl;

import com.yealink.practiceproject.bean.*;
import com.yealink.practiceproject.bean.request.PersonalRequest;
import com.yealink.practiceproject.bean.result.ErrorCode;
import com.yealink.practiceproject.bean.result.Result;
import com.yealink.practiceproject.service.domain.*;
import com.yealink.practiceproject.service.userservice.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import static com.yealink.practiceproject.util.ConstantPool.Role.ENTERPRISE_CREATOR_POSITION;
import static com.yealink.practiceproject.util.ConstantPool.Role.ENTERPRISE_CREATOR_ROLE;

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

        if (accountService.isAccountExist(personalRequest.getUsername())) {
            return Result.failure(ErrorCode.ACCOUNT_HAS_EXIST);
        }

        Account account = Account.builder().username(personalRequest.getUsername()).build();

        Staff staff = Staff.builder().mobile(personalRequest.getMobile()).build();

        if (!staffService.isStaffExist(personalRequest.getMobile())) {
            staffService.add(staff);
        }

        account.setPassword(personalRequest.getPassword());

        accountService.add(account);

        accountService.bindAccountStaff(staff, account);

        return Result.success();
    }


    @Override
    public Result enterpriseRegister(PersonalRequest personalRequest) {

        if (!staffService.isStaffExist(personalRequest.getMobile())) {
            return Result.failure(ErrorCode.STAFF_IS_NOT_EXIST);
        }
        if (enterpriseService.isEnterpriseExist(personalRequest.getEnterpriseNo())) {
            return Result.failure(ErrorCode.ENTERPRISE_HAS_EXIST);
        }

        Staff staff = Staff.builder().mobile(personalRequest.getMobile()).build();

        Enterprise enterprise = Enterprise.builder().no(personalRequest.getEnterpriseNo()).name(personalRequest.getEnterpriseName()).build();

        enterpriseService.add(enterprise);

        staffService.bindStaffEnterprise(enterprise, staff);

        String staffId = staffService.findIdByMobile(staff.getMobile());

        addRoleRelation(staffId, ENTERPRISE_CREATOR_ROLE);

        String depId = addDep(personalRequest.getEnterpriseName(), enterprise.getId());

        addDepRelation(staffId, depId, ENTERPRISE_CREATOR_POSITION);

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

        Role role = Role.builder().name(roleName).build();

        StaffRoleRelation staffRoleRelation = new StaffRoleRelation();

        staffRoleRelation.setStaffId(staffId);
        staffRoleRelation.setRoleId(roleManageService.findRoleByName(role).getId());

        roleManageService.addStaffRoleRelation(staffRoleRelation);
    }

}
