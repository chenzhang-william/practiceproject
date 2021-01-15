package com.yealink.practiceproject.service.domain.impl;

import com.yealink.practiceproject.bean.Department;
import com.yealink.practiceproject.bean.Enterprise;
import com.yealink.practiceproject.bean.Staff;
import com.yealink.practiceproject.bean.StaffDepartmentRelation;
import com.yealink.practiceproject.dao.DepartmentMapper;
import com.yealink.practiceproject.dao.StaffDepRelationMapper;
import com.yealink.practiceproject.service.domain.DepManageService;
import com.yealink.practiceproject.service.domain.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
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
@Validated
public class DepManageServiceImpl implements DepManageService {
    @Autowired
    private DepartmentMapper departmentMapper;
    @Autowired
    private StaffDepRelationMapper staffDepRelationMapper;
    @Autowired
    private StaffService staffService;


    //部门的企业字段为必要字段
    @Override
    public void addDep(@Valid Department dep) {
        long now = new Date().getTime();
        dep.setCreateTime(now);
        dep.setModifyTime(now);
        departmentMapper.add(dep);
    }

    @Override
    public void addStaffDepRelation(@Valid StaffDepartmentRelation staffDepartmentRelation) {
        long now = new Date().getTime();
        staffDepartmentRelation.setCreateTime(now);
        staffDepartmentRelation.setModifyTime(now);
        staffDepRelationMapper.add(staffDepartmentRelation);
    }

    @Override
    public void deleteDep(@Valid Department dep) {
        departmentMapper.delete(findDep(dep).getId());
    }


    @Override
    public void deleteStaffDepRelation(@Valid StaffDepartmentRelation relation) {
        staffDepRelationMapper.delete(findRelation(relation).getId());
    }


    @Override
    public void updateDep(@Valid Department oldDep, @Valid Department newDep) {
        newDep.setId(findDep(oldDep).getId());
        newDep.setModifyTime(new Date().getTime());
        departmentMapper.update(newDep);
    }

    @Override
    public void updateStaffDepRelation(@Valid StaffDepartmentRelation oldRelation, @Valid StaffDepartmentRelation newRelation) {
        newRelation.setId(findRelation(oldRelation).getId());
        newRelation.setModifyTime(new Date().getTime());
        staffDepRelationMapper.update(newRelation);
    }

    @Override
    public @NotNull(message = "员工不在该部门") StaffDepartmentRelation findRelation(StaffDepartmentRelation relation) {
        return staffDepRelationMapper.findRelation(relation.getStaffId(), relation.getDepartmentId());
    }

    public @NotNull(message = "部门不存在") Department findDep(Department dep) {
        return departmentMapper.findDep(dep.getName(), dep.getEnterpriseId());
    }

    @Override
    public List<Map<String, String>> getPosition(@Valid Staff staff) {
        return staffDepRelationMapper.getPosition(staffService.findStaffByMobile(staff).getId());
    }

    //和下面的查找组织架构树一起修改
    @Override
    public List<Department> findDepOfEnterprise(@Valid Enterprise enterprise) {
        return null;
    }

    @Override
    public List<Department> getChildDep(String parentId) {
        return departmentMapper.findByParentId(parentId);
    }

    @Override
    public List<Staff> getChildStaff(String id) {
        return staffDepRelationMapper.getStaffOfDep(id);
    }

    public List getDepTree(String id) {
        List childNode = new ArrayList<>();
        childNode.add(departmentMapper.findById(id));
        List<Staff> childStaff = getChildStaff(id);
        List<Department> childDep = getChildDep(id);
        if (childStaff != null) {
            childNode.add(childStaff);
        }
        if (childDep != null) {
            for (Department dep : childDep) {
                childNode.add(getDepTree(dep.getId()));
            }
        }
        return childNode;
    }

    @Override
    public boolean isDepExist(Department dep) {
        return departmentMapper.findId(dep.getName(), dep.getEnterpriseId()) != null;
    }

    @Override
    public boolean isRelationExist(StaffDepartmentRelation staffDepartmentRelation) {
        return staffDepRelationMapper.findId(staffDepartmentRelation.getStaffId(), staffDepartmentRelation.getDepartmentId()) != null;
    }

}
