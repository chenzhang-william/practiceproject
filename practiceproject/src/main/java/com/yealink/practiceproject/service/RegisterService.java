package com.yealink.practiceproject.service;

import com.yealink.practiceproject.bean.request.PersonalRequest;
import com.yealink.practiceproject.bean.result.Result;

/**
 * @author zhangchen
 * @description RegisterService
 * @date 2020/12/23 11:41
 */
public interface RegisterService {

    Result accountRegister(PersonalRequest personalRequest);

    Result enterpriseRegister(PersonalRequest personalRequest);





}
