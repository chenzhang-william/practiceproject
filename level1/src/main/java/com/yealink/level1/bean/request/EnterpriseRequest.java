package com.yealink.level1.bean.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * @author zhangchen
 * @description EnterpriseRequest
 * @date 2021/1/4 18:53
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnterpriseRequest {

    public interface StaffInsert{}
    public interface StaffUpdate{}
    public interface StaffSelect{}
    public interface StaffName{}
    public interface AccountInsert{}
    public interface AccountUpdate{}
    public interface AccountDelete{}
    public interface EnterpriseInsert{}
    public interface EnterpriseSelect{}
    public interface EnterpriseUpdate{}
    public interface EnterpriseDelete{}
    public interface DepInsert{}
    public interface DepUpdate{}
    public interface DepDelete{}
    public interface DepSelect{}
    public interface DepRelationInsert{}
    public interface RoleSelect{}


    @NotNull(message = "手机号不能为空",groups = {StaffInsert.class, StaffUpdate.class, StaffSelect.class})
    private String mobile;

    @NotNull(message = "企业号不能为空",groups = {EnterpriseInsert.class, EnterpriseSelect.class,EnterpriseUpdate.class,EnterpriseDelete.class})
    private String enterpriseNo;

    private String newEnterpriseNo;

    @NotNull(message = "企业名不能为空",groups = {EnterpriseInsert.class})
    private String enterpriseName;
    
    @NotNull(message = "邮箱不能为空",groups = {StaffInsert.class})
    private String email;

    @NotNull(message = "性别不能为空",groups = {StaffInsert.class})
    private int gender;

    @NotNull(message = "姓名不能为空",groups = {StaffInsert.class,StaffName.class})
    private String name;

    @NotNull(message = "账号不能为空",groups = {AccountUpdate.class})
    private String username;

    @NotNull(message = "密码不能为空",groups = {AccountInsert.class, AccountDelete.class})
    private String password;

    @NotNull(message = "部门名不能为空",groups = {DepInsert.class,DepSelect.class,DepDelete.class,DepUpdate.class})
    private String depName;

    private String newDepName;

    private String parentDepName;

    @NotNull(message = "职位不能为空",groups = {DepRelationInsert.class})
    private String position;

    @NotNull(message = "角色名不能为空",groups = {RoleSelect.class})
    private String roleName;

    private String newRoleName;

}
