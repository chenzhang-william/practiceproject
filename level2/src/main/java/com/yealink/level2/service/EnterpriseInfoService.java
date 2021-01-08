package com.yealink.level2.service;

import com.yealink.level2.bean.request.EnterpriseRequest;
import com.yealink.level2.bean.result.Result;

/**
 * @author zhangchen
 * @description EnterpriseInfoservice
 * @date 2021/1/4 18:50
 */
public interface EnterpriseInfoService {

    Result updateEnterprise(EnterpriseRequest enterpriseRequest);

    Result deleteEnterprise(EnterpriseRequest enterpriseRequest);

    Result bindStaffEnterprise(EnterpriseRequest enterpriseRequest);

    Result bindAccountEnterprise(EnterpriseRequest enterpriseRequest);

    Result addStaff(EnterpriseRequest enterpriseRequest);

    Result findStaff(EnterpriseRequest enterpriseRequest);

    Result addAccount(EnterpriseRequest enterpriseRequest);

    Result unbindStaff(EnterpriseRequest enterpriseRequest);

    Result unbindAccount(EnterpriseRequest enterpriseRequest);

    Result addDep(EnterpriseRequest enterpriseRequest);

    Result deleteDep(EnterpriseRequest enterpriseRequest);

    Result updateDep(EnterpriseRequest enterpriseRequest);

    Result addStaffDepRelation(EnterpriseRequest enterpriseRequest);

    Result deleteStaffDepRelation(EnterpriseRequest enterpriseRequest);

    Result updateStaffDepRelation(EnterpriseRequest enterpriseRequest);

    Result findTree(EnterpriseRequest enterpriseRequest);

    Result updateStaffRoleRelation(EnterpriseRequest enterpriseRequest);

    Result addStaffRoleRelation(EnterpriseRequest enterpriseRequest);

    Result deleteStaffRoleRelation(EnterpriseRequest enterpriseRequest);

    Result listRoleRelation(EnterpriseRequest enterpriseRequest);

}
