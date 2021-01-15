package com.yealink.level3.service;

import com.yealink.level3.bean.request.PersonalRequest;
import com.yealink.level3.bean.result.Result;

/**
 * @author zhangchen
 * @description Login
 * @date 2020/12/23 17:06
 */
public interface LoginService {

    Result login(PersonalRequest personalRequest);
}
