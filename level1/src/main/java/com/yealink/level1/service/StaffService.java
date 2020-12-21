package com.yealink.level1.service;

import com.yealink.level1.bean.Staff;

import java.util.List;

public interface StaffService {

    int add(Staff staff);

    int update(String name,int gender,String mobile,String email);

    int delete(String id);

    Staff findStaffById(String id);

    List<Staff> findStaffList();


}
