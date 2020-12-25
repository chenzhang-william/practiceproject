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
    public int update(Staff staff) {
        staff.setModifyTime(new Date().getTime());
        return staffMapper.update(staff);
    }


    @Override
    public int delete(String id) {
        return staffMapper.delete(id);
    }

    public int bindStaffEnterprise(String name,String mobile){
        String enterpriseId = enterpriseMapper.findIdByName(name);
        String staffId = staffMapper.findIdByMobile(mobile);
        if(enterpriseId == null||staffId == null){
            return -1;
        }else{
            Staff staff = staffMapper.findStaffById(staffId);
            staff.setEnterpriseId(enterpriseId);
            return update(staff);
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


}
