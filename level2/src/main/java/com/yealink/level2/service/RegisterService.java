package com.yealink.level2.service;

import com.yealink.level2.bean.request.PersonalRequest;
import com.yealink.level2.bean.result.Result;

/**
 * @author zhangchen
 * @description RegisterService
 * @date 2020/12/23 11:41
 */
public interface RegisterService {

    Result accountRegister(PersonalRequest personalRequest);

    Result enterpriseRegister(PersonalRequest personalRequest);





}
