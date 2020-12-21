package com.yealink.level1.service.impl;

import com.yealink.level1.bean.Staff;
import com.yealink.level1.domain.StaffMapper;
import com.yealink.level1.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class StaffServiceImpl implements StaffService {

    @Autowired
    private StaffMapper staffMapper;

    @Override
    public int add(Staff staff) {
        return staffMapper.add(staff);
    }

    @Override
    public int update(String name, int gender, String mobile, String email) {
        return staffMapper.update(name,gender,mobile,email);
    }

    @Override
    public int delete(String id) {
        return staffMapper.delete(id);
    }

    @Override
    public Staff findStaffById(String id) {
        return staffMapper.findStaffById(id);
    }

    @Override
    public List<Staff> findStaffList() {
        return staffMapper.findStaffList();
    }

    /*
    public int updateName(String name){
        return staffMapper.updateName(name);
    }

     */
}
