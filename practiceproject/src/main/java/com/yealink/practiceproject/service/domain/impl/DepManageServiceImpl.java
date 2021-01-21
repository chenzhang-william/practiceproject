package com.yealink.practiceproject.service.domain.impl;

import com.yealink.practiceproject.bean.Department;
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
    public List<Department> findDepOfEnterprise(String id) {
        return departmentMapper.findDepOfEnterprise(id);
    }

    public List getDepTree(String depId, String enterpriseId) {
        //获取公司的员工表，员工部门关系表，部门表
        List<Staff> staffList = staffService.findStaffByEnterpriseId(enterpriseId);

        List<Department> depList = findDepOfEnterprise(enterpriseId);

        List<StaffDepartmentRelation> depRelationList = getStaffDepartmentRelations(depList);

        return getChildNodeList(depId, staffList, depRelationList, depList);
    }

    private List<StaffDepartmentRelation> getStaffDepartmentRelations(List<Department> depList) {

        List<StaffDepartmentRelation> depRelationList = new ArrayList<>();

        for (Department dep : depList) {
            depRelationList.addAll(staffDepRelationMapper.findRelationByDepId(dep.getId()));
        }

        return depRelationList;
    }

    private List getChildNodeList(String depId, List<Staff> staffList, List<StaffDepartmentRelation> depRelationList, List<Department> depList) {

        List childNode = new ArrayList<>();
        //获取该部门信息
        getDepInfo(depId, depList, childNode);
        //获取子员工
        getChildStaff(depId, staffList, depRelationList, childNode);
        //获取子部门的组织架构
        getChildTree(depId, staffList, depRelationList, depList, childNode);

        return childNode;
    }

    private void getChildTree(String depId, List<Staff> staffList, List<StaffDepartmentRelation> depRelationList, List<Department> depList, List childNode) {

        for (Department dep : depList) {

            if (dep.getParentId() == null) {
                continue;
            }

            if (dep.getParentId().equals(depId)) {
                childNode.add(getChildNodeList(dep.getId(), staffList, depRelationList, depList));
            }
        }
    }

    private void getChildStaff(String depId, List<Staff> staffList, List<StaffDepartmentRelation> depRelationList, List childNode) {

        for (StaffDepartmentRelation relation : depRelationList) {

            if (relation.getDepartmentId().equals(depId)) {

                for (Staff staff : staffList) {

                    if (staff.getId().equals(relation.getStaffId())) {
                        childNode.add(staff);
                    }
                }
            }
        }
    }

    private void getDepInfo(String depId, List<Department> depList, List childNode) {

        for (Department dep : depList) {

            if (dep.getId().equals(depId)) {
                childNode.add(dep);
                break;
            }
        }
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
