package com.yealink.level1.service.impl;

import com.yealink.level1.bean.Department;
import com.yealink.level1.bean.Enterprise;
import com.yealink.level1.bean.StaffDepartmentRelation;
import com.yealink.level1.domain.DepartmentMapper;
import com.yealink.level1.domain.StaffDepRelationMapper;
import com.yealink.level1.service.DepManageService;
import com.yealink.level1.service.EnterpriseInfoService;
import com.yealink.level1.service.StaffInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
    @Autowired
    private EnterpriseInfoService enterpriseInfoService;

    @Override
    public int addDep(Department dep) {
        long now = new Date().getTime();
        dep.setCreateTime(now);
        dep.setModifyTime(now);
        return departmentMapper.add(dep);
    }

    @Override
    public int addStaffDepRelation(String mobile, String name, String position) {
        String enterpriseId = staffInfoService.findEnterpriseById(staffInfoService.findIdByMobile(mobile));
        String id = departmentMapper.findId(name,enterpriseId);
        if(id !=null){
            StaffDepartmentRelation staffDepartmentRelation = new StaffDepartmentRelation();
            staffDepartmentRelation.setStaffId(staffInfoService.findIdByMobile(mobile));
            staffDepartmentRelation.setDepartmentId(id);
            staffDepartmentRelation.setPosition(position);
            long now = new Date().getTime();
            staffDepartmentRelation.setCreateTime(now);
            staffDepartmentRelation.setModifyTime(now);
            return staffDepRelationMapper.add(staffDepartmentRelation);
        }else return -1;

    }

    @Override
    public int deleteDep(String depName,String enterpriseName) {
        return departmentMapper.delete(departmentMapper.findId(depName,enterpriseInfoService.findIdByName(enterpriseName)));
    }

    @Override
    public int deleteStaffDepRelation(String mobile, String name) {
        return staffDepRelationMapper.delete(staffDepRelationMapper.findId(staffInfoService.findIdByMobile(mobile),departmentMapper.findId(name,staffInfoService.findEnterpriseById(staffInfoService.findIdByMobile(mobile)))));
    }

    @Override
    public int updateDep(String depName,String enterpriseName, Department dep) {
        dep.setId(departmentMapper.findId(depName,enterpriseInfoService.findIdByName(enterpriseName)));
        dep.setModifyTime(new Date().getTime());
        return departmentMapper.update(dep);
    }

    @Override
    public int updateStaffDepRelation(String mobile, String oldDep,String enterpriseName, Map<String,String> newRelation) {
        String newMobile = newRelation.get("mobile");
        String newDep = newRelation.get("depName");
        String newPosition = newRelation.get("position");
        StaffDepartmentRelation relation = staffDepRelationMapper.findRelationById(staffDepRelationMapper.findId(staffInfoService.findIdByMobile(mobile),departmentMapper.findId(oldDep,enterpriseName)));
        relation.setStaffId(staffInfoService.findIdByMobile(newMobile));
        relation.setDepartmentId(departmentMapper.findId(newDep,enterpriseName));
        relation.setPosition(newPosition);
        relation.setModifyTime(new Date().getTime());
        return staffDepRelationMapper.update(relation);
    }

    @Override
    public int bindDepEnterprise(String depName, String enterpriseName) {
        Department dep = departmentMapper.findById(departmentMapper.findId(depName,enterpriseName));
        dep.setEnterpriseId(enterpriseInfoService.findIdByName(enterpriseName));
        return updateDep(depName,enterpriseName,dep);
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

    @Override
    public List getChildDep(String parentId) {
        return departmentMapper.findByParentId(parentId);
    }

    @Override
    public List getChildStaff(String id) {
        return staffDepRelationMapper.getStaffOfDep(id);
    }

    public List getTree(String id){
        List childNode =new ArrayList();
        childNode.add(departmentMapper.findById(id));
        List childStaff = getChildStaff(id);
        List<Department> childDep = getChildDep(id);
        if(childNode != null){
            childNode.add(childStaff);
            if (childDep != null){
                for(Department dep:childDep){
                    childNode.add(getTree(dep.getId()));
                }
            }
        }
        return childNode;
    }

}
