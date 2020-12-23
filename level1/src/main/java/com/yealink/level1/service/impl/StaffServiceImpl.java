package com.yealink.level1.service.impl;


import com.yealink.level1.bean.Staff;
import com.yealink.level1.domain.StaffMapper;
import com.yealink.level1.service.StaffService;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Date;
import java.util.List;




@Service
@Transactional
public class StaffServiceImpl implements StaffService {

    @Autowired
    private StaffMapper staffMapper;

    @Override
    public int add(Staff staff) {
        staff.setCreateTime(new Date().getTime());
        staff.setModifyTime(new Date().getTime());
        return staffMapper.add(staff);
    }

    @Override
    public int update(String id, String name, String mobile, String email, int gender) {
        Staff staffModify = staffMapper.findStaffById(id);
        if(name != null ){
            staffModify.setName(name);
        }
        if(mobile != null ){
            staffModify.setMobile(mobile);
        }
        if(email != null ){
            staffModify.setEmail(email);
        }
        if(gender>=0&&gender<=2 ){
            staffModify.setGender(gender);
        }
        staffModify.setModifyTime(new Date().getTime());
        return staffMapper.update(staffModify);
    }

    @Override
    public int updateEnterprise(String id, String enterpriseId) {
        Staff staff = staffMapper.findStaffById(id);
        if(enterpriseId != null ){
            staff.setEnterpriseId(enterpriseId);
        }
        staff.setModifyTime(new Date().getTime());
        return staffMapper.update(staff);
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

    @Override
    public Staff findStaffWithEnterprise(String id) {
        return staffMapper.findStaffWithEnterprise(id);
    }

    @Override
    public Staff getStaffWithAccount(String id) {
        return staffMapper.getStaffWithAccount(id);
    }


    /*
    public int updateName(String name){
        return staffMapper.updateName(name);
    }

     */
}
