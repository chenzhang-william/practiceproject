package com.yealink.level1.service;

import com.yealink.level1.bean.request.EnterpriseRequest;
import com.yealink.level1.bean.request.PersonalRequest;
import com.yealink.level1.bean.result.Result;

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

    Result addAccount(EnterpriseRequest enterpriseRequest);

    Result unbindStaff(EnterpriseRequest enterpriseRequest);

    Result unbindAccount(EnterpriseRequest enterpriseRequest);

    Result addDep(EnterpriseRequest enterpriseRequest);

    Result deleteDep(EnterpriseRequest enterpriseRequest);

    Result updateDep(EnterpriseRequest enterpriseRequest);

    Result updatePosition(EnterpriseRequest enterpriseRequest);

}
