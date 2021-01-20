package com.yealink.practiceproject.service.domain.impl;

import com.yealink.practiceproject.bean.Enterprise;
import com.yealink.practiceproject.bean.Staff;
import com.yealink.practiceproject.dao.StaffMapper;
import com.yealink.practiceproject.service.domain.EnterpriseService;
import com.yealink.practiceproject.service.domain.StaffService;
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
    public void update(@Valid Staff oldStaff, @Valid Staff newStaff) {
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
    public void bindStaffEnterprise(@Valid Enterprise enterprise, @Valid Staff staff) {
        Staff newStaff = new Staff();
        newStaff.setEnterpriseId(enterpriseService.findEnterpriseByNo(enterprise).getId());
        update(staff, newStaff);
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
    public List<Staff> findStaffByEnterpriseId(String enterpriseId) {
        return staffMapper.findStaffByEnterpriseId(enterpriseId);

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
    public String findIdByMobile(String mobile) {
        return staffMapper.findIdByMobile(mobile);
    }

}



