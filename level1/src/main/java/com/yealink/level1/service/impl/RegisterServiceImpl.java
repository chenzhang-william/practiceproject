package com.yealink.level1.service.impl;

import com.yealink.level1.bean.*;
import com.yealink.level1.service.*;
import com.yealink.level1.bean.result.ErrorCode;
import com.yealink.level1.bean.request.PersonalRequest;
import com.yealink.level1.bean.result.Result;
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
        Account account =new Account();
        account.setUsername(personalRequest.getUsername());
        Staff staff =new Staff();
        staff.setMobile(personalRequest.getMobile());
        if(!accountService.isAccountExist(account)){
            account.setPassword(personalRequest.getPassword());
            accountService.add(account);
            if(!staffService.isStaffExist(staff)){
                staffService.add(staff);
            }
            accountService.bindAccountStaff(staff,account);
        return Result.success();
        }else return Result.failure(ErrorCode.ACCOUNT_HAS_EXIST);
    }




    @Override
    public Result enterpriseRegister(PersonalRequest personalRequest) {
        Staff staff =new Staff();
        staff.setMobile(personalRequest.getMobile());
        Enterprise enterprise =new Enterprise();
        enterprise.setNo(personalRequest.getEnterpriseNo());
        enterprise.setName(personalRequest.getEnterpriseName());
        StaffRoleRelation staffRoleRelation = new StaffRoleRelation();


        StaffDepartmentRelation staffDepartmentRelation = new StaffDepartmentRelation();
        if(staffService.isStaffExist(staff)) {
            if (!enterpriseService.isEnterpriseExist(enterprise)) {
                enterpriseService.add(enterprise);

                staffService.bindStaffEnterprise(enterprise, staff);

                String staffId = staffService.findStaffByMobile(staff).getId();

                Role role = new Role();
                role.setName("创建者");
                staffRoleRelation.setStaffId(staffId);
                staffRoleRelation.setRoleId(roleManageService.findRoleByName(role).getId());
                roleManageService.addStaffRoleRelation(staffRoleRelation);

                Department dep = new Department();
                dep.setName(personalRequest.getEnterpriseName());
                dep.setEnterpriseId(enterpriseService.findEnterpriseByNo(enterprise).getId());
                depManageService.addDep(dep);

                staffDepartmentRelation.setStaffId(staffId);
                staffDepartmentRelation.setPosition("Boss");
                staffDepartmentRelation.setDepartmentId(depManageService.findDep(dep).getId());
                depManageService.addStaffDepRelation(staffDepartmentRelation);
                return Result.success();
            } else return Result.failure(ErrorCode.ENTERPRISE_HAS_EXIST);
        }else return Result.failure(ErrorCode.STAFF_IS_NOT_EXIST);
    }




}
