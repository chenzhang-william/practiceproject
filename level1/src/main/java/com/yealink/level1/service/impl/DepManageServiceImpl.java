package com.yealink.level1.service.impl;

import com.yealink.level1.bean.Department;
import com.yealink.level1.bean.StaffDepartmentRelation;
import com.yealink.level1.domain.DepartmentMapper;
import com.yealink.level1.domain.StaffDepRelationMapper;
import com.yealink.level1.service.DepManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author zhangchen
 * @description DepManageService
 * @date 2020/12/24 15:27
 */
@Service
@Transactional
public class DepManageServiceImpl implements DepManageService {
    @Autowired
    private DepartmentMapper departmentMapper;
    @Autowired
    private StaffDepRelationMapper staffDepRelationMapper;

    @Override
    public int addDep(Department dep) {
        long now = new Date().getTime();
        dep.setCreateTime(now);
        dep.setModifyTime(now);
        return departmentMapper.add(dep);
    }

    @Override
    public int addStaffDepRelation(String staffId, String name, String postition) {
        StaffDepartmentRelation staffDepartmentRelation = new StaffDepartmentRelation();
        staffDepartmentRelation.setStaffId(staffId);
        staffDepartmentRelation.setDepartmentId(departmentMapper.findIdByName(name));
        staffDepartmentRelation.setPosition(postition);
        long now = new Date().getTime();
        staffDepartmentRelation.setCreateTime(now);
        staffDepartmentRelation.setModifyTime(now);
        return staffDepRelationMapper.add(staffDepartmentRelation);
    }
}
