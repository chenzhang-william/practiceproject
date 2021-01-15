package com.yealink.practiceproject.service.impl;

import com.yealink.practiceproject.bean.Enterprise;
import com.yealink.practiceproject.bean.Staff;
import com.yealink.practiceproject.domain.StaffMapper;
import com.yealink.practiceproject.service.EnterpriseService;
import com.yealink.practiceproject.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Service
@Transactional
@Validated
public class StaffServiceImpl implements StaffService {

    @Autowired
    private StaffMapper staffMapper;
    @Autowired
    private EnterpriseService enterpriseService;


    @Override
    public void add(@Valid Staff staff) {
        long now = new Date().getTime();
        staff.setCreateTime(now);
        staff.setModifyTime(now);
        staffMapper.add(staff);
    }

    @Override
    public void update(@Valid Staff oldStaff,@Valid Staff newStaff) {
        Staff staff = findStaffByMobile(oldStaff);
        newStaff.setId(staff.getId());
        newStaff.setModifyTime(new Date().getTime());
        staffMapper.update(newStaff);
    }


    @Override
    public void delete(@Valid Staff staff) {
        staffMapper.delete(findStaffByMobile(staff).getId());
    }

    @Override
    public void bindStaffEnterprise(@Valid Enterprise enterprise, @Valid Staff staff){
        Staff newStaff = new Staff();
        newStaff.setEnterpriseId(enterpriseService.findEnterpriseByNo(enterprise).getId());
        update(staff,newStaff);
    }

    @Override
    public @NotNull(message = "员工不存在") Staff findStaffByMobile(@Valid Staff staff) {
        return staffMapper.findStaffByMobile(staff.getMobile());
    }

    @Override
    public @NotNull(message = "无匹配员工") List<Staff> findStaffByName(@Valid Staff staff) {
        return staffMapper.findStaffByName(findStaffByMobile(staff).getName());
    }

    @Override
    public List<Staff> findStaffByEnterpriseNo(@Valid Enterprise enterprise) {
        return staffMapper.findStaffByEnterpriseNo(enterprise.getNo());
    }

    @Override
    public List<Staff> findStaffOfRole(String id) {
        return staffMapper.findStaffOfRole(id);
    }

    @Override
    public Boolean isStaffExist(@Valid Staff staff) {
        return staffMapper.findIdByMobile(staff.getMobile()) != null;
    }

    @Override
    public void unbindEnterprise(@Valid Staff staff) {
        staffMapper.unbindEnterprise(staff.getMobile());

    }

    @Override
    public int genderTransfer(String gender) {
        switch (gender){
            case "男":
                return 1;
            case "女":
                return 2;
            default:
                return 0;
        }
    }

    @Override
    public String findIdByMobile(String mobile) {
        return staffMapper.findIdByMobile(mobile);
    }

}



