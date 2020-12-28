package com.yealink.level1.service.impl;

import com.yealink.level1.bean.Department;
import com.yealink.level1.bean.StaffDepartmentRelation;
import com.yealink.level1.domain.DepartmentMapper;
import com.yealink.level1.domain.StaffDepRelationMapper;
import com.yealink.level1.service.DepManageService;
import com.yealink.level1.service.StaffInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
    @Autowired
    private StaffInfoService staffInfoService;

    @Override
    public int addDep(Department dep) {
        long now = new Date().getTime();
        dep.setCreateTime(now);
        dep.setModifyTime(now);
        return departmentMapper.add(dep);
    }

    @Override
    public int addStaffDepRelation(String mobile, String name, String position) {
        if(findEnterpriseById(departmentMapper.findIdByName(name)).equals(staffInfoService.findEnterpriseById(staffInfoService.findIdByMobile(mobile)))){
            StaffDepartmentRelation staffDepartmentRelation = new StaffDepartmentRelation();
            staffDepartmentRelation.setStaffId(staffInfoService.findIdByMobile(mobile));
            staffDepartmentRelation.setDepartmentId(departmentMapper.findIdByName(name));
            staffDepartmentRelation.setPosition(position);
            long now = new Date().getTime();
            staffDepartmentRelation.setCreateTime(now);
            staffDepartmentRelation.setModifyTime(now);
            return staffDepRelationMapper.add(staffDepartmentRelation);
        }else return -1;

    }

    @Override
    public int deleteDep(String name) {
        return departmentMapper.delete(departmentMapper.findIdByName(name));
    }

    @Override
    public int deleteStaffDepRelation(String mobile, String name) {
        return staffDepRelationMapper.delete(staffDepRelationMapper.findId(staffInfoService.findIdByMobile(mobile),departmentMapper.findIdByName(name)));
    }

    @Override
    public int updateDep(String name, Department dep) {
        dep.setId(departmentMapper.findIdByName(name));
        dep.setModifyTime(new Date().getTime());
        return departmentMapper.update(dep);
    }

    @Override
    public int updateStaffDepRelation(String mobile, String oldDep, Map<String,String> newRelation) {
        String newMobile = newRelation.get("mobile");
        String newDep = newRelation.get("depName");
        String newPosition = newRelation.get("position");
        StaffDepartmentRelation relation = staffDepRelationMapper.findRelationById(staffDepRelationMapper.findId(staffInfoService.findIdByMobile(mobile),departmentMapper.findIdByName(oldDep)));
        relation.setStaffId(staffInfoService.findIdByMobile(newMobile));
        relation.setDepartmentId(departmentMapper.findIdByName(newDep));
        relation.setPosition(newPosition);
        relation.setModifyTime(new Date().getTime());
        return staffDepRelationMapper.update(relation);
    }

    @Override
    public List<Map<String,String>> getPosition(String mobile) {
        return staffDepRelationMapper.getPosition(staffInfoService.findIdByMobile(mobile));
    }

    @Override
    public String findEnterpriseById(String id) {
        return departmentMapper.findEnterpriseById(id);
    }

    @Override
    public List<Department> findDepByEnterprise(String name) {
        return null;
    }
}
