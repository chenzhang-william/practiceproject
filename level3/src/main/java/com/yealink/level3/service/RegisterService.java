package com.yealink.level3.service;

import com.yealink.level3.bean.request.PersonalRequest;
import com.yealink.level3.bean.result.Result;

/**
 * @author zhangchen
 * @description RegisterService
 * @date 2020/12/23 11:41
 */
public interface RegisterService {

    Result accountRegister(PersonalRequest personalRequest);

    Result enterpriseRegister(PersonalRequest personalRequest);





}
