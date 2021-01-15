package com.yealink.practiceproject.controller;

import com.yealink.practiceproject.bean.request.EnterpriseRequest;
import com.yealink.practiceproject.bean.result.Result;
import com.yealink.practiceproject.service.userservice.EnterpriseInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhangchen
 * @description EnterpriseInfoController
 * @date 2021/1/4 18:56
 */
@RestController
@RequestMapping("/enterprise")
@Validated
public class EnterpriseInfoController {
    @Autowired
    private EnterpriseInfoService enterpriseInfoService;

    @PostMapping("/updateEnterprise")
    public Result updateEnterprise(@RequestBody @Validated({EnterpriseRequest.EnterpriseUpdate.class}) EnterpriseRequest enterpriseRequest) {
        return enterpriseInfoService.updateEnterprise(enterpriseRequest);
    }

    @PostMapping("/deleteEnterprise")
    public Result deleteEnterprise(@RequestBody @Validated({EnterpriseRequest.EnterpriseDelete.class}) EnterpriseRequest enterpriseRequest) {
        return enterpriseInfoService.deleteEnterprise(enterpriseRequest);
    }

    @PostMapping("/bindStaffEnterprise")
    public Result BindStaffEnterprise(@RequestBody @Validated({EnterpriseRequest.EnterpriseSelect.class, EnterpriseRequest.StaffUpdate.class}) EnterpriseRequest enterpriseRequest) {
        return enterpriseInfoService.bindStaffEnterprise(enterpriseRequest);
    }

    @PostMapping("/bindAccountEnterprise")
    public Result BindAccountEnterprise(@RequestBody @Validated({EnterpriseRequest.EnterpriseSelect.class, EnterpriseRequest.AccountUpdate.class}) EnterpriseRequest enterpriseRequest) {
        return enterpriseInfoService.bindAccountEnterprise(enterpriseRequest);
    }

    @PostMapping("/addStaff")
    public Result addStaff(@RequestBody @Validated({EnterpriseRequest.StaffInsert.class, EnterpriseRequest.EnterpriseSelect.class}) EnterpriseRequest enterpriseRequest) {
        return enterpriseInfoService.addStaff(enterpriseRequest);
    }

    @PostMapping("/findStaffByName")
    public Result findStaffByName(@RequestBody @Validated({EnterpriseRequest.StaffName.class}) EnterpriseRequest enterpriseRequest) {
        return enterpriseInfoService.findStaff(enterpriseRequest);
    }

    @PostMapping("/addAccount")
    public Result addAccount(@RequestBody @Validated({EnterpriseRequest.AccountInsert.class, EnterpriseRequest.EnterpriseSelect.class}) EnterpriseRequest enterpriseRequest) {
        return enterpriseInfoService.addAccount(enterpriseRequest);
    }

    @PostMapping("/unbindStaff")
    public Result unbindStaff(@RequestBody @Validated({EnterpriseRequest.StaffUpdate.class, EnterpriseRequest.EnterpriseSelect.class}) EnterpriseRequest enterpriseRequest) {
        return enterpriseInfoService.unbindStaff(enterpriseRequest);
    }

    @PostMapping("/unbindAccount")
    public Result unbindAccount(@RequestBody @Validated({EnterpriseRequest.AccountUpdate.class, EnterpriseRequest.EnterpriseSelect.class}) EnterpriseRequest enterpriseRequest) {
        return enterpriseInfoService.unbindAccount(enterpriseRequest);
    }

    @PostMapping("/addDepartment")
    public Result addDepartment(@RequestBody @Validated({EnterpriseRequest.DepInsert.class, EnterpriseRequest.DepSelect.class}) EnterpriseRequest enterpriseRequest) {
        return enterpriseInfoService.addDep(enterpriseRequest);
    }

    @PostMapping("/deleteDepartment")
    public Result deleteDepartment(@RequestBody @Validated({EnterpriseRequest.DepDelete.class, EnterpriseRequest.EnterpriseSelect.class}) EnterpriseRequest enterpriseRequest) {
        return enterpriseInfoService.deleteDep(enterpriseRequest);
    }

    @PostMapping("/updateDepartment")
    public Result updateDepartment(@RequestBody @Validated({EnterpriseRequest.DepUpdate.class, EnterpriseRequest.EnterpriseSelect.class}) EnterpriseRequest enterpriseRequest) {
        return enterpriseInfoService.updateDep(enterpriseRequest);
    }

    @PostMapping("/addStaffDepRelation")
    public Result addStaffDepRelation(@RequestBody @Validated({EnterpriseRequest.DepRelationInsert.class, EnterpriseRequest.StaffSelect.class, EnterpriseRequest.DepSelect.class, EnterpriseRequest.EnterpriseSelect.class}) EnterpriseRequest enterpriseRequest) {
        return enterpriseInfoService.addStaffDepRelation(enterpriseRequest);
    }

    @PostMapping("/deleteStaffDepRelation")
    public Result deleteStaffDepRelation(@RequestBody @Validated({EnterpriseRequest.StaffSelect.class, EnterpriseRequest.DepSelect.class, EnterpriseRequest.EnterpriseSelect.class}) EnterpriseRequest enterpriseRequest) {
        return enterpriseInfoService.deleteStaffDepRelation(enterpriseRequest);
    }

    @PostMapping("/updateStaffDepRelation")
    public Result updateStaffDepRelation(@RequestBody @Validated({EnterpriseRequest.StaffSelect.class, EnterpriseRequest.DepSelect.class, EnterpriseRequest.EnterpriseSelect.class}) EnterpriseRequest enterpriseRequest) {
        return enterpriseInfoService.updateStaffDepRelation(enterpriseRequest);
    }

    @PostMapping("findPositionOfStaff")
    public Result findPositionOfStaff(@RequestBody @Validated({EnterpriseRequest.StaffSelect.class}) EnterpriseRequest enterpriseRequest){
        return enterpriseInfoService.findPositionOfStaff(enterpriseRequest);
    }

    @PostMapping("/findTree")
    public Result findTree(@RequestBody @Validated({EnterpriseRequest.EnterpriseSelect.class}) EnterpriseRequest enterpriseRequest) {
        return enterpriseInfoService.findTree(enterpriseRequest);
    }

    @PostMapping("/updateStaffRoleRelation")
    public Result updateStaffRoleRelation(@RequestBody @Validated({EnterpriseRequest.RoleSelect.class, EnterpriseRequest.StaffSelect.class}) EnterpriseRequest enterpriseRequest) {
        return enterpriseInfoService.updateStaffRoleRelation(enterpriseRequest);
    }

    @PostMapping("/addStaffRoleRelation")
    public Result addStaffRoleRelation(@RequestBody @Validated({EnterpriseRequest.RoleSelect.class, EnterpriseRequest.StaffSelect.class}) EnterpriseRequest enterpriseRequest) {
        return enterpriseInfoService.addStaffRoleRelation(enterpriseRequest);
    }

    @PostMapping("/deleteStaffRoleRelation")
    public Result deleteStaffRoleRelation(@RequestBody @Validated({EnterpriseRequest.RoleSelect.class, EnterpriseRequest.StaffSelect.class}) EnterpriseRequest enterpriseRequest) {
        return enterpriseInfoService.deleteStaffRoleRelation(enterpriseRequest);
    }

    @PostMapping("/listRoleRelation")
    public Result listRoleRelation(@RequestBody @Validated({EnterpriseRequest.RoleSelect.class, EnterpriseRequest.EnterpriseSelect.class}) EnterpriseRequest enterpriseRequest) {
        return enterpriseInfoService.listRoleRelation(enterpriseRequest);
    }
}
