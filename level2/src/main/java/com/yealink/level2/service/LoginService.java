package com.yealink.level2.service;

import com.yealink.level2.bean.request.PersonalRequest;
import com.yealink.level2.bean.result.Result;

/**
 * @author zhangchen
 * @description Login
 * @date 2020/12/23 17:06
 */
public interface LoginService {

    Result login(PersonalRequest personalRequest);
}
