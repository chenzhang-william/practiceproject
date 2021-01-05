package com.yealink.level1.service.impl;

import com.baomidou.mybatisplus.extension.api.R;
import com.yealink.level1.bean.*;
import com.yealink.level1.bean.request.EnterpriseRequest;
import com.yealink.level1.bean.request.PersonalRequest;
import com.yealink.level1.bean.result.ErrorCode;
import com.yealink.level1.bean.result.Result;
import com.yealink.level1.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.management.relation.Relation;

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
    @Autowired
    private DepManageService depManageService;
    @Autowired
    private RoleManageService roleManageService;

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
    public Result findStaff(EnterpriseRequest enterpriseRequest) {
        Staff staff = new Staff();
        staff.setName(enterpriseRequest.getName());
        return Result.success(staffService.findStaffByName(staff));
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
        Department dep = new Department();
        dep.setName(enterpriseRequest.getDepName());
        Enterprise enterprise = new Enterprise();
        enterprise.setNo(enterpriseRequest.getEnterpriseNo());
        enterprise = enterpriseService.findEnterpriseByNo(enterprise);
        dep.setEnterpriseId(enterprise.getId());

        if(!depManageService.isDepExist(dep)) {
            Department parentDep = new Department();
            parentDep.setEnterpriseId(enterprise.getId());
            if (enterpriseRequest.getParentDepName() != null) {
                parentDep.setName(enterpriseRequest.getParentDepName());
                if(!depManageService.isDepExist(parentDep)) return Result.failure(ErrorCode.DEPARTMENT_IS_NOT_EXIST);
            } else {
                parentDep.setName(enterprise.getName());
            }
            dep.setParentId(depManageService.findDep(parentDep).getId());
            depManageService.addDep(dep);
            return Result.success();
        }else return Result.failure(ErrorCode.DEPARTMENT_HAS_EXIST);
    }

    @Override
    public Result deleteDep(EnterpriseRequest enterpriseRequest) {
        Department dep = new Department();
        dep.setName(enterpriseRequest.getDepName());
        Enterprise enterprise = new Enterprise();
        enterprise.setNo(enterpriseRequest.getEnterpriseNo());
        enterprise = enterpriseService.findEnterpriseByNo(enterprise);
        dep.setEnterpriseId(enterprise.getId());

        if(depManageService.isDepExist(dep)) {
            depManageService.deleteDep(dep);
            return Result.success();
        }else return Result.failure(ErrorCode.DEPARTMENT_IS_NOT_EXIST);
    }

    @Override
    public Result updateDep(EnterpriseRequest enterpriseRequest) {
        Department dep = new Department();
        dep.setName(enterpriseRequest.getDepName());
        Enterprise enterprise = new Enterprise();
        enterprise.setNo(enterpriseRequest.getEnterpriseNo());
        enterprise = enterpriseService.findEnterpriseByNo(enterprise);
        dep.setEnterpriseId(enterprise.getId());

        if(depManageService.isDepExist(dep)) {
            Department oldDep = depManageService.findDep(dep);
            if(enterpriseRequest.getNewDepName()!=null) dep.setName(enterpriseRequest.getNewDepName());
            if(enterpriseRequest.getParentDepName()!=null){
                Department parentDep = new Department();
                parentDep.setName(enterpriseRequest.getParentDepName());
                parentDep.setEnterpriseId(enterprise.getId());
                parentDep = depManageService.findDep(parentDep);
                dep.setParentId(parentDep.getId());
            }
            depManageService.updateDep(oldDep,dep);
            return Result.success();
        }else return Result.failure(ErrorCode.DEPARTMENT_IS_NOT_EXIST);
    }

    @Override
    public Result addStaffDepRelation(EnterpriseRequest enterpriseRequest) {
        Department dep = new Department();
        dep.setName(enterpriseRequest.getDepName());
        Enterprise enterprise = new Enterprise();
        enterprise.setNo(enterpriseRequest.getEnterpriseNo());
        enterprise = enterpriseService.findEnterpriseByNo(enterprise);
        dep.setEnterpriseId(enterprise.getId());
        dep = depManageService.findDep(dep);

        Staff staff = new Staff();
        staff.setMobile(enterpriseRequest.getMobile());
        staff = staffService.findStaffByMobile(staff);

        if(dep.getEnterpriseId() == staff.getEnterpriseId()){
            StaffDepartmentRelation relation = new StaffDepartmentRelation();
            relation.setStaffId(staff.getId());
            relation.setDepartmentId(dep.getId());
            relation.setPosition(enterpriseRequest.getPosition());
            if(depManageService.findRelation(relation)==null){
                depManageService.addStaffDepRelation(relation);
                return Result.success();
            }else return Result.failure(ErrorCode.DEPRELATION_HAS_EXIST);
        }else return Result.failure(ErrorCode.ENTERPRISE_MISMATCH);
    }

    @Override
    public Result deleteStaffDepRelation(EnterpriseRequest enterpriseRequest) {
        Department dep = new Department();
        dep.setName(enterpriseRequest.getDepName());
        Enterprise enterprise = new Enterprise();
        enterprise.setNo(enterpriseRequest.getEnterpriseNo());
        enterprise = enterpriseService.findEnterpriseByNo(enterprise);
        dep.setEnterpriseId(enterprise.getId());
        dep = depManageService.findDep(dep);

        Staff staff = new Staff();
        staff.setMobile(enterpriseRequest.getMobile());
        staff = staffService.findStaffByMobile(staff);

        if(dep.getEnterpriseId() == staff.getEnterpriseId()){
            StaffDepartmentRelation relation = new StaffDepartmentRelation();
            relation.setStaffId(staff.getId());
            relation.setDepartmentId(dep.getId());
            relation.setPosition(enterpriseRequest.getPosition());
            depManageService.deleteStaffDepRelation(relation);
            return Result.success();
        }else return Result.failure(ErrorCode.ENTERPRISE_MISMATCH);
    }

    @Override
    public Result updateStaffDepRelation(EnterpriseRequest enterpriseRequest) {
        Department dep = new Department();
        dep.setName(enterpriseRequest.getDepName());
        Enterprise enterprise = new Enterprise();
        enterprise.setNo(enterpriseRequest.getEnterpriseNo());
        enterprise = enterpriseService.findEnterpriseByNo(enterprise);
        dep.setEnterpriseId(enterprise.getId());
        dep = depManageService.findDep(dep);

        Staff staff = new Staff();
        staff.setMobile(enterpriseRequest.getMobile());
        staff = staffService.findStaffByMobile(staff);

        if(dep.getEnterpriseId() == staff.getEnterpriseId()){
            StaffDepartmentRelation relation = new StaffDepartmentRelation();
            relation.setStaffId(staff.getId());
            relation.setDepartmentId(dep.getId());
            StaffDepartmentRelation oldRelation = depManageService.findRelation(relation);
            relation.setPosition(enterpriseRequest.getPosition());
            if(enterpriseRequest.getNewDepName()!=null){
                dep.setName(enterpriseRequest.getNewDepName());
                dep = depManageService.findDep(dep);
                relation.setDepartmentId(dep.getId());
            }
            depManageService.updateStaffDepRelation(oldRelation,relation);
            return Result.success();
        }else return Result.failure(ErrorCode.ENTERPRISE_MISMATCH);
    }

    @Override
    public Result findTree(EnterpriseRequest enterpriseRequest) {
        Department dep = new Department();
        Enterprise enterprise = new Enterprise();
        enterprise .setNo(enterpriseRequest.getEnterpriseNo());
        enterprise = enterpriseService.findEnterpriseByNo(enterprise);
        dep.setName(enterprise.getName());
        dep.setEnterpriseId(enterprise.getId());
        return Result.success(depManageService.getTree(depManageService.findDep(dep).getId()));
    }

    @Override
    public Result updateStaffRoleRelation(EnterpriseRequest enterpriseRequest) {
        Role role = new Role();
        role.setName(enterpriseRequest.getRoleName());
        role = roleManageService.findRoleByName(role);

        Staff staff = new Staff();
        staff.setMobile(enterpriseRequest.getMobile());
        staff = staffService.findStaffByMobile(staff);

        StaffRoleRelation relation = new StaffRoleRelation();
        relation.setStaffId(staff.getId());
        relation.setRoleId(role.getId());
        StaffRoleRelation oldRelation = roleManageService.findRelation(relation);
        //获取新的relation
        role.setName(enterpriseRequest.getRoleName());
        role = roleManageService.findRoleByName(role);
        staff.setMobile(enterpriseRequest.getMobile());
        staff = staffService.findStaffByMobile(staff);
        relation.setStaffId(staff.getId());
        relation.setRoleId(role.getId());
        roleManageService.updateStaffRoleRelation(oldRelation,relation);
        return Result.success();
    }

    @Override
    public Result addStaffRoleRelation(EnterpriseRequest enterpriseRequest) {
        Role role = new Role();
        role.setName(enterpriseRequest.getRoleName());
        role = roleManageService.findRoleByName(role);

        Staff staff = new Staff();
        staff.setMobile(enterpriseRequest.getMobile());
        staff = staffService.findStaffByMobile(staff);

        StaffRoleRelation relation = new StaffRoleRelation();
        relation.setStaffId(staff.getId());
        relation.setRoleId(role.getId());

        if(roleManageService.findRelation(relation)==null){
            roleManageService.addStaffRoleRelation(relation);
            return Result.success();
        }else return Result.failure(ErrorCode.ROLERELATION_HAS_EXIST);
    }

    @Override
    public Result deleteStaffRoleRelation(EnterpriseRequest enterpriseRequest) {
        Role role = new Role();
        role.setName(enterpriseRequest.getRoleName());
        role = roleManageService.findRoleByName(role);

        Staff staff = new Staff();
        staff.setMobile(enterpriseRequest.getMobile());
        staff = staffService.findStaffByMobile(staff);

        StaffRoleRelation relation = new StaffRoleRelation();
        relation.setStaffId(staff.getId());
        relation.setRoleId(role.getId());

        if(roleManageService.findRelation(relation)==null){
            roleManageService.deleteStaffRoleRelation(relation);
            return Result.success();
        }else return Result.failure(ErrorCode.ROLERELATION_IS_NOT_EXIST);
    }

    @Override
    public Result listRoleRelation(EnterpriseRequest enterpriseRequest) {
        Role role = new Role();
        Enterprise enterprise = new Enterprise();
        role.setName(enterpriseRequest.getRoleName());
        enterprise.setNo(enterpriseRequest.getEnterpriseNo());
        return Result.success(roleManageService.findStaffOfRoleInEnterprise(role,enterprise));
    }
}
