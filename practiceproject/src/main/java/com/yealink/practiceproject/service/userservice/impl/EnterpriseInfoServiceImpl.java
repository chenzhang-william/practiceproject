package com.yealink.practiceproject.service.userservice.impl;

import com.yealink.practiceproject.bean.*;
import com.yealink.practiceproject.bean.request.EnterpriseRequest;
import com.yealink.practiceproject.bean.result.ErrorCode;
import com.yealink.practiceproject.bean.result.Result;
import com.yealink.practiceproject.service.domain.*;
import com.yealink.practiceproject.service.userservice.EnterpriseInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import static com.yealink.practiceproject.util.DataConversion.genderTransfer;

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

        if (!enterpriseService.isEnterpriseExist(enterpriseRequest.getEnterpriseNo())) {
            return Result.failure(ErrorCode.ENTERPRISE_IS_NOT_EXIST);
        }

        Enterprise enterprise = getEnterprise(enterpriseRequest);
        enterprise.setName(enterpriseRequest.getEnterpriseName());

        Enterprise oldEnterprise = enterpriseService.findEnterpriseByNo(enterprise);

        if (enterpriseRequest.getNewEnterpriseNo() != null) {
            enterprise.setNo(enterpriseRequest.getNewEnterpriseNo());
        }

        enterpriseService.update(oldEnterprise, enterprise);

        return Result.success();
    }

    @Override
    public Result deleteEnterprise(EnterpriseRequest enterpriseRequest) {

        if (!enterpriseService.isEnterpriseExist(enterpriseRequest.getEnterpriseNo())) {
            return Result.failure(ErrorCode.ENTERPRISE_IS_NOT_EXIST);
        }

        Enterprise enterprise = getEnterprise(enterpriseRequest);

        enterpriseService.delete(enterprise);

        return Result.success();
    }

    @Override
    public Result bindStaffEnterprise(EnterpriseRequest enterpriseRequest) {

        if (!staffService.isStaffExist(enterpriseRequest.getMobile())) {
            return Result.failure(ErrorCode.STAFF_IS_NOT_EXIST);
        }

        Staff staff = Staff.builder().mobile(enterpriseRequest.getMobile()).build();

        Enterprise enterprise = getEnterprise(enterpriseRequest);

        staffService.bindStaffEnterprise(enterprise, staff);

        return Result.success();
    }

    @Override
    public Result bindAccountEnterprise(EnterpriseRequest enterpriseRequest) {

        Enterprise enterprise = getEnterprise(enterpriseRequest);

        Account account = getAccount(enterpriseRequest);

        if (!accountService.isAccountExist(account.getUsername()))
            return Result.failure(ErrorCode.ACCOUNT_IS_NOT_EXIST);

        accountService.bindAccountEnterprise(enterprise, account);

        return Result.success();
    }


    @Override
    public Result addStaff(EnterpriseRequest enterpriseRequest) {

        if (!enterpriseService.isEnterpriseExist(enterpriseRequest.getEnterpriseNo())) {
            return Result.failure(ErrorCode.ENTERPRISE_IS_NOT_EXIST);
        }
        if (staffService.isStaffExist(enterpriseRequest.getMobile())) {
            return Result.failure(ErrorCode.STAFF_HAS_EXIST);
        }

        Enterprise enterprise = getEnterprise(enterpriseRequest);

        Staff staff = new Staff();
        staff.setMobile(enterpriseRequest.getMobile());
        staff.setEmail(enterpriseRequest.getEmail());
        staff.setName(enterpriseRequest.getName());
        staff.setGender(genderTransfer(enterpriseRequest.getGender()));

        staffService.add(staff);

        staffService.bindStaffEnterprise(enterprise, staff);

        return Result.success();
    }

    @Override
    public Result findStaff(EnterpriseRequest enterpriseRequest) {

        Staff staff = new Staff();
        staff.setName(enterpriseRequest.getName());

        return Result.success(staffService.findStaffByName(staff));
    }

    @Override
    public Result addAccount(EnterpriseRequest enterpriseRequest) {

        if (!enterpriseService.isEnterpriseExist(enterpriseRequest.getEnterpriseNo())) {
            return Result.failure(ErrorCode.ENTERPRISE_IS_NOT_EXIST);
        }
        if (accountService.isAccountExist(enterpriseRequest.getUsername())) {
            return Result.failure(ErrorCode.ACCOUNT_HAS_EXIST);
        }

        Account account = getAccount(enterpriseRequest);
        account.setPassword(enterpriseRequest.getPassword());

        Enterprise enterprise = getEnterprise(enterpriseRequest);

        accountService.add(account);

        accountService.bindAccountEnterprise(enterprise, account);

        return Result.success();
    }

    @Override
    public Result unbindStaff(EnterpriseRequest enterpriseRequest) {
        Staff staff = new Staff();
        staff.setMobile(enterpriseRequest.getMobile());

        Enterprise enterprise = getEnterprise(enterpriseRequest);

        if (!(staffService.isStaffExist(enterpriseRequest.getMobile())
                && staffService.findStaffByMobile(staff).getEnterpriseId().equals(enterpriseService.findEnterpriseByNo(enterprise).getId()))) {
            return Result.failure(ErrorCode.STAFF_IS_NOT_EXIST);
        }

        staffService.unbindEnterprise(staff);

        return Result.success();
    }

    @Override
    public Result unbindAccount(EnterpriseRequest enterpriseRequest) {

        Account account = getAccount(enterpriseRequest);

        Enterprise enterprise = getEnterprise(enterpriseRequest);

        if (!(accountService.isAccountExist(account.getUsername())
                && accountService.findAccountByUsername(account).getEnterpriseId().equals(enterpriseService.findEnterpriseByNo(enterprise).getId()))) {
            return Result.failure(ErrorCode.ACCOUNT_IS_NOT_EXIST);
        }

        accountService.unbindEnterprise(account);

        return Result.success();
    }

    @Override
    public Result addDep(EnterpriseRequest enterpriseRequest) {

        Enterprise enterprise = getExistEnterprise(enterpriseRequest);

        Department dep = getDepartment(enterpriseRequest, enterprise);

        if (depManageService.isDepExist(dep)) {
            return Result.failure(ErrorCode.DEPARTMENT_HAS_EXIST);
        }

        if (setParentId(enterpriseRequest.getParentDepName(), enterprise, dep)) {
            return Result.failure(ErrorCode.DEPARTMENT_IS_NOT_EXIST);
        }

        depManageService.addDep(dep);
        return Result.success();
    }

    @Override
    public Result deleteDep(EnterpriseRequest enterpriseRequest) {

        Enterprise enterprise = getExistEnterprise(enterpriseRequest);

        Department dep = getDepartment(enterpriseRequest, enterprise);

        if (depManageService.isDepExist(dep)) {
            return Result.failure(ErrorCode.DEPARTMENT_IS_NOT_EXIST);
        }

        depManageService.deleteDep(dep);

        return Result.success();
    }

    @Override
    public Result updateDep(EnterpriseRequest enterpriseRequest) {

        Enterprise enterprise = getExistEnterprise(enterpriseRequest);

        Department dep = getDepartment(enterpriseRequest, enterprise);

        if (depManageService.isDepExist(dep)) {
            return Result.failure(ErrorCode.DEPARTMENT_IS_NOT_EXIST);
        }

        Department oldDep = depManageService.findDep(dep);

        if (enterpriseRequest.getNewDepName() != null) {
            dep.setName(enterpriseRequest.getNewDepName());
        }

        if (setParentId(enterpriseRequest.getParentDepName(), enterprise, dep)) {
            return Result.failure(ErrorCode.DEPARTMENT_IS_NOT_EXIST);
        }

        depManageService.updateDep(oldDep, dep);

        return Result.success();
    }

    @Override
    public Result addStaffDepRelation(EnterpriseRequest enterpriseRequest) {

        Enterprise enterprise = getExistEnterprise(enterpriseRequest);

        Department dep = getDepartment(enterpriseRequest, enterprise);
        dep = depManageService.findDep(dep);

        Staff staff = getStaff(enterpriseRequest);

        if (!dep.getEnterpriseId().equals(staff.getEnterpriseId())) {
            return Result.failure(ErrorCode.ENTERPRISE_MISMATCH);
        }

        StaffDepartmentRelation relation = getStaffDepartmentRelation(enterpriseRequest.getPosition(), dep, staff);

        if (depManageService.isRelationExist(relation)) {
            return Result.failure(ErrorCode.DEPRELATION_HAS_EXIST);
        }

        depManageService.addStaffDepRelation(relation);

        return Result.success();
    }

    @Override
    public Result deleteStaffDepRelation(EnterpriseRequest enterpriseRequest) {

        Enterprise enterprise = getExistEnterprise(enterpriseRequest);

        Department dep = new Department();
        dep.setName(enterpriseRequest.getDepName());
        dep.setEnterpriseId(enterprise.getId());
        dep = depManageService.findDep(dep);

        Staff staff = getStaff(enterpriseRequest);

        if (!dep.getEnterpriseId().equals(staff.getEnterpriseId())) {
            return Result.failure(ErrorCode.ENTERPRISE_MISMATCH);
        }

        StaffDepartmentRelation relation = getStaffDepartmentRelation(enterpriseRequest.getPosition(), dep, staff);

        depManageService.deleteStaffDepRelation(relation);

        return Result.success();
    }

    @Override
    public Result updateStaffDepRelation(EnterpriseRequest enterpriseRequest) {

        Enterprise enterprise = getExistEnterprise(enterpriseRequest);

        Department dep = getDepartment(enterpriseRequest, enterprise);
        dep = depManageService.findDep(dep);

        Staff staff = getStaff(enterpriseRequest);

        if (!dep.getEnterpriseId().equals(staff.getEnterpriseId())) {
            return Result.failure(ErrorCode.ENTERPRISE_MISMATCH);
        }

        StaffDepartmentRelation relation = getStaffDepartmentRelation(dep, staff);
        relation.setPosition(enterpriseRequest.getPosition());

        StaffDepartmentRelation oldRelation = depManageService.findRelation(relation);



        if (enterpriseRequest.getNewDepName() != null) {
            dep.setName(enterpriseRequest.getNewDepName());
            dep = depManageService.findDep(dep);
            relation.setDepartmentId(dep.getId());
        }

        depManageService.updateStaffDepRelation(oldRelation, relation);

        return Result.success();
    }

    @Override
    public Result findPositionOfStaff(EnterpriseRequest enterpriseRequest) {

        Staff staff = getStaff(enterpriseRequest);

        staff = staffService.findStaffByMobile(staff);

        Enterprise enterprise = getEnterprise(enterpriseRequest);

        if (!staff.getEnterpriseId().equals(enterprise.getId())) {
            return Result.failure(ErrorCode.ENTERPRISE_MISMATCH);
        }

        return Result.success(depManageService.getPosition(staff));
    }

    @Override
    public Result findTree(EnterpriseRequest enterpriseRequest) {

        Department dep = new Department();

        Enterprise enterprise = getExistEnterprise(enterpriseRequest);

        dep.setName(enterprise.getName());
        dep.setEnterpriseId(enterprise.getId());

        return Result.success(depManageService.getDepTree(depManageService.findDep(dep).getId(), enterprise.getId()));
    }

    @Override
    public Result updateStaffRoleRelation(EnterpriseRequest enterpriseRequest) {
        Role role = new Role();
        role.setName(enterpriseRequest.getRoleName());
        role = roleManageService.findRoleByName(role);

        Staff staff = getStaff(enterpriseRequest);

        StaffRoleRelation relation = getStaffRoleRelation(role, staff);
        StaffRoleRelation oldRelation = roleManageService.findRelation(relation);
        //获取新的relation
        role.setName(enterpriseRequest.getRoleName());
        role = roleManageService.findRoleByName(role);

        staff.setMobile(enterpriseRequest.getMobile());
        staff = staffService.findStaffByMobile(staff);

        relation.setStaffId(staff.getId());
        relation.setRoleId(role.getId());

        roleManageService.updateStaffRoleRelation(oldRelation, relation);

        return Result.success();
    }


    @Override
    public Result addStaffRoleRelation(EnterpriseRequest enterpriseRequest) {

        Role role = new Role();
        role.setName(enterpriseRequest.getRoleName());
        role = roleManageService.findRoleByName(role);

        Staff staff = getStaff(enterpriseRequest);

        StaffRoleRelation relation = getStaffRoleRelation(role, staff);

        if (roleManageService.findRelation(relation) != null) {
            return Result.failure(ErrorCode.ROLERELATION_HAS_EXIST);
        }

        roleManageService.addStaffRoleRelation(relation);

        return Result.success();
    }

    @Override
    public Result deleteStaffRoleRelation(EnterpriseRequest enterpriseRequest) {

        Role role = new Role();
        role.setName(enterpriseRequest.getRoleName());
        role = roleManageService.findRoleByName(role);

        Staff staff = getStaff(enterpriseRequest);

        StaffRoleRelation relation = getStaffRoleRelation(role, staff);

        if (roleManageService.findRelation(relation) == null) {
            return Result.failure(ErrorCode.ROLERELATION_IS_NOT_EXIST);
        }

        roleManageService.deleteStaffRoleRelation(relation);
        return Result.success();
    }

    @Override
    public Result listRoleRelation(EnterpriseRequest enterpriseRequest) {

        Role role = new Role();

        role.setName(enterpriseRequest.getRoleName());

        Enterprise enterprise = new Enterprise();

        enterprise.setNo(enterpriseRequest.getEnterpriseNo());

        return Result.success(roleManageService.findStaffOfRoleInEnterprise(role, enterprise));
    }


    private Staff getStaff(EnterpriseRequest enterpriseRequest) {

        Staff staff = Staff.builder().mobile(enterpriseRequest.getMobile()).build();
        staff = staffService.findStaffByMobile(staff);

        return staff;
    }

    private Enterprise getEnterprise(EnterpriseRequest enterpriseRequest) {
        return Enterprise.builder().no(enterpriseRequest.getEnterpriseNo()).name(enterpriseRequest.getEnterpriseName()).build();
    }

    private Account getAccount(EnterpriseRequest enterpriseRequest) {
        return Account.builder().username(enterpriseRequest.getUsername()).build();
    }

    private Department getDepartment(EnterpriseRequest enterpriseRequest, Enterprise enterprise) {

        Department dep = new Department();

        dep.setName(enterpriseRequest.getDepName());
        dep.setEnterpriseId(enterprise.getId());

        return dep;
    }

    private Enterprise getExistEnterprise(EnterpriseRequest enterpriseRequest) {

        Enterprise enterprise = getEnterprise(enterpriseRequest);
        enterprise = enterpriseService.findEnterpriseByNo(enterprise);

        return enterprise;
    }

    private StaffDepartmentRelation getStaffDepartmentRelation(String position, Department dep, Staff staff) {

        StaffDepartmentRelation relation = getStaffDepartmentRelation(dep, staff);
        relation.setPosition(position);

        return relation;
    }

    private StaffDepartmentRelation getStaffDepartmentRelation(Department dep, Staff staff) {

        StaffDepartmentRelation relation = new StaffDepartmentRelation();

        relation.setStaffId(staff.getId());
        relation.setDepartmentId(dep.getId());

        return relation;
    }

    private StaffRoleRelation getStaffRoleRelation(Role role, Staff staff) {

        StaffRoleRelation relation = new StaffRoleRelation();

        relation.setStaffId(staff.getId());
        relation.setRoleId(role.getId());

        return relation;
    }

    private boolean setParentId(String parentDepName, Enterprise enterprise, Department dep) {

        Department parentDep = new Department();

        parentDep.setEnterpriseId(enterprise.getId());

        if (parentDepName != null) {
            parentDep.setName(parentDepName);
            if (!depManageService.isDepExist(parentDep)) {
                return true;
            }
        } else {
            parentDep.setName(enterprise.getName());
        }

        dep.setParentId(depManageService.findDep(parentDep).getId());

        return false;
    }

}
