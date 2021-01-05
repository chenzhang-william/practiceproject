package com.yealink.level1.service;

import com.yealink.level1.bean.request.PersonalRequest;
import com.yealink.level1.bean.result.Result;

/**
 * @author zhangchen
 * @description RegisterService
 * @date 2020/12/23 11:41
 */
public interface RegisterService {

    Result accountRegister(PersonalRequest personalRequest);

    Result enterpriseRegister(PersonalRequest personalRequest);





}
