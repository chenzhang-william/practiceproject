package com.yealink.level1.service;

import com.yealink.level1.bean.Staff;

import java.util.List;

public interface StaffService {

    int add(Staff staff);

    int update(String id, String name, String mobile, String email, int gender);

    int updateEnterprise(String id,String enterpriseId);

    int delete(String id);

    Staff findStaffById(String id);

    List<Staff> findStaffList();

    Staff findStaffWithEnterprise(String id);

    Staff getStaffWithAccount(String id);



}
