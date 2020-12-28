package com.yealink.level1.service.impl;

import com.yealink.level1.bean.Staff;
import com.yealink.level1.domain.EnterpriseMapper;
import com.yealink.level1.domain.StaffMapper;
import com.yealink.level1.service.StaffInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class StaffInfoServiceImpl implements StaffInfoService {

    @Autowired
    private StaffMapper staffMapper;
    @Autowired
    private EnterpriseMapper enterpriseMapper;

    @Override
    public int add(Staff staff) {
        if(staffMapper.findIdByMobile(staff.getMobile()) == null ){
            staff.setCreateTime(new Date().getTime());
            staff.setModifyTime(new Date().getTime());
            return staffMapper.add(staff);
        }else{
            return -1;
        }

    }

    @Override
    public int update(Staff staff,String mobile) {
        staff.setId(findIdByMobile(mobile));
        staff.setModifyTime(new Date().getTime());
        return staffMapper.update(staff);
    }


    @Override
    public int delete(String mobile) {
        return staffMapper.delete(findIdByMobile(mobile));
    }

    @Override
    public int bindStaffEnterprise(String name,String mobile){
        if(enterpriseMapper.findIdByName(name) == null||findIdByMobile(mobile) == null){
            return -1;
        }else{
            Staff staff = findStaffByMobile(mobile);
            staff.setEnterpriseId(enterpriseMapper.findIdByName(name));
            return update(staff,mobile);
        }
    }

    @Override
    public Staff findStaffByMobile(String mobile) {
        return staffMapper.findStaffById(staffMapper.findIdByMobile(mobile));
    }

    @Override
    public List<Staff> findStaffByName(String name) {
        return staffMapper.findStaffByName(name);
    }

    @Override
    public List<Staff> findStaffByEnterpriseName(String name) {
        return staffMapper.findStaffByEnterpriseName(name);
    }

    @Override
    public String findIdByMobile(String mobile) {
        return staffMapper.findIdByMobile(mobile);
    }

    @Override
    public String findEnterpriseById(String id) {
        return staffMapper.findEnterpriseById(id);
    }


}
