package com.yealink.level1.service;

import com.yealink.level1.bean.Staff;
import java.util.List;

public interface StaffInfoService {

    int add(Staff staff);

    int update(Staff staff);

    int delete(String id);

    int bindStaffEnterprise(String name,String mobile);

    Staff findStaffByMobile(String mobile);

    List<Staff> findStaffByName(String name);

    List<Staff> findStaffByEnterpriseName(String name);

    String findIdByMobile(String mobile);


}
