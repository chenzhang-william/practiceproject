package com.yealink.level3.controller;

import com.yealink.level3.bean.request.PersonalRequest;
import com.yealink.level3.bean.result.Result;
import com.yealink.level3.service.PersonalInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author zhangchen
 * @description PersonalInfoController
 * @date 2021/1/4 14:14
 */
@RestController
@RequestMapping("/personal")
@Validated
public class PersonalInfoController {
    @Autowired
    private PersonalInfoService personalInfoService;

    @PostMapping("/updateStaff")
    public Result UpdateStaff(@RequestBody @Validated({PersonalRequest.StaffUpdate.class}) PersonalRequest personalRequest) {
        return personalInfoService.updateStaff(personalRequest);
    }


    @PostMapping("/updateEnterpriseId")
    public Result updateEnterpriseId(@RequestBody @Validated({PersonalRequest.AccountUpdate.class, PersonalRequest.EnterpriseSelect.class}) PersonalRequest personalRequest) {
        return personalInfoService.updateEnterpriseId(personalRequest);
    }

    @PostMapping("/updateStaffId")
    public Result updateStaffId(@RequestBody @Validated({PersonalRequest.AccountUpdate.class, PersonalRequest.StaffSelect.class}) PersonalRequest personalRequest) {
        return personalInfoService.updateStaffId(personalRequest);
    }

    @PostMapping("/updateAccount")
    public Result updateAccount(@RequestBody @Validated({PersonalRequest.AccountUpdate.class}) PersonalRequest personalRequest) {
        return personalInfoService.updateAccount(personalRequest);
    }

    @PostMapping("/getAllAccounts")
    public Result getAllAccounts(@RequestBody @Validated({PersonalRequest.StaffSelect.class}) PersonalRequest personalRequest) {
        return personalInfoService.getAllAccounts(personalRequest);
    }

    @PostMapping("/deleteAccount")
    public Result deleteAccounts(@RequestBody @Validated({PersonalRequest.AccountDelete.class}) PersonalRequest personalRequest) {
        return personalInfoService.deleteAccount(personalRequest);
    }

    @PostMapping("/personalInfo")
    public Result personalInfo(@RequestBody @Validated({PersonalRequest.StaffSelect.class}) PersonalRequest personalRequest) {
        return personalInfoService.personalInfo(personalRequest);
    }

    @PostMapping("/findRoleOfStaff")
    public Result findRoleOfStaff(@RequestBody @Validated({PersonalRequest.StaffSelect.class}) PersonalRequest personalRequest) {
        return personalInfoService.findRoleOfStaff(personalRequest);
    }

    @PostMapping("/enterpriseInfo")
    public Result enterpriseInfo(@RequestBody @Validated({PersonalRequest.EnterpriseSelect.class}) PersonalRequest personalRequest) {
        return personalInfoService.enterpriseInfo(personalRequest);
    }

    @PostMapping("/findTree")
    public Result findTree(@RequestBody @Validated({PersonalRequest.EnterpriseSelect.class})PersonalRequest personalRequest){
        return personalInfoService.findTree(personalRequest);
    }

    @PostMapping("/findEnterprise")
    public Result findEnterprise(@RequestBody@Validated({PersonalRequest.EnterpriseName.class})PersonalRequest personalRequest){
        return personalInfoService.findEnterprise(personalRequest);
    }
}
