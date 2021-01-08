package com.yealink.level2.service;

import com.yealink.level2.bean.Enterprise;
import com.yealink.level2.bean.Staff;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

public interface StaffService {

    void add(@Valid Staff staff);

    void update(@Valid Staff oldStaff,@Valid Staff newStaff);

    void delete(@Valid Staff staff);

    void bindStaffEnterprise(@Valid Enterprise enterprise,@Valid Staff staff);

    @NotNull(message = "员工不存在") Staff findStaffByMobile(@Valid Staff staff);

    @NotNull(message = "无匹配员工") List<Staff> findStaffByName(@Valid Staff staff);

    List<Staff> findStaffByEnterpriseNo(@Valid Enterprise enterprise);

    List<Staff> findStaffOfRole(String id);

    Boolean isStaffExist(@Valid Staff staff);

    void unbindEnterprise(@Valid Staff staff);

    int genderTransfer(String gender);

    String findIdByMobile(String mobile);
}
