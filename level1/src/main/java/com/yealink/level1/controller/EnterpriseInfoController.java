package com.yealink.level1.controller;

import com.yealink.level1.bean.Enterprise;
import com.yealink.level1.bean.request.EnterpriseRequest;
import com.yealink.level1.bean.request.PersonalRequest;
import com.yealink.level1.bean.result.Result;
import com.yealink.level1.service.EnterpriseInfoService;
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
    public Result updateEnterprise(@RequestBody@Validated({EnterpriseRequest.EnterpriseUpdate.class}) EnterpriseRequest enterpriseRequest){
        return enterpriseInfoService.updateEnterprise(enterpriseRequest);
    }

    @PostMapping("/deleteEnterprise")
    public Result deleteEnterprise(@RequestBody@Validated({EnterpriseRequest.EnterpriseDelete.class}) EnterpriseRequest enterpriseRequest){
        return enterpriseInfoService.deleteEnterprise(enterpriseRequest);
    }

    @PostMapping("/bindStaffEnterprise")
    public Result BindStaffEnterprise(@RequestBody @Validated({EnterpriseRequest.EnterpriseSelect.class, EnterpriseRequest.StaffUpdate.class}) EnterpriseRequest enterpriseRequest) {
        return enterpriseInfoService.bindStaffEnterprise(enterpriseRequest);
    }

    @PostMapping("/bindAccountEnterprise")
    public Result BindAccountEnterprise(@RequestBody @Validated({EnterpriseRequest.EnterpriseSelect.class,EnterpriseRequest.AccountUpdate.class})EnterpriseRequest enterpriseRequest){
        return enterpriseInfoService.bindAccountEnterprise(enterpriseRequest);
    }

    @PostMapping("/addStaff")
    public Result addStaff(@RequestBody@Validated({EnterpriseRequest.StaffInsert.class,EnterpriseRequest.EnterpriseSelect.class})EnterpriseRequest enterpriseRequest){
        return enterpriseInfoService.addStaff(enterpriseRequest);
    }

    @PostMapping("/addAccount")
    public Result addAccount(@RequestBody@Validated({EnterpriseRequest.AccountInsert.class,EnterpriseRequest.EnterpriseSelect.class})EnterpriseRequest enterpriseRequest){
        return enterpriseInfoService.addAccount(enterpriseRequest);
    }

    @PostMapping("/unbindStaff")
    public Result unbindStaff(@RequestBody@Validated({EnterpriseRequest.StaffUpdate.class,EnterpriseRequest.EnterpriseSelect.class})EnterpriseRequest enterpriseRequest){
        return enterpriseInfoService.unbindStaff(enterpriseRequest);
    }

    @PostMapping("/unbindAccount")
    public Result unbindAccount(@RequestBody@Validated({EnterpriseRequest.AccountUpdate.class,EnterpriseRequest.EnterpriseSelect.class})EnterpriseRequest enterpriseRequest){
        return enterpriseInfoService.unbindAccount(enterpriseRequest);
    }
}
