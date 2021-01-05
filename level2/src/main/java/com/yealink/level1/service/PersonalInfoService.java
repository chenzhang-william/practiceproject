package com.yealink.level1.service;

import com.yealink.level1.bean.Account;
import com.yealink.level1.bean.request.PersonalRequest;
import com.yealink.level1.bean.result.Result;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author zhangchen
 * @description personalInfoService
 * @date 2020/12/29 20:03
 */
public interface PersonalInfoService {

    Result updateStaff(PersonalRequest personalRequest);

    Result findEnterprise(PersonalRequest personalRequest);

    Result updateEnterpriseId(PersonalRequest personalRequest);

    Result updateStaffId(PersonalRequest personalRequest);

    Result updateAccount(PersonalRequest personalRequest);

    Result getAllAccounts(PersonalRequest personalRequest);

    Result deleteAccount(PersonalRequest personalRequest);

    Result personalInfo(PersonalRequest personalRequest);

    Result findRoleOfStaff(PersonalRequest personalRequest);

    Result enterpriseInfo(PersonalRequest personalRequest);

    Result findTree(PersonalRequest personalRequest);
}
