package com.yealink.practiceproject.service.userservice;

import com.yealink.practiceproject.bean.request.PersonalRequest;
import com.yealink.practiceproject.bean.result.Result;

/**
 * @author zhangchen
 * @description personalInfoService
 * @date 2020/12/29 20:03
 */
public interface PersonalInfoService {

    Result updateStaff(PersonalRequest personalRequest);

    Result findEnterpriseByName(PersonalRequest personalRequest);

    Result updateEnterpriseId(PersonalRequest personalRequest);

    Result updateStaffId(PersonalRequest personalRequest);

    Result updateAccount(PersonalRequest personalRequest);

    Result getAllAccounts(PersonalRequest personalRequest);

    Result deleteAccount(PersonalRequest personalRequest);

    Result personalInfo(PersonalRequest personalRequest);

    Result findRoleOfStaff(PersonalRequest personalRequest);

    Result enterpriseInfo(PersonalRequest personalRequest);

    Result findTree(PersonalRequest personalRequest);

    Result getPosition(PersonalRequest personalRequest);
}
