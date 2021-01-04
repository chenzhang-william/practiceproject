package com.yealink.level1.service;

import com.yealink.level1.bean.Enterprise;
import com.yealink.level1.bean.Role;
import com.yealink.level1.bean.Staff;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

public interface StaffService {

    void add(@Valid Staff staff);

    void update(@Valid Staff oldStaff,@Valid Staff newStaff);

    void delete(@Valid Staff staff);

    void bindStaffEnterprise(@Valid Enterprise enterprise,@Valid Staff staff);

    @NotNull(message = "员工不存在") Staff findStaffByMobile(@Valid Staff staff);

    List<Staff> findStaffByName(@Valid Staff staff);

    List<Staff> findStaffByEnterpriseNo(@Valid Enterprise enterprise);

    List<Staff> findStaffOfRole(String id);

    Boolean isStaffExist(@Valid Staff staff);

    void unbindEnterprise(@Valid Staff staff);
}
