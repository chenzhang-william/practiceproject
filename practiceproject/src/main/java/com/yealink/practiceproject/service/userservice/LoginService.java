package com.yealink.practiceproject.service.userservice;

import com.yealink.practiceproject.bean.request.PersonalRequest;
import com.yealink.practiceproject.bean.result.Result;

/**
 * @author zhangchen
 * @description Login
 * @date 2020/12/23 17:06
 */
public interface LoginService {

    Result login(PersonalRequest personalRequest);
}
