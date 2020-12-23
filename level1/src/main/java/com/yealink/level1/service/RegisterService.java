package com.yealink.level1.service;

import com.yealink.level1.bean.Account;
import com.yealink.level1.bean.Enterprise;
import com.yealink.level1.bean.Staff;

/**
 * @author zhangchen
 * @description RegisterService
 * @date 2020/12/23 11:41
 */
public interface RegisterService {
    int accountRegister(Account account);
    int bindAccountStaff(String username, String mobile);
    int enterpriseRegister(Enterprise enterprise);
    int bindAccountEnterprise(String username, String name);
    int bindStaffEnterprise(String name,String mobile);
}
