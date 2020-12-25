package com.yealink.level1.service;

import com.yealink.level1.bean.Account;
import com.yealink.level1.bean.Enterprise;

/**
 * @author zhangchen
 * @description RegisterService
 * @date 2020/12/23 11:41
 */
public interface RegisterService {

    int[] accountRegister(Account account,String mobile);

    int[] enterpriseRegister(Enterprise enterprise,String staffId);



}
